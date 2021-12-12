package me.marty.openpixelmon.entity.pokeball;

import me.marty.openpixelmon.api.component.EntityComponents;
import me.marty.openpixelmon.entity.Entities;
import me.marty.openpixelmon.entity.data.PartyEntry;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class PokeballEntity extends ThrownEntity {

    protected static final TrackedData<Boolean> CATCHING = DataTracker.registerData(PokeballEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public boolean doingSomething;
    public boolean sendingOut;

    private final Item item;

    public PokeballEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
        this.item = OpenPixelmonItems.POKE_BALL;
    }

    public PokeballEntity(double x, double y, double z, ClientWorld world) {
        super(Entities.POKEBALL_ENTITY, x, y, z, world);
        this.item = OpenPixelmonItems.POKE_BALL;
    }

    public PokeballEntity(PlayerEntity user, World world, Item item) {
        super(Entities.POKEBALL_ENTITY, user, world);
        this.item = item;
    }

    public PokeballEntity(ServerPlayerEntity player, Item item) {
        super(Entities.POKEBALL_ENTITY, player.getWorld());
        this.item = item;
        this.setPos(player.getX(), player.getY(), player.getZ());
        setOwner(player);
        this.sendingOut = true;
    }

    public Item getItem() {
        return item;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if (sendingOut) {
            PlayerEntity player = (PlayerEntity) getOwner();
            System.out.println("Send out Pixelmon of " + player);
        }
        if (!dataTracker.get(CATCHING)) {
            BlockPos pos = blockHitResult.getBlockPos();
            kill();
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(getItem())));
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (sendingOut) return;
        if (entityHitResult.getEntity() instanceof PixelmonEntity) {
            dataTracker.set(CATCHING, true);
            this.doingSomething = true;
            setNoGravity(true);
            //TODO: make it fall to the ground.
            setVelocity(new Vec3d(0, 0, 0));
            if (!getEntityWorld().isClient()) {
                EntityComponents.PARTY_COMPONENT.get(Objects.requireNonNull(getOwner(), "Owner was null!")).getParty().add((ServerPlayerEntity) getOwner(), new PartyEntry((PixelmonEntity) entityHitResult.getEntity(), (PokeballItem) OpenPixelmonItems.POKE_BALL));
                EntityComponents.PARTY_COMPONENT.sync(getOwner());
            }
            entityHitResult.getEntity().kill();
        } else {
            if (!doingSomething) {
                Vec3d pos = entityHitResult.getPos();
                kill();
                entityHitResult.getEntity().damage(DamageSource.GENERIC, 1);
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(getItem())));
            }
        }
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(CATCHING, false);
    }
}
