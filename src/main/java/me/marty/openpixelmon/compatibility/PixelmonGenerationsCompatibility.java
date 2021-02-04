package me.marty.openpixelmon.compatibility;

import net.minecraft.util.Identifier;

import java.io.InputStream;

public class PixelmonGenerationsCompatibility implements ModelCompatibility{

	public PixelmonGenerationsCompatibility() {
	}

	@Override
	public InputStream getPixelmonModel(String name) {
		return null;
	}

	@Override
	public InputStream getPixelmonTexture(String name) {
		return null;
	}

	public InputStream getPixelmonInfo(String infoFileLocation) {
		return null;
	}

	@Override
	public boolean isCompatibleMod(String modName) {
		return true; //TODO: we currently assume pixelmon generations is the jar. this will cause issues.
	}
}
