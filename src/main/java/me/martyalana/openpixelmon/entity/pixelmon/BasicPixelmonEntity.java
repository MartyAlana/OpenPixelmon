package me.martyalana.openpixelmon.entity.pixelmon;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BasicPixelmonEntity extends AnimalEntity {

	public BasicPixelmonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder createPixelmonAttributes() { //TODO: finish
		return createMobAttributes();
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return new BasicPixelmonEntity(getType(), world);
	}

	// Hack to get everywhere else to shut up about casting.
	@Override
	public EntityType<BasicPixelmonEntity> getType() {
		return (EntityType<BasicPixelmonEntity>) super.getType();
	}
}
