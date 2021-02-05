package me.marty.openpixelmon.compatibility;

import me.marty.openpixelmon.OpenPixelmon;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PixelmonGenerationsCompatibility implements ModelCompatibility {

	public FileSystem root;

	public PixelmonGenerationsCompatibility() {
		try {
			this.root = FileSystems.newFileSystem(Paths.get("compatibility/generations.jar"), (ClassLoader) null);
		} catch (Exception e) {
			OpenPixelmon.LOGGER.error("An Exception Has Occurred! Pixelmon Generations Compat will be disabled!");
			e.printStackTrace();
		}
	}

	@Override
	public InputStream getPixelmonModel(String name) {
		return getAsset(name);
	}

	@Override
	public InputStream getPixelmonTexture(String name) {
		return getAsset(name);
	}

	public InputStream getPixelmonInfo(String infoFileLocation) {
		return getAsset(infoFileLocation);
	}

	public InputStream getAsset(String asset) {
		try {
			return Files.newInputStream(root.getPath("assets/pixelmon/" + asset));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public boolean isCompatibleMod(String modName) {
		return modName.contains("generations");
	}

	public AbstractTexture load(Identifier pixelmonTexture) {
		// Due to the generations team to be possibly the shittiest devs in the world, i have to do some guess work into figuring out what the texture's name is
		InputStream stream = doSomeGuesswork(pixelmonTexture.getPath());

		if (stream != null) {
			try {
				return new NativeImageBackedTexture(NativeImage.read(stream));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("Cannot find texture from " + pixelmonTexture);
		}
		return null;
	}

	/**
	 * Why
	 * @return texture
	 */
	private InputStream doSomeGuesswork(String path) {
		InputStream stream = getAsset(path);
		if (stream == null) {
			stream = getAsset(path.replace(".png", "-normal.png"));
		}
		if (stream == null) {
			stream = getAsset(path.replace(".png", "-neutral.png"));
		}
		if (stream == null) {
			stream = getAsset(path.replace(".png", "-natural.png"));
		}
		if (stream == null) {
			stream = getAsset(path.replace(".png", "-white.png"));
		}
		if (stream == null) {
			stream = getAsset(path.replace(".png", "-spring.png"));
		}
		if (stream == null) {
			stream = getAsset(path.replace(".png", "-strawberry-vanillacream.png"));
		}
		if (stream == null) {
			stream = getAsset(path.replace(".png", "male.png"));
		}
		if (stream == null) {
			stream = getAsset(path.replace("porygonz", "porygon-z"));
		}
		return stream;
	}
}
