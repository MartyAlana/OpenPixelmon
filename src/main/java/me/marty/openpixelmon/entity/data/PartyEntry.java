package me.marty.openpixelmon.entity.data;

import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PartyEntry {
	private static final int DEFAULT_LEVEL = 0;
	private final PokeballItem pokeball;
	private final EntityType<? extends PixelmonEntity> entityType;
	private final int hp;
	private final Gender gender;
	private final int level;

	public PartyEntry(EntityType<? extends PixelmonEntity> entityType, int hp, int maxHp, Gender gender, int level, PokeballItem pokeball) {
		this.entityType = entityType;
		this.hp = hp;
		this.gender = gender;
		this.level = level;
		this.pokeball = pokeball;
	}

	public PartyEntry(EntityType<? extends PixelmonEntity> rawId, int hp, int maxHp, Gender gender, PokeballItem pokeball) {
		this(rawId, hp, maxHp, gender, DEFAULT_LEVEL, pokeball);
	}

	public PartyEntry(PixelmonEntity entity, PokeballItem pokeball) {
		this(entity.getType(), entity.getHp(), entity.getMaxHp(), entity.getGender() , entity.getLevel(), pokeball);
	}

	public EntityType<? extends PixelmonEntity> getEntityType() {
		return entityType;
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

	public CompoundTag writeToTag(CompoundTag tag) {
		tag.putString("EntityType", Registry.ENTITY_TYPE.getId(entityType).toString());
		tag.putInt("Hp", hp);
		tag.putByte("Gender", (byte) gender.ordinal());
		tag.putInt("Level", level);
		tag.putString("Pokeball", Registry.ITEM.getId(pokeball).toString());
		return tag;
	}

	public static PartyEntry readFromTag(CompoundTag tag) {
		EntityType<? extends PixelmonEntity> entityType = (EntityType<? extends PixelmonEntity>) Registry.ENTITY_TYPE.get(new Identifier(tag.getString("EntityType")));
		int hp = tag.getInt("Hp");
		Gender gender = Gender.values()[tag.getByte("Gender")];
		int level = tag.getInt("Level");
		PokeballItem pokeball = (PokeballItem) Registry.ITEM.get(new Identifier(tag.getString("Pokeball")));
		return new PartyEntry(entityType, hp, 0, gender, level, pokeball);
	}

//    public PacketByteBuf writeToPacketBuf(PacketByteBuf buf) {
//        buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entityType)); // EntityType
//        buf.writeInt(hp); // Hp
//        buf.writeByte((byte) gender.ordinal()); // Gender
//        buf.writeInt(level); // Level
//        buf.writeVarInt(Registry.ITEM.getRawId(pokeball)); // Pokeball
//        return buf;
//    }

//    public static PartyEntry readFromPacketBuf(PacketByteBuf buf) {
//        EntityType<? extends PixelmonEntity> entityType = (EntityType<? extends PixelmonEntity>) Registry.ENTITY_TYPE.get(buf.readVarInt());
//        int hp = buf.readInt();
//        Gender gender = Gender.values()[buf.readByte()];
//        int level = buf.readInt();
//        PokeballItem pokeball = (PokeballItem) Registry.ITEM.get(buf.readVarInt());
//        return new PartyEntry(entityType, hp, gender, level, pokeball);
//    }
}
