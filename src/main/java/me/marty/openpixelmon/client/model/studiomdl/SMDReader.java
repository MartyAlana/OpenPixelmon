package me.marty.openpixelmon.client.model.studiomdl;

import dev.thecodewarrior.binarysmd.formats.SMDBinaryReader;
import dev.thecodewarrior.binarysmd.studiomdl.NodesBlock;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFile;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFileBlock;
import dev.thecodewarrior.binarysmd.studiomdl.SkeletonBlock;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.compatibility.OtherModCompat;
import org.apache.commons.io.IOUtils;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SMDReader {
	public static LazySMDContext createLazyModel(String location) {
		return new LazySMDContext(location);
	}

	public static SMDContext readPokemonMdl(String location) {
		try {
			return new SMDContext("assets/generations/models/" + location, parseInfo(location));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static SMDFile safeReadFile(String location) {
		try (MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(OtherModCompat.INSTANCE.getPixelmonModel("models/" + location))) {
			return new SMDBinaryReader().read(unpacker);
		} catch (IOException e) {
			OpenPixelmon.LOGGER.fatal("Unable to read model!");
			e.printStackTrace();
		}
		return null;
	}

	private static SMDContext.Info parseInfo(String location) throws IOException {
		String[] slashSplit = location.split("/");
		String infoFileLocation = "models/" + location + "/" + slashSplit[slashSplit.length - 1] + ".pqc"; //Returns pokemon name for example "pokemon/abra" returns "abra"
		if(OtherModCompat.INSTANCE.getPixelmonInfo(infoFileLocation) == null){
			OpenPixelmon.LOGGER.warn(location + " could not be loaded!");
			return null;
		}
		String[] infoFileProperties = IOUtils.toString(OtherModCompat.INSTANCE.getPixelmonInfo(infoFileLocation), StandardCharsets.UTF_8).replace("$", "").replace("\r", "").replace("\t", "").split("\n");

		String bodyFileLocation = null;
		String walkAnim = null;
		String idleAnim = null;

		for (String infoFileProperty : infoFileProperties) {
			String[] split = infoFileProperty.split(" ");
			String property = split[0];
			String value = split[1];
			if (property.equals("body")) {
				bodyFileLocation = value;
			} else if (property.equals("anim")) {
				if (value.equals("walk")) {
					walkAnim = split[2];
				} else if (value.equals("idle")) {
					idleAnim = split[2];
				} else {
					OpenPixelmon.LOGGER.warn("Unknown animation type: " + value);
				}
			}
		}
		return new SMDContext.Info(
				safeReadFile(location + "/" + bodyFileLocation),
				1,
				parseAnimationData(location, walkAnim, idleAnim)
		);
	}

	private static Map<String, SMDContext.AnimationData> parseAnimationData(String location, String walkAnim, String idleAnim) {
		Map<String, SMDContext.AnimationData> animationDataMap = new HashMap<>();
		if(walkAnim != null){
			SMDFile walkingAnimation = safeReadFile(location + "/" + walkAnim);
			animationDataMap.put(walkAnim, getAnimData(walkingAnimation));
		}
		if(idleAnim != null){
			SMDFile idleAnimation = safeReadFile(location + "/" + idleAnim);
			animationDataMap.put(idleAnim, getAnimData(idleAnimation));
		}
		return animationDataMap;
	}

	private static SMDContext.AnimationData getAnimData(SMDFile animation) {
		SMDContext.AnimationData data = new SMDContext.AnimationData();
		for (SMDFileBlock block : animation.blocks) {
			if (block instanceof NodesBlock) {
				NodesBlock nodeBlock = (NodesBlock) block;
				data.bones = nodeBlock.bones;
			}
			if (block instanceof SkeletonBlock) {
				SkeletonBlock nodeBlock = (SkeletonBlock) block;
				data.keyframes = nodeBlock.keyframes;
			}
		}
		return data;
	}
}
