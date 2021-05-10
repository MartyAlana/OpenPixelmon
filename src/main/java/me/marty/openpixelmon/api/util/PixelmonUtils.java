package me.marty.openpixelmon.api.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Util;

import java.util.Random;

/**
 * Miscellaneous Utilities used throughout Open Pixelmon
 */
public class PixelmonUtils {
    private static final Random RANDOM = new Random();

    /**
     * Generates a Random number between a high and low number
     * @param from the lowest number
     * @param to the highest number
     * @return random integer
     */
    public static int randBetween(int from, int to) {
        return RANDOM.nextInt(to - from + 1) + from;
    }

    /**
     * Used to stop a sound on the client. <h1>DO NOT</h1> call this on the server.
     * @param sound the {@link SoundEvent} you want cancelled
     * @param category the {@link SoundCategory} the sound is playing on
     */
    @Environment(EnvType.CLIENT)
    public static void stopSound(SoundEvent sound, SoundCategory category) {
        MinecraftClient.getInstance().runTasks(() -> {
            MinecraftClient.getInstance().getNetworkHandler().onStopSound(new StopSoundS2CPacket(sound.getId(), category));
            return true;
        });
    }
}
