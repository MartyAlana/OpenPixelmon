package me.marty.openpixelmon.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class PixelmonManager extends JsonDataLoader {

	private static final Gson GSON = new Gson();
	private final BiMap<Identifier, PokedexEntry> pixelmon = HashBiMap.create();

	public PixelmonManager() {
		super(GSON, "pixelmon");
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {
		pixelmon.clear();
		for (Identifier identifier : loader.keySet()) {
			pixelmon.put(identifier, GSON.fromJson(loader.get(identifier), PokedexEntry.class));
		}
		OpenPixelmon.LOGGER.info("Loaded {} Pixelmon", pixelmon.size());
	}

	public BiMap<Identifier, PokedexEntry> getPixelmon() {
		return pixelmon;
	}
}
