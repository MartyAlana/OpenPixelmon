package me.marty.openpixelmon.entity.pixelmon;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.pixelmon.EvStorage;
import me.marty.openpixelmon.api.pixelmon.IvStorage;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import me.marty.openpixelmon.data.DataLoaders;
import me.marty.openpixelmon.entity.PixelmonDataTrackers;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Optional;
import java.util.UUID;

/**
 * The class which represents a living pixelmon inside the world.
 * If you want to do pokedex related things, see {@link PokedexEntry}
 */
public class PixelmonEntity extends AnimalEntity implements IAnimatable {

    public static final Identifier MISSING = OpenPixelmon.id("pichu");

    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    protected static final TrackedData<Identifier> PIXELMON_ID = DataTracker.registerData(PixelmonEntity.class, PixelmonDataTrackers.IDENTIFIER);

    protected static final TrackedData<Boolean> BOSS = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Integer> MAX_HP = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> NATURE = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> PSEUDO_NATURE = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> GROWTH = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> INTERACTION_COUNT = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> FORM = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Boolean> MALE = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<Integer> LEVEL = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> DYNAMAX_LEXEL = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> TRANSFORMATION = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> SPAWN_LOC = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> BREEDING_LEVELS = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Boolean> SHINY = DataTracker.registerData(PixelmonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    protected static final TrackedData<IvStorage> IV_STORAGE = DataTracker.registerData(PixelmonEntity.class, PixelmonDataTrackers.IVS);
    protected static final TrackedData<EvStorage> EV_STORAGE = DataTracker.registerData(PixelmonEntity.class, PixelmonDataTrackers.EVS);

    protected final AnimationFactory factory = new AnimationFactory(this);

    public PixelmonEntity(EntityType<PixelmonEntity> entityType, World world) {
        super(entityType, world);
        this.setHealth(get(MAX_HP));
    }

    public void setup(Identifier entry) {
        this.setPixelmonId(entry);
        setHealth(69420);

        calculateStats();
    }

    /**
     * Calculates things like health, etc for a pixelmon.
     * Here are formula's from the wiki:
     * <p>
     * HP = floor(0.01 x (2 x Base + IV + floor(0.25 x EV)) x Level) + Level + 10
     */
    private void calculateStats() {
//        int hpIV = !this.bottleCapIVs.contains(StatsType.HP) ? this.IVs.HP : 31;
//        return (int)(((float)hpIV + 2.0F * (float)baseStats.hp + (float)this.EVs.HP / 4.0F + 100.0F) * (float)level / 100.0F + 10.0F);
//        setHealth(Math.floor(0.01 * (2 * getPokedexEntry().hp + getPokedexEntry().)));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());

        this.dataTracker.startTracking(BOSS, false);
        this.dataTracker.startTracking(PIXELMON_ID, MISSING);
        this.dataTracker.startTracking(LEVEL, 0);
        this.dataTracker.startTracking(MALE, true);
        this.dataTracker.startTracking(MAX_HP, -1);
        this.dataTracker.startTracking(NATURE, -1);
        this.dataTracker.startTracking(PSEUDO_NATURE, -1);
        this.dataTracker.startTracking(GROWTH, -1);
        this.dataTracker.startTracking(INTERACTION_COUNT, -1);
        this.dataTracker.startTracking(FORM, -1);
        this.dataTracker.startTracking(DYNAMAX_LEXEL, -1);
        this.dataTracker.startTracking(TRANSFORMATION, -1);
        this.dataTracker.startTracking(SPAWN_LOC, -1);
        this.dataTracker.startTracking(BREEDING_LEVELS, -1);
        this.dataTracker.startTracking(SHINY, false);
        this.dataTracker.startTracking(IV_STORAGE, new IvStorage());
        this.dataTracker.startTracking(EV_STORAGE, new EvStorage());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        if (this.dataTracker.get(OWNER_UUID).isPresent()) {
            tag.putUuid("ownerUuid", this.dataTracker.get(OWNER_UUID).get());
        }

        tag.putBoolean("boss", get(BOSS));
        tag.putString("pixelmonId", getPixelmonId().toString());
        tag.putInt("level", get(LEVEL));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        if (tag.getKeys().contains("ownerUuid")) {
            this.setOwnerUuid(tag.getUuid("ownerUuid"));
        }

        if (tag.getKeys().contains("level")) {
            set(BOSS, tag.getBoolean("boss"));
            set(PIXELMON_ID, new Identifier(tag.getString("pixelmonId")));
            set(LEVEL, tag.getInt("level"));
        }
    }

    private void setPixelmonId(Identifier pixelmonId) {
        this.dataTracker.set(PIXELMON_ID, pixelmonId);
    }

    private void setOwnerUuid(UUID uuid) {
        if (uuid != null) {
            this.dataTracker.set(OWNER_UUID, Optional.of(uuid));
        } else {
            this.dataTracker.set(OWNER_UUID, Optional.empty());
        }
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityTag);

        set(BOSS, getLevel() >= 20 && random.nextFloat() < 0.05F);
        set(LEVEL, 69420);

        BlockPos pos = this.getBlockPos();
        Biome biome = world.getBiome(pos);


        if (this.getPixelmonId() == MISSING) {
            for (Identifier pokedex : DataLoaders.PIXELMON_MANAGER.getPixelmon().keySet()) {
                // FIXME: temporarily randomise pixelmon spawns
                if (random.nextInt(10) == 5) {
                    this.setup(pokedex);
                    return entityData;
                }
            }
            this.setup(MISSING);
        }
        return entityData;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new LookAroundGoal(this));
        this.goalSelector.add(1, new WanderAroundGoal(this, 0.4d));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PixelmonEntity.class, 10));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 4));
    }

    public static DefaultAttributeContainer.Builder createPixelmonAttributes() { //TODO: finish
        return createMobAttributes();
    }

    private <T> T get(TrackedData<T> trackedData) {
        return this.getDataTracker().get(trackedData);
    }

    private <T> void set(TrackedData<T> trackedData, T data) {
        this.getDataTracker().set(trackedData, data);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        PixelmonEntity pixelmonEntity = new PixelmonEntity(getType(), world);
        pixelmonEntity.setup(getPixelmonId());
        return pixelmonEntity;
    }

    @Override
    public EntityType<PixelmonEntity> getType() {
        return (EntityType<PixelmonEntity>) super.getType();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        //TODO:
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public Identifier getPixelmonId() {
        return this.dataTracker.get(PIXELMON_ID);
    }

    public PokedexEntry getPokedexEntry() {
        return DataLoaders.PIXELMON_MANAGER.getPixelmon().get(getPixelmonId());
    }

    public String getNickname() {
        return "nickname"; //TODO:
//        return OpenPixelmonTranslator.createTranslation(pokedexEntry.name).getString();
    }

    public boolean isWild() {
        return this.dataTracker.get(OWNER_UUID).isPresent();
    }

    public int getHp() {
        return (int) getHealth();
    }

    public ServerPlayerEntity getOwner() {
        if (this.dataTracker.get(OWNER_UUID).isPresent()) {
            return world.getServer().getPlayerManager().getPlayer(this.dataTracker.get(OWNER_UUID).get()); //TODO
        }
        return null;
    }

    public int getLevel() {
        return get(LEVEL);
    }

    public boolean isMale() {
        return get(MALE);
    }

    public int getMaxHp() {
        return get(MAX_HP);
    }

    public boolean isBoss() {
        return get(BOSS);
    }
}
