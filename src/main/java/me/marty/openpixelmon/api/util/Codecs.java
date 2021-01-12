package me.marty.openpixelmon.api.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.UnboundedMapCodec;

import java.util.Arrays;
import java.util.Map;

public class Codecs {

	public static <A> Codec<A[]> ofArray(Codec<A> codec, A... empty) {
		return codec
				.listOf()
				.xmap(list -> list.toArray(list.toArray(empty)), Arrays::asList);
	}

	public static <K, V> Codec<Map<K, V>> ofMap(Codec<K> keyCodec, Codec<V> valueCodec) {
		return new UnboundedMapCodec<>(keyCodec, valueCodec);
	}

	public static <E extends Enum<E>> Codec<E> ofEnum(Class<E> type) {
		return Codec.STRING
				.xmap(name -> Enum.valueOf(type, name), Enum::name);
	}
}
