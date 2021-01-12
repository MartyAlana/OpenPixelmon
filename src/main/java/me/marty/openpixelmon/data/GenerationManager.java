package me.marty.openpixelmon.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.generation.PixelmonGeneration;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class GenerationManager extends JsonDataLoader {

	private static final Gson GSON = new Gson();
	private final Map<Identifier, PixelmonGeneration> generations = new Object2ObjectOpenHashMap<>();

	public GenerationManager() {
		super(GSON, "generations");
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {
		for (Identifier identifier : loader.keySet()) {
			generations.put(identifier, GSON.fromJson(loader.get(identifier), PixelmonGeneration.class));
		}
		OpenPixelmon.LOGGER.info("Loaded {} Generations", generations.size());
	}
}
