package me.marty.openpixelmon.compatibility;

import me.marty.openpixelmon.OpenPixelmon;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class PixelmonGenerationsCompatibility implements PixelmonAssetProvider {

    private static final Map<Identifier, Identifier> TRANSLATION_MAP = Map.of(
            OpenPixelmon.id("textures/item/rock_star_costume.png"), OpenPixelmon.id("textures/item/rockstar_costume.png"),
            OpenPixelmon.id("textures/item/item_finder.png"), OpenPixelmon.id("textures/item/itemfindernohit.png") //TODO: this requires a more complicated texture
    );
    public FileSystem root;

    public PixelmonGenerationsCompatibility() {
        try {
            this.root = FileSystems.newFileSystem(JAR_PATH.resolve("generations.jar"), (ClassLoader) null);
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

    /**
     * Tries to locate the path to an asset if it is in a subdirectory.
     *
     * @param path the original path of the asset
     */
    private Path fixTexturePath(Path path) {
        AtomicReference<Path> outputPath = new AtomicReference<>(path);
        if (!fileExists(path) && path.toString().contains("texture")) {
            try {
                Files.walk(path.getParent()).forEach(walkPath -> {
                    if (walkPath.getFileName().equals(path.getFileName())) {
                        outputPath.set(walkPath);
                    }
                });
            } catch (IOException ignored) {
            }

            // Try and go again but with no underscores because Pixelmon: Generations names stuff weirdly
            if (outputPath.get().equals(path) && path.getFileName().toString().contains("_")) {
                return fixTexturePath(path.getParent().resolve(path.getFileName().toString().replace("_", "")));
            }
        }

        return outputPath.get();
    }

    /**
     * For some reason, Files.exists does not work on ZipPath's. Maybe a java bug?
     */
    private boolean fileExists(Path path) {
        try {
            Files.newInputStream(path);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isCompatibleJar(String jarName) {
        return jarName.contains("generations");
    }

    @Override
    public InputStream open(ResourceType type, Identifier id) throws IOException {
        String namespace = id.getNamespace();
        String rawPath = TRANSLATION_MAP.getOrDefault(id, id).getPath().replace("textures/item/", "textures/items/"); // 1.12 -> 1.14+ path refactor
        Path path = root.getPath("assets/" + namespace + "/" + rawPath);
        return Files.newInputStream(fixTexturePath(path));
    }

    @Override
    public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
        return Collections.emptySet();
    }

    @Override
    public boolean contains(ResourceType type, Identifier id) {
        try {
            return open(type, id) != null && !id.getNamespace().endsWith(".json");
        } catch (IOException ignored) {
            // Check if we have already gone through to stop Stack Overflow
            if (id.getPath().contains("_")) {
                return contains(type, new Identifier(id.getNamespace(), toSnakeCase(id.getPath())));
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "Pixelmon Generations Asset Provider";
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

    public AbstractTexture load(Identifier pixelmonTexture) {
        // Due to the original pixelmon dev's and possibly some generation devs being the shittiest devs in the world, i have to do some guess work into figuring out what the texture's name is
        // Sorry if the original comment here had offended anyone not directly responsible for it. dont use giant hardcoded enum's next time :)
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
     *
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

    private String toSnakeCase(String path) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(path.split("_")).forEach(builder::append);
        return builder.toString();
    }
}
