package me.martyalana.openpixelmon.entity.pixelmon;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
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

	public PixelmonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createPixelmonAttributes() { //TODO: finish
		return createMobAttributes();
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new PixelmonEntity(getType(), world);
	}

	// Hack to get everywhere else to shut up about casting.
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
		if(!(source.getAttacker() instanceof PlayerEntity)) {
			return super.damage(source, amount);
		}
		return false;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	public boolean isBoss() {
		return true; //TODO:
	}

	public int getLevel() {
		return 69;
	}
}
