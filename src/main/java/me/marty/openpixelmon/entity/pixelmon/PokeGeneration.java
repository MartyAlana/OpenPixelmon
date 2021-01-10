package me.marty.openpixelmon.entity.pixelmon;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.pixelmon.PokedexData;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Map;

public class PokeGeneration {

	public Identifier name;
	public Map<Identifier, PokedexData> pokedexDataMap;

	public PokeGeneration(Identifier generationName, Map<Identifier, PokedexData> pokedexDataMap) {
		this.name = generationName;
		this.pokedexDataMap = pokedexDataMap;
	}

	public Collection<PokedexData> getPixelmon() {
		return pokedexDataMap.values();
	}

	public PokedexData getPixelmonById(Identifier pixelmonName) {
		for (PokedexData pokedexData : getPixelmon()) {
			if(pokedexData.name.equals(pixelmonName)){
				return pokedexData;
			}
		}
		OpenPixelmon.throwError("Couldnt get pixelmon from id: " + pixelmonName);
		return null;
	}

	public static class Builder {

		public Identifier name;
		public Map<Identifier, PokedexData> pokedexDataMap;

		public Builder(Identifier name) {
			this.name = name;
			this.pokedexDataMap = new Object2ObjectOpenHashMap<>();
		}

		public Builder addPixelmon(PokedexData data) {
			pokedexDataMap.put(data.name, data);
			return this;
		}

		public PokeGeneration build() {
			return new PokeGeneration(name, pokedexDataMap);
		}
	}
}
