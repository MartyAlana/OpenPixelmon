package me.marty.openpixelmon.client.model.studiomdl.loader;

import dev.thecodewarrior.binarysmd.formats.SMDBinaryReader;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFile;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.client.model.studiomdl.animation.AnimationData;
import me.marty.openpixelmon.client.model.studiomdl.animation.Animator;
import me.marty.openpixelmon.compatibility.PixelmonAssetProvider;
import me.marty.openpixelmon.compatibility.PixelmonGenerationsCompatibility;
import net.minecraft.util.Lazy;
import org.apache.commons.io.IOUtils;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SmdReader {
    public static Lazy<SmdModel> createLazyModel(String location) {
        return new Lazy<>(() -> SmdReader.readPixelmonModel(location));
    }

    private static SmdModel readPixelmonModel(String location) {
        try {
            return new SmdModel("assets/generations/models/" + location, parseInfo(location));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SMDFile safeReadFile(String location) {
        try (MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(PixelmonAssetProvider.ASSET_PROVIDERS.get(0).getPixelmonModel("models/" + location))) {
            return new SMDBinaryReader().read(unpacker);
        } catch (IOException e) {
            OpenPixelmon.LOGGER.fatal("Unable to read model!");
            e.printStackTrace();
        }
        return null;
    }

    private static SmdModel.Info parseInfo(String location) throws IOException {
        String[] slashSplit = location.split("/");
        String infoFileLocation = "models/" + location + "/" + slashSplit[slashSplit.length - 1] + ".pqc"; //Returns pokemon name for example "pokemon/abra" returns "abra"
        if (((PixelmonGenerationsCompatibility) PixelmonAssetProvider.ASSET_PROVIDERS.get(0)).getPixelmonInfo(infoFileLocation) == null) {
            OpenPixelmon.LOGGER.warn(location + " could not be loaded!");
            return null;
        }
        String[] infoFileProperties = IOUtils.toString(((PixelmonGenerationsCompatibility) PixelmonAssetProvider.ASSET_PROVIDERS.get(0)).getPixelmonInfo(infoFileLocation), StandardCharsets.UTF_8).replace("$", "").replace("\r", "").replace("\t", "").split("\n");

        return parseAnimationData(infoFileProperties, location);
    }

    private static SmdModel.Info parseAnimationData(String[] infoFileProperties, String location) {
        Map<String, AnimationData> animationDataMap = new HashMap<>();
        String bodyFileLocation = null;
        float scale = 0.02f;

        for (String infoFileProperty : infoFileProperties) {
            String[] split = infoFileProperty.split(" ");
            String property = split[0];
            String value = split[1];
            switch (property) {
                case "body" -> bodyFileLocation = value;
                case "anim" -> {
                    String animPath = split[2];
                    SMDFile animation = safeReadFile(location + "/" + animPath);
                    if (animation == null) {
                        throw new RuntimeException("Couldn't read animation!");
                    }
                    animationDataMap.put(animPath, Animator.getAnimData(animation));
                }
                case "scale" -> scale = Float.parseFloat(value);
                default -> OpenPixelmon.LOGGER.warn("We dont know how to handle the property: " + property);
            }
        }

        return new SmdModel.Info(
                safeReadFile(location + "/" + bodyFileLocation),
                scale / 10,
                animationDataMap
        );
    }
}
