package me.martyalana.openpixelmon.entity;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("EntityConstructor")
public class PokeballEntity extends ThrownEntity implements IAnimatable {

	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.pokeball.start_catch", true);

	private final AnimationFactory factory = new AnimationFactory(this);

	public PokeballEntity(EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	public PokeballEntity(World world, PlayerEntity user) {
		super(Entities.POKEBALL_ENTITY, user, world);
	}

	public PokeballEntity(double x, double y, double z, ClientWorld world) {
		super(Entities.POKEBALL_ENTITY, x, y, z, world);
	}

	@Override
	protected void initDataTracker() {
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
	}

	private <P extends IAnimatable> PlayState animationPredicate(AnimationEvent<P> event) {
		event.getController().setAnimation(IDLE_ANIMATION);
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
