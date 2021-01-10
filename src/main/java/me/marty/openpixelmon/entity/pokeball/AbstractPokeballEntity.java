package me.marty.openpixelmon.entity.pokeball;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@SuppressWarnings("EntityConstructor")
public abstract class AbstractPokeballEntity extends ThrownEntity implements IAnimatable {

	private static final AnimationBuilder IDLE_ANIMATION = new AnimationBuilder().addAnimation("animation.pokeball.start_catch", true);

	private final AnimationFactory factory = new AnimationFactory(this);

	public AbstractPokeballEntity(EntityType<? extends ThrownEntity> entityType, World world) {
		super(entityType, world);
	}

	public AbstractPokeballEntity(EntityType<AbstractPokeballEntity> type, double x, double y, double z, ClientWorld world) {
		super(type, x, y, z, world);
	}

	public AbstractPokeballEntity(EntityType<AbstractPokeballEntity> type, PlayerEntity user, World world) {
		super(type, user, world);
	}

	public abstract Item getItem();

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		BlockPos pos = blockHitResult.getBlockPos();
		kill();
		world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(getItem())));
	}

	@Override
	protected void initDataTracker() {
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
	}

	private <P extends IAnimatable> PlayState animationPredicate(AnimationEvent<P> event) {
		return PlayState.STOP;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
