package me.marty.openpixelmon.entity.data;

import me.marty.openpixelmon.data.DataLoaders;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PartyEntry {
    private static final int DEFAULT_LEVEL = 0;
    private final PokeballItem pokeball;
    private final Identifier id;
    private final int hp;
    private final boolean isMale;
    private final int level;

    public PartyEntry(Identifier id, int hp, int maxHp, boolean isMale, int level, PokeballItem pokeball) {
        this.id = id;
        this.hp = hp;
        this.isMale = isMale;
        this.level = level;
        this.pokeball = pokeball;
    }

    public PartyEntry(Identifier id, int hp, int maxHp, boolean isMale, PokeballItem pokeball) {
        this(id, hp, maxHp, isMale, DEFAULT_LEVEL, pokeball);
    }

    public PartyEntry(PixelmonEntity entity, PokeballItem pokeball) {
        this(entity.getPokedexEntry().getIdentifier(), entity.getHp(), entity.getMaxHp(), entity.isMale() , entity.getLevel(), pokeball);
    }

    public int getHp() {
        return hp;
    }

    public boolean isMale() {
        return isMale;
    }

    public int getLevel() {
        return level;
    }

    public PokeballItem getPokeball() {
        return pokeball;
    }

    public Identifier getIdentifier() {
        return id;
    }

    public NbtCompound writeToTag(NbtCompound tag) {
        tag.putString("id", id.toString());
        tag.putInt("hp", hp);
        tag.putBoolean("isMale", isMale);
        tag.putInt("level", level);
        tag.putString("pokeball", Registry.ITEM.getId(pokeball).toString());
        return tag;
    }

    public static PartyEntry readFromTag(NbtCompound tag) {
        Identifier id = new Identifier(tag.getString("id"));
        int hp = tag.getInt("hp");
        boolean isMale = tag.getBoolean("isMale");
        int level = tag.getInt("level");
        PokeballItem pokeball = (PokeballItem) Registry.ITEM.get(new Identifier(tag.getString("pokeball")));
        return new PartyEntry(id, hp, 0, isMale, level, pokeball);
    }

    public PacketByteBuf writeToPacketBuf(PacketByteBuf buf) {
        buf.writeIdentifier(id);
        buf.writeInt(hp);
        buf.writeBoolean(isMale);
        buf.writeInt(level);
        buf.writeVarInt(Registry.ITEM.getRawId(pokeball));
        return buf;
    }

    public static PartyEntry readFromPacketBuf(PacketByteBuf buf) {
        Identifier id = buf.readIdentifier();
        int hp = buf.readInt();
        boolean isMale = buf.readBoolean();
        int level = buf.readInt();
        PokeballItem pokeball = (PokeballItem) Registry.ITEM.get(buf.readVarInt());
        return new PartyEntry(id, hp, 0, isMale, level, pokeball);
    }

    public String getName() {
        return getIdentifier().getPath();
    }
}
