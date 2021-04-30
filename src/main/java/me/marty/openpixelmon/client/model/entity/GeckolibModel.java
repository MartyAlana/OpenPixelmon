package me.marty.openpixelmon.client.model.entity;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GeckolibModel<T extends IAnimatable> extends AnimatedGeoModel<T> {

    private final String texture;
    public String modelName;

    public GeckolibModel(String modelName, String texture) {
        this.modelName = modelName;
        this.texture = texture;
    }

    @Override
    public Identifier getModelLocation(T t) {
        return OpenPixelmon.id("geo/" + modelName + ".geo.json");
    }

    @Override
    public Identifier getTextureLocation(T t) {
        return OpenPixelmon.id("textures/entity/" + texture + ".png");
    }

    @Override
    public Identifier getAnimationFileLocation(T t) {
        return OpenPixelmon.id("animations/" + modelName + ".animation.json");
    }

    public static GeckolibModel<PixelmonEntity> of(String pixelmonName) {
        return new GeckolibModel<>(pixelmonName, "pixelmon/" + pixelmonName + "/" + pixelmonName);
    }
}
