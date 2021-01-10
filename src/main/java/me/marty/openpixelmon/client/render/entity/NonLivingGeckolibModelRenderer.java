package me.marty.openpixelmon.client.render.entity;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderer.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderer.geo.IGeoRenderer;
import software.bernie.geckolib3.util.AnimationUtils;

import java.awt.*;
import java.util.Collections;
import java.util.List;

public class NonLivingGeckolibModelRenderer<T extends Entity & IAnimatable> extends EntityRenderer<T> implements IGeoRenderer<T> {

	protected final List<GeoLayerRenderer<T>> layerRenderers = Lists.newArrayList();
	private final AnimatedGeoModel<T> modelProvider;

	public NonLivingGeckolibModelRenderer(EntityRendererFactory.Context context, AnimatedGeoModel<T> modelProvider) {
		super(context);
		this.modelProvider = modelProvider;
	}

	public static int getPackedOverlay(float uIn) {
		return OverlayTexture.getUv((float)OverlayTexture.getU(uIn), false);
	}

	public void render(T entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
		boolean shouldSit = entity.hasVehicle() && entity.getVehicle() != null;
		EntityModelData entityModelData = new EntityModelData();
		entityModelData.isSitting = shouldSit;
		entityModelData.isChild = false;
		float f = entityYaw;
		float f2 = entityYaw - f;
		float f7;
		if (shouldSit && entity.getVehicle() instanceof LivingEntity) {
			LivingEntity livingentity = (LivingEntity)entity.getVehicle();
			f = MathHelper.lerpAngleDegrees(partialTicks, livingentity.prevBodyYaw, livingentity.bodyYaw);
			f2 = entityYaw - f;
			f7 = MathHelper.wrapDegrees(f2);
			if (f7 < -85.0F) {
				f7 = -85.0F;
			}

			if (f7 >= 85.0F) {
				f7 = 85.0F;
			}

			f = entityYaw - f7;
			if (f7 * f7 > 2500.0F) {
				f += f7 * 0.2F;
			}

			f2 = entityYaw - f;
		}

		float f6 = MathHelper.lerp(partialTicks, entity.prevPitch, entity.pitch);
		float limbSwingAmount;
		if (entity.getPose() == EntityPose.SLEEPING) {
			throw new RuntimeException("H O W is an non living entity sleeping?!?!!");
		}

		f7 = this.handleRotationFloat(entity, partialTicks);
		this.applyRotations(entity, stack, f);
		limbSwingAmount = 0.0F;
		float limbSwing = 0.0F;

		AnimationEvent<?> predicate = new AnimationEvent<>(entity, limbSwing, limbSwingAmount, partialTicks, false, Collections.singletonList(entityModelData));
		this.modelProvider.setLivingAnimations(entity, this.getUniqueID(entity), predicate);

		stack.push();
		stack.translate(0.0D, 0.009999999776482582D, 0.0D);
		MinecraftClient.getInstance().getTextureManager().bindTexture(this.getTexture(entity));
		GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelLocation(entity));
		Color renderColor = this.getRenderColor(entity, partialTicks, stack, bufferIn, null, packedLightIn);
		RenderLayer renderType = this.getRenderType(entity, partialTicks, stack, bufferIn, null, packedLightIn, this.getTexture(entity));
		this.render(model, entity, partialTicks, renderType, stack, bufferIn, null, packedLightIn, getPackedOverlay(0.0F), (float)renderColor.getRed() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getAlpha() / 255.0F);
		if (!entity.isSpectator()) {
			for (GeoLayerRenderer<T> renderer : this.layerRenderers) {
				renderer.render(stack, bufferIn, packedLightIn, entity, limbSwing, limbSwingAmount, partialTicks, f7, f2, f6);
			}
		}

		stack.pop();
		super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
	}

	public Identifier getTexture(T entity) {
		return this.getTextureLocation(entity);
	}

	public GeoModelProvider<?> getGeoModelProvider() {
		return this.modelProvider;
	}

	protected void applyRotations(T entityLiving, MatrixStack matrixStackIn, float rotationYaw) {
		EntityPose pose = entityLiving.getPose();
		if (pose != EntityPose.SLEEPING) {
			matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - rotationYaw));
		}

		if (entityLiving.hasCustomName() || entityLiving instanceof PlayerEntity) {
			String s = Formatting.strip(entityLiving.getName().getString());
			if (("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof PlayerEntity) || ((PlayerEntity)entityLiving).isPartVisible(PlayerModelPart.CAPE))) {
				matrixStackIn.translate(0.0D, entityLiving.getHeight() + 0.1F, 0.0D);
				matrixStackIn.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
			}
		}
	}

	protected float handleRotationFloat(T livingBase, float partialTicks) {
		return (float)livingBase.age + partialTicks;
	}

	public Identifier getTextureLocation(T instance) {
		return this.modelProvider.getTextureLocation(instance);
	}

	static {
		AnimationController.addModelFetcher((object) -> object instanceof Entity ? (IAnimatableModel<?>) AnimationUtils.getGeoModelForEntity((Entity)object) : null);
	}
}
