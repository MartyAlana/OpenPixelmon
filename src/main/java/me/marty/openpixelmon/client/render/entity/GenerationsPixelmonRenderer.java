package me.marty.openpixelmon.client.render.entity;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.client.model.studiomdl.loader.SmdModel;
import me.marty.openpixelmon.client.model.studiomdl.loader.SmdReader;
import me.marty.openpixelmon.compatibility.PixelmonAssetProvider;
import me.marty.openpixelmon.compatibility.PixelmonGenerationsCompatibility;
import me.marty.openpixelmon.config.OpenPixelmonConfig;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerationsPixelmonRenderer extends EntityRenderer<PixelmonEntity> {

    public static Map<String, Pair<Identifier, Lazy<SmdModel>>> rendererInfoMap = new Object2ObjectOpenHashMap<>();

    public GenerationsPixelmonRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        new Thread(() -> {
            List<String> pixelmons = loopModels();
            for (String pixelmon : pixelmons) {
                if (PixelmonAssetProvider.ASSET_PROVIDERS.get(0).getPixelmonModel("models/pokemon/" + pixelmon + "/" + pixelmon + ".pqc") == null) { // FIXME: hacks
                    OpenPixelmon.LOGGER.warn(pixelmon + " could not be loaded!");
                } else {
                    Identifier pixelmonTexture = new Identifier("pixelmon", "textures/pokemon/" + pixelmon + ".png");
                    MinecraftClient.getInstance().getTextureManager().registerTexture(pixelmonTexture, ((PixelmonGenerationsCompatibility) PixelmonAssetProvider.ASSET_PROVIDERS.get(0)).load(pixelmonTexture));
                    GenerationsPixelmonRenderer.rendererInfoMap.put(pixelmon, new Pair<>(pixelmonTexture, SmdReader.createLazyModel("pokemon/" + pixelmon)));
                }
            }
        }).start();
    }

    @Override
    public void render(PixelmonEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (MinecraftClient.getInstance().cameraEntity.getBlockPos().isWithinDistance(entity.getBlockPos(), OpenPixelmonConfig.pixelmonRenderDistance)) {
            Pair<Identifier, Lazy<SmdModel>> pair = rendererInfoMap.get(entity.getPixelmonId().getPath());
            if (pair == null) {
//                throw new RuntimeException("There is a corrupt pixelmon in your world! please report this error");
                return;
            }
            Lazy<SmdModel> modelFile = pair.getRight();
            Identifier modelTexture = pair.getLeft();

            SmdModel.render(matrices, modelFile.get(), modelTexture, vertexConsumers, light);

            PixelmonEntityRenderer.renderPixelmonInfo(entity, getTextRenderer(), dispatcher, matrices, light, vertexConsumers);
        }
    }

    @Override
    public Identifier getTexture(PixelmonEntity entity) {
        return rendererInfoMap.get(entity.getPixelmonId().getPath()).getLeft();
    }

    /**
     * @deprecated unsure of purpose
     */
    @Deprecated
    private static List<String> loopModels() {
        Path root = ((PixelmonGenerationsCompatibility) PixelmonAssetProvider.ASSET_PROVIDERS.get(0)).root.getPath("assets/pixelmon/models/pokemon");
        try {
            return Files.list(root).map(path -> root.relativize(path).toString()).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
