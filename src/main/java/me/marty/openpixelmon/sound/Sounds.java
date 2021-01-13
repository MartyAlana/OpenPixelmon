package me.marty.openpixelmon.sound;

import me.marty.openpixelmon.OpenPixelmon;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds {

	public static SoundEvent BATTLE_1 = registerSound("music.battle_1");

	public static SoundEvent AMBIENT_1 = registerSound("music.ambient_1");
	public static SoundEvent AMBIENT_2 = registerSound("music.ambient_2");
	public static SoundEvent AMBIENT_3 = registerSound("music.ambient_3");
	public static SoundEvent AMBIENT_4 = registerSound("music.ambient_4");

	private static SoundEvent registerSound(String id) {
		Identifier identifier = OpenPixelmon.id(id);
		return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
	}

	public static void initialize() {
	}
}
