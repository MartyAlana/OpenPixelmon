package me.marty.openpixelmon.api.generation;

import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.Map;

public class PixelmonGeneration {

	public String displayName;
	public Map<Identifier, PokedexEntry> pokedexEntries = new Object2ObjectOpenHashMap<>();

	public static class Deserializer implements JsonDeserializer<PixelmonGeneration> {
		@Override
		public PixelmonGeneration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			String[] rawPokemonIdentifiers = PixelmonGenerationManager.GSON.fromJson(jsonObject.get("pixelmon").getAsJsonArray(), String[].class);

			PixelmonGeneration generation = new PixelmonGeneration();
			for (String rawPokemonIdentifier : rawPokemonIdentifiers) {
				Identifier identifier = new Identifier(rawPokemonIdentifier);
				generation.pokedexEntries.put(identifier, PixelmonGenerationManager.loadPixelmon(identifier));
			}
			generation.displayName = jsonObject.get("displayName").getAsString();
			PixelmonGenerationManager.GENERATION_MAP.put(new Identifier(jsonObject.get("id").getAsString()), generation);
			return generation;
		}
	}
}
