package me.marty.openpixelmon.entity.data;

import me.marty.openpixelmon.data.DataLoaders;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PartyEntry {
	private static final int DEFAULT_LEVEL = 0;
	private final PokeballItem pokeball;
	private final Identifier id;
	private final int hp;
	private final Gender gender;
	private final int level;

	public PartyEntry(Identifier id, int hp, int maxHp, Gender gender, int level, PokeballItem pokeball) {
		this.id = id;
		this.hp = hp;
		this.gender = gender;
		this.level = level;
		this.pokeball = pokeball;
	}

	public PartyEntry(Identifier id, int hp, int maxHp, Gender gender, PokeballItem pokeball) {
		this(id, hp, maxHp, gender, DEFAULT_LEVEL, pokeball);
	}

	public PartyEntry(PixelmonEntity entity, PokeballItem pokeball) {
		this(DataLoaders.getIdentifier(entity.getPokedexData()), entity.getHp(), entity.getMaxHp(), entity.getGender() , entity.getLevel(), pokeball);
	}

	public int getHp() {
		return hp;
	}

	public Gender getGender() {
		return gender;
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

	public CompoundTag writeToTag(CompoundTag tag) {
		tag.putString("id", id.toString());
		tag.putInt("hp", hp);
		tag.putByte("gender", (byte) gender.ordinal());
		tag.putInt("level", level);
		tag.putString("pokeball", Registry.ITEM.getId(pokeball).toString());
		return tag;
	}

	public static PartyEntry readFromTag(CompoundTag tag) {
		Identifier id = new Identifier(tag.getString("id"));
		int hp = tag.getInt("hp");
		Gender gender = Gender.values()[tag.getByte("gender")];
		int level = tag.getInt("level");
		PokeballItem pokeball = (PokeballItem) Registry.ITEM.get(new Identifier(tag.getString("pokeball")));
		return new PartyEntry(id, hp, 0, gender, level, pokeball);
	}

    public PacketByteBuf writeToPacketBuf(PacketByteBuf buf) {
        buf.writeIdentifier(id);
        buf.writeInt(hp);
        buf.writeByte((byte) gender.ordinal());
        buf.writeInt(level);
        buf.writeVarInt(Registry.ITEM.getRawId(pokeball));
        return buf;
    }

    public static PartyEntry readFromPacketBuf(PacketByteBuf buf) {
		Identifier id = buf.readIdentifier();
        int hp = buf.readInt();
        Gender gender = Gender.values()[buf.readByte()];
        int level = buf.readInt();
        PokeballItem pokeball = (PokeballItem) Registry.ITEM.get(buf.readVarInt());
        return new PartyEntry(id, hp, 0, gender, level, pokeball);
    }
}
