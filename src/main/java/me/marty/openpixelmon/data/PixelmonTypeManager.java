package me.marty.openpixelmon.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;

public class PixelmonTypeManager extends JsonDataLoader {

	private static final Gson GSON = new Gson();

	public PixelmonTypeManager() {
		super(GSON, "elements");
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {

	}
}
