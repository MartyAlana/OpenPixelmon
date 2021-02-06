package me.marty.openpixelmon.client.render.entity;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.client.model.studiomdl.loader.LazySMDContext;
import me.marty.openpixelmon.client.model.studiomdl.loader.SMDReader;
import me.marty.openpixelmon.client.model.studiomdl.loader.SmdModel;
import me.marty.openpixelmon.compatibility.OtherModCompat;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerationsPixelmonRenderer extends EntityRenderer<PixelmonEntity> {

	public Map<String, Pair<Identifier, LazySMDContext>> rendererInfoMap = new Object2ObjectOpenHashMap<>();

	public GenerationsPixelmonRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		OpenPixelmon.LOGGER.info("Loading Pixelmon Generations Models");
		for (String pokemon : loopAssetDir("assets/pixelmon/models/pokemon")) {
			if (OtherModCompat.INSTANCE.getPixelmonModel("models/pokemon/" + pokemon + "/" + pokemon + ".pqc") == null) {
				OpenPixelmon.LOGGER.warn(pokemon + " could not be loaded!");
			} else {
				Identifier pixelmonTexture = new Identifier("pixelmon", "textures/pokemon/" + pokemon + ".png");
				MinecraftClient.getInstance().getTextureManager().registerTexture(pixelmonTexture, OtherModCompat.INSTANCE.load(pixelmonTexture));
				rendererInfoMap.put(pokemon, new Pair<>(pixelmonTexture, SMDReader.createLazyModel("pokemon/" + pokemon)));
			}
		}
	}

	@Override
	public void render(PixelmonEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		Pair<Identifier, LazySMDContext> pair = rendererInfoMap.get(entity.getPixelmonId().getPath());
		LazySMDContext modelFile = pair.getRight();
		Identifier modelTexture = pair.getLeft();

		matrices.push();
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));
		matrices.scale(0.02f, 0.02f, 0.02f);
		SmdModel.render(matrices, modelFile.getContext(), modelTexture, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(modelTexture)));
		matrices.pop();
		PixelmonEntityRenderer.renderPixelmonInfo(entity, getFontRenderer(), dispatcher, matrices, light, vertexConsumers);
	}

	@Override
	public Identifier getTexture(PixelmonEntity entity) {
		return rendererInfoMap.get(entity.getPixelmonId().getPath()).getLeft();
	}

	public static List<String> loopAssetDir(String rootDir) {
		Path root = OtherModCompat.INSTANCE.root.getPath("/" + rootDir);
		try {
			return Files.list(root).map(path -> root.relativize(path).toString()).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
