package me.marty.openpixelmon.entity.pixelmon;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PokeGeneration {

	public static final Map<Identifier, PokedexEntry> CACHED_POKEDEX_DATA_MAP = new Object2ObjectOpenHashMap<>();
	public static final List<PokeGeneration> GENERATIONS = new ObjectArrayList<>();
	public Identifier name;
	public Map<Identifier, PokedexEntry> pokedexDataMap;

	public PokeGeneration(Identifier generationName, Map<Identifier, PokedexEntry> pokedexDataMap) {
		this.name = generationName;
		this.pokedexDataMap = pokedexDataMap;
		GENERATIONS.add(this);
	}

	public static Collection<PokedexEntry> getAllPixelmon() {
		if(CACHED_POKEDEX_DATA_MAP.values().size() == 0) {
			for (PokeGeneration generation : GENERATIONS) {
				CACHED_POKEDEX_DATA_MAP.putAll(generation.pokedexDataMap);
			}
		}
		return CACHED_POKEDEX_DATA_MAP.values();
	}

	public Collection<PokedexEntry> getPixelmon() {
		return pokedexDataMap.values();
	}

	@NotNull
	public static PokedexEntry getPixelmonById(Identifier pixelmonName) {
		for (PokedexEntry pokedexEntry : getAllPixelmon()) {
			if(pokedexEntry.name.equals(pixelmonName)){
				return pokedexEntry;
			}
		}
		throw new RuntimeException("Couldnt get pixelmon from id: " + pixelmonName);
	}

	@NotNull
	public static PokedexEntry getPixelmonByType(EntityType<? extends PixelmonEntity> pixelmonName) {
		for (PokedexEntry pokedexEntry : getAllPixelmon()) {
			if(pokedexEntry.type.equals(pixelmonName)){
				return pokedexEntry;
			}
		}
		throw new RuntimeException("Couldnt get pixelmon from id: " + pixelmonName);
	}

	public static class Builder {

		public Identifier name;
		public Map<Identifier, PokedexEntry> pokedexDataMap;

		public Builder(Identifier name) {
			this.name = name;
			this.pokedexDataMap = new Object2ObjectOpenHashMap<>();
		}

		public Builder addPixelmon(PokedexEntry data) {
			pokedexDataMap.put(data.name, data);
			return this;
		}

		public PokeGeneration build() {
			return new PokeGeneration(name, pokedexDataMap);
		}
	}
}
