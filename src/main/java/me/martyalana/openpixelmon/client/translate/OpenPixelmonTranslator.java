package me.martyalana.openpixelmon.client.translate;

import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class OpenPixelmonTranslator {
	public static TranslatableText createTranslation(Identifier pixelmonName) {
		return new TranslatableText("entity." + pixelmonName.getNamespace() + "." + pixelmonName.getPath());
	}
}
