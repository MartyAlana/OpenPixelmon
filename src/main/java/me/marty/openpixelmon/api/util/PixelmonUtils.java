package me.marty.openpixelmon.api.util;

import java.util.Random;

public class PixelmonUtils {
    private static final Random RANDOM = new Random();

    public static int randBetween(int from, int to) {
        return RANDOM.nextInt(to - from + 1) + from;
    }
}
