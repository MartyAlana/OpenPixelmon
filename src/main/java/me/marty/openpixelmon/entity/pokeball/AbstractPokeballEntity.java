package me.marty.openpixelmon.entity.pokeball;

import me.marty.openpixelmon.api.component.EntityComponents;
import me.marty.openpixelmon.entity.data.PartyEntry;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.item.OpenPixelmonItems;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
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
	private boolean catchingPixelmon;

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
		if(!catchingPixelmon) {
			BlockPos pos = blockHitResult.getBlockPos();
			kill();
			world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(getItem())));
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if(entityHitResult.getEntity() instanceof PixelmonEntity) {
			this.catchingPixelmon = true;
			setNoGravity(true);
			if(!getEntityWorld().isClient()){
				EntityComponents.PARTY_COMPONENT.get(getOwner()).getParty().add((ServerPlayerEntity) getOwner(), new PartyEntry((PixelmonEntity) entityHitResult.getEntity(), (PokeballItem) OpenPixelmonItems.POKE_BALL));
				EntityComponents.PARTY_COMPONENT.sync(getOwner());
			}
			entityHitResult.getEntity().kill();
			setVelocity(0, 0.5, 0);
		} else {
			Vec3d pos = entityHitResult.getPos();
			kill();
			entityHitResult.getEntity().damage(DamageSource.GENERIC, 1);
			world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(getItem())));
		}
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
