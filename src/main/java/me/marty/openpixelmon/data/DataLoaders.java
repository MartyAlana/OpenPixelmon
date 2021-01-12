package me.marty.openpixelmon.data;

import me.marty.openpixelmon.api.pixelmon.PokedexEntry;
import net.minecraft.util.Identifier;

import java.util.Collection;

public class DataLoaders {

	public static final GenerationManager GENERATION_MANAGER = new GenerationManager();
	public static final MoveManager MOVE_MANAGER = new MoveManager();
	public static final PixelmonManager PIXELMON_MANAGER = new PixelmonManager();
	public static final PixelmonTypeManager PIXELMON_TYPE_MANAGER = new PixelmonTypeManager();

	public Collection<PokedexEntry> getAllPokemon() {
		return PIXELMON_MANAGER.getPixelmon().values();
	}

	public static Identifier getIdentifier(PokedexEntry entry) {
		for (Identifier identifier : PIXELMON_MANAGER.getPixelmon().keySet()) {
			if(entry == PIXELMON_MANAGER.getPixelmon().get(identifier)){
				return identifier;
			}
		}
		throw new RuntimeException("Unable to find the identifier from an entry");
	}
}
