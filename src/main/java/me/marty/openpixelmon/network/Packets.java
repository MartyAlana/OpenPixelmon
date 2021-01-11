package me.marty.openpixelmon.network;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class Packets {

	public static final Identifier SYNC_PIXELMON = OpenPixelmon.id("sync_pixelmon");
	public static final Identifier SYNC_PP = OpenPixelmon.id("sync_pp");
}
