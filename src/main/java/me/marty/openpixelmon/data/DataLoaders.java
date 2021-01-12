package me.marty.openpixelmon.data;

import me.marty.openpixelmon.api.pixelmon.PokedexEntry;

import java.util.Collection;

public class DataLoaders {

	public static final GenerationManager GENERATION_MANAGER = new GenerationManager();
	public static final MoveManager MOVE_MANAGER = new MoveManager();
	public static final PixelmonManager PIXELMON_MANAGER = new PixelmonManager();
	public static final PixelmonTypeManager PIXELMON_TYPE_MANAGER = new PixelmonTypeManager();

	public Collection<PokedexEntry> getAllPokemon() {
		return PIXELMON_MANAGER.getPixelmon().values();
	}
}
