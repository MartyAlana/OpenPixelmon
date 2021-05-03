package me.marty.openpixelmon.api.pixelmon;

import net.minecraft.nbt.NbtCompound;

/**
 * Used for storing Pixelmon Effort Values (EV's)
 */
public class EvStorage {
    public static final int MAX_EVS = 252;
    public static final int MAX_TOTAL_EVS = 510;

    public int hp;
    public int att;
    public int def;
    public int spAtt;
    public int spDef;
    public int speed;

    /**
     * Adds another {@link EvStorage} to this {@link EvStorage}
     * @param evStorage the other {@link EvStorage} to add to this {@link EvStorage}
     */
    public void addEv(EvStorage evStorage) {
        int remainingEVs = this.getRemainingEVs();
        this.hp = Math.min(MAX_EVS, this.hp + Math.min(remainingEVs, evStorage.hp));
        remainingEVs = this.getRemainingEVs();
        this.att = Math.min(MAX_EVS, this.att + Math.min(remainingEVs, evStorage.att));
        remainingEVs = this.getRemainingEVs();
        this.def = Math.min(MAX_EVS, this.def + Math.min(remainingEVs, evStorage.def));
        remainingEVs = this.getRemainingEVs();
        this.spAtt = Math.min(MAX_EVS, this.spAtt + Math.min(remainingEVs, evStorage.spAtt));
        remainingEVs = this.getRemainingEVs();
        this.spDef = Math.min(MAX_EVS, this.spDef + Math.min(remainingEVs, evStorage.spDef));
        remainingEVs = this.getRemainingEVs();
        this.speed = Math.min(MAX_EVS, this.speed + Math.min(remainingEVs, evStorage.speed));
    }

    private int getRemainingEVs() {
        return Math.max(0, MAX_TOTAL_EVS - this.hp - this.att - this.def - this.spAtt - this.spDef - this.speed);
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putInt("EvHp", hp);
        tag.putInt("EvAtt", att);
        tag.putInt("EvDef", def);
        tag.putInt("EvSpAtt", spAtt);
        tag.putInt("EvSpDef", spDef);
        tag.putInt("EvSpeed", speed);
    }

    public static EvStorage readFromNbt(NbtCompound tag) {
        EvStorage storage = new EvStorage();
        storage.hp = tag.getInt("EvHp");
        storage.att = tag.getInt("EvAtt");
        storage.def = tag.getInt("EvDef");
        storage.spAtt = tag.getInt("EvSpAtt");
        storage.spDef = tag.getInt("EvSpDef");
        storage.speed = tag.getInt("EvSpeed");
        return storage;
    }

    public EvStorage copy() {
        return this; // TODO
    }
}
