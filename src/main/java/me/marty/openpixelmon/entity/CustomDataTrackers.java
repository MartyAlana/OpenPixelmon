package me.marty.openpixelmon.entity;

import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class CustomDataTrackers {

	public static final TrackedDataHandler<Identifier> IDENTIFIER = new TrackedDataHandler<Identifier>() {
		public void write(PacketByteBuf packetByteBuf, Identifier id) {
			packetByteBuf.writeIdentifier(id);
		}

		public Identifier read(PacketByteBuf packetByteBuf) {
			return packetByteBuf.readIdentifier();
		}

		public Identifier copy(Identifier id) {
			return id;
		}
	};

	public static void initialize() {
		TrackedDataHandlerRegistry.DATA_HANDLERS.add(IDENTIFIER);
	}
}
