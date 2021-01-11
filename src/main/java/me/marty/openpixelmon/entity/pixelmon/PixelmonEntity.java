package me.marty.openpixelmon.entity.pixelmon;

import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.client.translate.OpenPixelmonTranslator;
import me.marty.openpixelmon.entity.data.Gender;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("EntityConstructor")
public class PixelmonEntity extends AnimalEntity implements IAnimatable {

	private final AnimationFactory factory = new AnimationFactory(this);
	private final PokedexEntry pokedexEntry;
	protected static final TrackedData<Boolean> BOSS = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
	protected static final TrackedData<Integer> LEVEL = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
	protected static final TrackedData<Byte> GENDER = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.BYTE);
	protected static final byte MALE_BYTE = (byte) Gender.MALE.ordinal();
	protected static final byte FEMALE_BYTE = (byte) Gender.FEMALE.ordinal();
	protected static final Gender[] GENDERS = Gender.values();
	private final int hp;

	public PixelmonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
		this.pokedexEntry = null;//PokeGeneration.getPixelmonById(Registry.ENTITY_TYPE.getId(getType()));
		this.hp = getMaxHp();
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(BOSS, false);
		this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
		this.dataTracker.startTracking(LEVEL, 0);
		this.dataTracker.startTracking(GENDER, MALE_BYTE);
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);

		tag.putBoolean("boss", this.dataTracker.get(BOSS));
		if(this.dataTracker.get(OWNER_UUID).isPresent()){
			tag.putUuid("ownerUuid", this.dataTracker.get(OWNER_UUID).get());
		}
		tag.putInt("level", this.dataTracker.get(LEVEL));
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);
		if (tag.getKeys().contains("level")) {
			this.setBoss(tag.getBoolean("boss"));
			this.setLevel(tag.getInt("level"));
		}
		if(tag.getKeys().contains("ownerUuid")) {
			this.setOwnerUuid(tag.getUuid("ownerUuid"));
		}
	}

	private void setOwnerUuid(UUID uuid) {
		if(uuid != null){
			this.dataTracker.set(OWNER_UUID, Optional.of(uuid));
		}else {
			this.dataTracker.set(OWNER_UUID, Optional.empty());
		}
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
		entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);

		setBoss(getLevel() >= 20 && random.nextFloat() < 0.05F);
		setLevel(0);//pokedexEntry.minLevel + random.nextInt(pokedexEntry.evolutionLevel / 2));
		return entityData;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new LookAroundGoal(this));
		this.goalSelector.add(1, new WanderAroundGoal(this, 0.4d));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PixelmonEntity.class, 10));
		this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 4));
	}

	public boolean isBoss() {
		return this.dataTracker.get(BOSS);
	}

	public void setBoss(boolean boss) {
		this.dataTracker.set(BOSS, boss);
	}

	public int getLevel() {
		return this.dataTracker.get(LEVEL);
	}

	public void setGender(Gender gender) {
		this.dataTracker.set(GENDER, (byte) gender.ordinal());
	}

	public Gender getGender() {
		return GENDERS[this.dataTracker.get(GENDER)];
	}

	public void setLevel(int level) {
		this.dataTracker.set(LEVEL, level);
	}

	public static DefaultAttributeContainer.Builder createPixelmonAttributes() { //TODO: finish
		return createMobAttributes();
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new PixelmonEntity(getType(), world);
	}

	@Override
	public EntityType<PixelmonEntity> getType() {
		return (EntityType<PixelmonEntity>) super.getType();
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		//TODO:
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (source.getAttacker() instanceof PlayerEntity) {
			return false;
		}

		return super.damage(source, amount);
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public PokedexEntry getPokedexData() {
		return pokedexEntry;
	}

	public String getNickname() {
		return "No";
//		return OpenPixelmonTranslator.createTranslation(pokedexEntry.name).getString();
	}

	public boolean isWild() {
		return this.dataTracker.get(OWNER_UUID).isPresent();
	}

	public int getMaxHp() {
		return 100; //TODO: implement base stats, IV & EV properly,
	}

	public int getHp() {
		return hp;
	}

	public ServerPlayerEntity getOwner() {
		if(this.dataTracker.get(OWNER_UUID).isPresent()) {
			return world.getServer().getPlayerManager().getPlayer(this.dataTracker.get(OWNER_UUID).get()); //TODO
		}
		return null;
	}
}
