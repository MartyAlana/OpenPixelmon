package me.marty.openpixelmon.sound;

import me.marty.openpixelmon.OpenPixelmon;
import net.minecraft.client.sound.MusicType;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds {

    public static final SoundEvent BATTLE_1 = registerSound("music.battle_1");
    public static final SoundEvent BATTLE_2 = registerSound("music.battle_2");

    public static final SoundEvent AMBIENT_1 = registerSound("music.ambient_1");
    public static final SoundEvent AMBIENT_2 = registerSound("music.ambient_2");
    public static final SoundEvent AMBIENT_4 = registerSound("music.ambient_4");

    public static final SoundEvent MENU = registerSound("music.menu");

    public static final MusicSound AMBIENT_1_LOOP = MusicType.createIngameMusic(AMBIENT_1);
    public static final MusicSound AMBIENT_2_LOOP = MusicType.createIngameMusic(AMBIENT_2);
    public static final MusicSound AMBIENT_4_LOOP = MusicType.createIngameMusic(AMBIENT_4);

    public static final MusicSound BATTLE_1_LOOP = MusicType.createIngameMusic(BATTLE_1);
    public static final MusicSound BATTLE_2_LOOP = MusicType.createIngameMusic(BATTLE_2);

    public static final MusicSound MENU_LOOP = MusicType.createIngameMusic(MENU);

    private static SoundEvent registerSound(String id) {
        Identifier identifier = OpenPixelmon.id(id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    public static void initialize() {
    }
}
