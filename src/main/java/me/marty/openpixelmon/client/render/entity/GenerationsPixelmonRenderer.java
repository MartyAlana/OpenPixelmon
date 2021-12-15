package me.marty.openpixelmon.client.render.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import me.marty.openpixelmon.client.model.smd.loader.SmdReader;
import me.marty.openpixelmon.client.render.renderer.CompiledModel;
import me.marty.openpixelmon.compatibility.PixelmonAssetProvider;
import me.marty.openpixelmon.compatibility.PixelmonGenerationsCompatibility;
import me.marty.openpixelmon.config.OpenPixelmonConfig;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import java.util.HashMap;
import java.util.Map;

public class GenerationsPixelmonRenderer extends EntityRenderer<PixelmonEntity> {

    private static final Map<Identifier, CompiledModel> MODEL_MAP = new HashMap<>();
    private static final Map<Identifier, Identifier> TEXTURE_MAP = new HashMap<>();

    public GenerationsPixelmonRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(PixelmonEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (MinecraftClient.getInstance().cameraEntity.getBlockPos().isWithinDistance(entity.getBlockPos(), OpenPixelmonConfig.pixelmonRenderDistance)) {
            Identifier texture = getTexture(entity);
            CompiledModel model = getModel(entity.getPixelmonId());
            matrices.push();
            matrices.scale(model.smdInfo.scale(), model.smdInfo.scale(), model.smdInfo.scale());
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.enableDepthTest();
            model.render(matrices);
            RenderSystem.disableDepthTest();
            matrices.pop();

            PixelmonEntityRenderer.renderPixelmonInfo(entity, getTextRenderer(), dispatcher, matrices, light, vertexConsumers);
        }
    }

    private CompiledModel getModel(Identifier pixelmonId) {
        return MODEL_MAP.computeIfAbsent(pixelmonId, identifier -> SmdReader.readPixelmonModel(identifier.getPath()).compile());
    }

    @Override
    public Identifier getTexture(PixelmonEntity entity) {
        return TEXTURE_MAP.computeIfAbsent(entity.getPixelmonId(), this::registerTexture);
    }

    private Identifier registerTexture(Identifier pixelmonId) {
        Identifier textureId = new Identifier("pixelmon", "textures/pokemon/" + pixelmonId.getPath() + ".png");
        AbstractTexture texture = ((PixelmonGenerationsCompatibility) PixelmonAssetProvider.ASSET_PROVIDERS.get(0)).load(textureId);
        if (texture == null) {
            throw new RuntimeException("Failed to load Texture!");
        }

        MinecraftClient.getInstance().getTextureManager().registerTexture(textureId, texture);
        return textureId;
    }
}
