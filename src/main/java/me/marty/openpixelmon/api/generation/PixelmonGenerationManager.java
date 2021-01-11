package me.marty.openpixelmon.api.generation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class PixelmonGenerationManager {

	public static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(PixelmonGeneration.class, new PixelmonGeneration.Deserializer())
			.create();
	public static final Map<Identifier, PixelmonGeneration> GENERATION_MAP = new Object2ObjectOpenHashMap<>();

	public static void loadPixelmonGeneration(Reader reader) {
		GSON.fromJson(reader, PixelmonGeneration.class);
	}

	public static void loadDefaultGenerations() {
		loadPixelmonGeneration(readResource("generations/generation1.json"));
		OpenPixelmon.LOGGER.info("Default Pixelmon Generations have loaded");
	}

	public static Reader readResource(String namespace, String path) {
		return new InputStreamReader(PixelmonGenerationManager.class.getResourceAsStream("/data/" + namespace + "/" + path));
	}

	public static Reader readResource(String path) {
		return readResource("open_pixelmon", path);
	}

	public static PokedexEntry loadPixelmon(Identifier rawPokemonIdentifier) {
		return GSON.fromJson(readResource(rawPokemonIdentifier.getNamespace(), "pixelmon/" + rawPokemonIdentifier.getPath() + ".json"), PokedexEntry.class);
	}

	public Map<Identifier, PokedexEntry> getAllPokemonData() {
		return GENERATION_MAP.get("generation_1").pokedexEntries; //TODO: unhardcode for datapack generations
	}
}
