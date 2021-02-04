package me.marty.openpixelmon.compatibility;

import me.marty.openpixelmon.OpenPixelmon;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PixelmonGenerationsCompatibility implements ModelCompatibility{

	public FileSystem root;

	public PixelmonGenerationsCompatibility() {
		try {
			this.root = FileSystems.newFileSystem(Paths.get("compatibility/generations.jar"), (ClassLoader) null);
		}catch (Exception e) {
			OpenPixelmon.LOGGER.error("An Exception Has Occurred! Pixelmon Generations Compat will be disabled!");
			e.printStackTrace();
		}
	}

	@Override
	public InputStream getPixelmonModel(String name) {
		return getAsset("assets/pixelmon/" + name);
	}

	@Override
	public InputStream getPixelmonTexture(String name) {
		return getAsset(name);
	}

	public InputStream getPixelmonInfo(String infoFileLocation) {
		return getAsset(infoFileLocation);
	}

	private InputStream getAsset(String asset) {
		try {
			return Files.newInputStream(root.getPath(asset));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public boolean isCompatibleMod(String modName) {
		return true; //TODO: we currently assume pixelmon generations is the jar. this will cause issues.
	}
}
