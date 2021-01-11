package me.marty.openpixelmon.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class PixelmonManager extends JsonDataLoader {

	private static final Gson GSON = new Gson();
	private final Map<Identifier, PokedexEntry> pixelmon = new Object2ObjectOpenHashMap<>();

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

	public Map<Identifier, PokedexEntry> getPixelmon() {
		return pixelmon;
	}
}
