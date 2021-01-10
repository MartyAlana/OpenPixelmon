package me.marty.openpixelmon.entity.pixelmon;

import me.marty.openpixelmon.api.pixelmon.PokedexData;
import me.marty.openpixelmon.client.translate.OpenPixelmonTranslator;
import me.marty.openpixelmon.entity.Entities;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("EntityConstructor")
public class PixelmonEntity extends AnimalEntity implements IAnimatable {

	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.pixelmon.idle", true);
	private final AnimationFactory factory = new AnimationFactory(this);
	private final PokedexData pokedexData;
	private static final TrackedData<Boolean> BOSS = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Integer> LEVEL = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public PixelmonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
		pokedexData = Entities.GENERATION_3.getPixelmonById(Registry.ENTITY_TYPE.getId(getType()));
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(BOSS, false);
		this.dataTracker.startTracking(LEVEL, 0);
	}

	@Override
	public void writeCustomDataToTag(CompoundTag tag) {
		super.writeCustomDataToTag(tag);

		tag.putBoolean("boss", this.dataTracker.get(BOSS));
		tag.putInt("level", this.dataTracker.get(LEVEL));
	}

	@Override
	public void readCustomDataFromTag(CompoundTag tag) {
		super.readCustomDataFromTag(tag);

		if (tag.getKeys().contains("level")) {
			this.setBoss(tag.getBoolean("boss"));
			this.setLevel(tag.getInt("level"));
		}
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
		entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);

		setBoss(getLevel() >= 20 && random.nextFloat() < 0.05F);
		setLevel(pokedexData.minLevel + random.nextInt(pokedexData.evolutionLevel / 2));
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

	public PokedexData getPokedexData() {
		return pokedexData;
	}

	public String getNickname() {
		return OpenPixelmonTranslator.createTranslation(pokedexData.name).getString();
	}
}
