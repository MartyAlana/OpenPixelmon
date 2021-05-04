package me.marty.openpixelmon.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.component.EntityComponents;
import me.marty.openpixelmon.data.DataLoaders;
import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.entity.data.PartyEntry;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import me.marty.openpixelmon.network.Packets;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.NbtCompoundTagArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.literal;

public class Commands {
    public static void initialize() {
        CommandRegistrationCallback.EVENT.register(Commands::register);
    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(literal("summon")
                .then(literal("pixelmon")
                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .then(CommandManager.argument("type", IdentifierArgumentType.identifier())
                                .suggests((context, builder) -> {
                                    for (Identifier identifier : DataLoaders.PIXELMON_MANAGER.getPixelmon().keySet()) {
                                        builder.suggest(identifier.toString());
                                    }

                                    return builder.buildFuture();
                                })
                                .executes(commandContext -> {
                                    return execute(commandContext.getSource(), IdentifierArgumentType.getIdentifier(commandContext, "type"), commandContext.getSource().getPosition(), new NbtCompound(), true);
                                })
                                .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                        .executes(commandContext -> {
                                            return execute(commandContext.getSource(), IdentifierArgumentType.getIdentifier(commandContext, "type"), Vec3ArgumentType.getVec3(commandContext, "pos"), new NbtCompound(), true);
                                        })
                                        .then(CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound())
                                                .executes(commandContext -> {
                                                    return execute(commandContext.getSource(), IdentifierArgumentType.getIdentifier(commandContext, "type"), Vec3ArgumentType.getVec3(commandContext, "pos"), NbtCompoundTagArgumentType.getNbtCompound(commandContext, "nbt"), false);
                                                }))))));

        dispatcher.register(
                literal("pokeparty")
                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                        .executes(context -> {
                            PixelmonEntity entity = Entities.PIXELMON.create(context.getSource().getWorld());
                            entity.setup(OpenPixelmon.id("bulbasaur"));
                            EntityComponents.PARTY_COMPONENT.get(context.getSource().getPlayer()).getParty().add(
                                    context.getSource().getPlayer(),
                                    new PartyEntry(entity, (PokeballItem) OpenPixelmonItems.POKE_BALL));
                            EntityComponents.PARTY_COMPONENT.sync(context.getSource().getPlayer());
                            return Command.SINGLE_SUCCESS;
                        }));

        dispatcher.register(
                literal("wildbattle")
                    .requires(source -> source.hasPermissionLevel(4))
                    .executes(context -> {
                        ServerPlayNetworking.send(context.getSource().getPlayer(),
                                Packets.BATTLE_START,
                                PacketByteBufs.create()
                                        .writeVarInt(2)
                                        .writeVarInt(context.getSource().getPlayer().getId())
                                        .writeVarInt(-1)
                        );
                        return Command.SINGLE_SUCCESS;
                    }));
    }

    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon.failed"));
    private static final SimpleCommandExceptionType DUPLICATE_UUID = new SimpleCommandExceptionType(new TranslatableText("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon.invalidPosition"));

    private static int execute(ServerCommandSource source, Identifier type, Vec3d pos, NbtCompound nbt, boolean initialize) throws CommandSyntaxException {
        Identifier realType = OpenPixelmon.id(type.getPath());
        BlockPos blockPos = new BlockPos(pos);

        if (!DataLoaders.PIXELMON_MANAGER.getPixelmon().containsKey(realType)) {
            return 0;
        }

        if (!World.isValid(blockPos)) {
            throw INVALID_POSITION_EXCEPTION.create();
        } else {
            NbtCompound NbtCompound = nbt.copy();
            NbtCompound.putString("id", Registry.ENTITY_TYPE.getId(Entities.PIXELMON).toString());
            ServerWorld serverWorld = source.getWorld();

            PixelmonEntity entity = (PixelmonEntity) EntityType.loadEntityWithPassengers(NbtCompound, serverWorld, e -> {
                e.refreshPositionAndAngles(pos.x, pos.y, pos.z, e.yaw, e.pitch);
                return e;
            });

            if (entity == null) {
                throw FAILED_EXCEPTION.create();
            } else {
                entity.setup(realType);

                if (initialize) {
                    entity.initialize(source.getWorld(), source.getWorld().getLocalDifficulty(entity.getBlockPos()), SpawnReason.COMMAND, null, null);
                }

                if (!serverWorld.shouldCreateNewEntityWithPassenger(entity)) {
                    throw DUPLICATE_UUID.create();
                } else {
                    source.sendFeedback(new TranslatableText("commands.summon.success", entity.getDisplayName()), true);
                    return 1;
                }
            }
        }
    }
}
