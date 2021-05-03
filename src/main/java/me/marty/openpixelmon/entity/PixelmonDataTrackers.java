package me.marty.openpixelmon.entity;

import me.marty.openpixelmon.api.pixelmon.EvStorage;
import me.marty.openpixelmon.api.pixelmon.IvStorage;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

/**
 * Used for tracking data Minecraft does not in vanilla
 */
public class PixelmonDataTrackers {

    public static final TrackedDataHandler<Identifier> IDENTIFIER = new TrackedDataHandler<Identifier>() {
        public void write(PacketByteBuf buf, Identifier id) {
            buf.writeIdentifier(id);
        }

        public Identifier read(PacketByteBuf buf) {
            return buf.readIdentifier();
        }

        public Identifier copy(Identifier id) {
            return id;
        }
    };

    public static final TrackedDataHandler<IvStorage> IVS = new TrackedDataHandler<IvStorage>() {
        @Override
        public void write(PacketByteBuf buf, IvStorage value) {
            buf.writeInt(value.hp);
            buf.writeInt(value.att);
            buf.writeInt(value.def);
            buf.writeInt(value.spAtt);
            buf.writeInt(value.spDef);
            buf.writeInt(value.speed);
        }

        @Override
        public IvStorage read(PacketByteBuf buf) {
            IvStorage ivStorage = new IvStorage();
            ivStorage.hp = buf.readInt();
            ivStorage.att = buf.readInt();
            ivStorage.def = buf.readInt();
            ivStorage.spAtt = buf.readInt();
            ivStorage.spDef = buf.readInt();
            ivStorage.speed = buf.readInt();
            return ivStorage;
        }

        @Override
        public IvStorage copy(IvStorage value) {
            return value.copy();
        }
    };

    public static final TrackedDataHandler<EvStorage> EVS = new TrackedDataHandler<EvStorage>() {
        @Override
        public void write(PacketByteBuf buf, EvStorage value) {
            buf.writeInt(value.hp);
            buf.writeInt(value.att);
            buf.writeInt(value.def);
            buf.writeInt(value.spAtt);
            buf.writeInt(value.spDef);
            buf.writeInt(value.speed);
        }

        @Override
        public EvStorage read(PacketByteBuf buf) {
            EvStorage evStorage = new EvStorage();
            evStorage.hp = buf.readInt();
            evStorage.att = buf.readInt();
            evStorage.def = buf.readInt();
            evStorage.spAtt = buf.readInt();
            evStorage.spDef = buf.readInt();
            evStorage.speed = buf.readInt();
            return evStorage;
        }

        @Override
        public EvStorage copy(EvStorage value) {
            return value.copy();
        }
    };

    public static void initialize() {
        TrackedDataHandlerRegistry.DATA_HANDLERS.add(IDENTIFIER);
    }
}
