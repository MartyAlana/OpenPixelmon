package me.marty.openpixelmon.client.model.entity;

import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PixelmonModel extends AnimatedGeoModel<PixelmonEntity> {

	public static final PixelmonModel INSTANCE = new PixelmonModel();

	private PixelmonModel() {
	}

	@Override
	public Identifier getModelLocation(PixelmonEntity object) {
		Identifier identifier = object.getPokedexEntry().getIdentifier();
		return new Identifier(identifier.getNamespace(), "geo/pixelmon/" + identifier.getPath() + "/" + identifier.getPath() + ".geo.json");
	}

	@Override
	public Identifier getTextureLocation(PixelmonEntity object) {
		Identifier identifier = object.getPokedexEntry().getIdentifier();
		return new Identifier(identifier.getNamespace(), "textures/entity/pixelmon/" + identifier.getPath() + "/" + identifier.getPath() + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(PixelmonEntity animatable) {
		Identifier identifier = animatable.getPokedexEntry().getIdentifier();
		return new Identifier(identifier.getNamespace(), "animations/" + identifier.getPath() + ".animation.json");
	}
}
