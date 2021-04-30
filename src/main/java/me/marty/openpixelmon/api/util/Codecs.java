package me.marty.openpixelmon.api.util;

import com.google.gson.Gson;
import com.mojang.serialization.Codec;

public class Codecs {

    private static final Gson GSON = new Gson();

    public static <A> Codec<A> withGson(Class<A> clazz) {
        return Codec.STRING.xmap(string -> GSON.fromJson(string, clazz), GSON::toJson);
    }
}
