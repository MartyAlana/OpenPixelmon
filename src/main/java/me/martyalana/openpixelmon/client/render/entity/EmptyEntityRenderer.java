package me.martyalana.openpixelmon.client.render.entity;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class EmptyEntityRenderer extends EntityRenderer<LivingEntity> {

	private static final Identifier TEXTURE = new Identifier("extures/entity/fox/fox.png");

	public EmptyEntityRenderer(EntityRenderDispatcher dispatcher, EntityRendererRegistry.Context context) {
		super(dispatcher);
	}

	@Override
	public Identifier getTexture(LivingEntity entity) {
		return TEXTURE;
	}
}
