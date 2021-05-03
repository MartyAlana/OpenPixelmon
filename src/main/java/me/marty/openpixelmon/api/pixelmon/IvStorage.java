package me.marty.openpixelmon.api.pixelmon;

import net.minecraft.nbt.NbtCompound;

import java.util.Random;

/**
 * Used for storing Pixelmon Individual Values (IV's)
 */
public class IvStorage {
    public static final int MAX_IV = 31;
    private static final Random RANDOM = new Random();

    public int hp;
    public int att;
    public int def;
    public int spAtt;
    public int spDef;
    public int speed;

    public IvStorage() {
        this.hp = randBetween(0, MAX_IV);
        this.att = randBetween(0, MAX_IV);
        this.def = randBetween(0, MAX_IV);
        this.spAtt = randBetween(0, MAX_IV);
        this.spDef = randBetween(0, MAX_IV);
        this.speed = randBetween(0, MAX_IV);
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putInt("IvHp", hp);
        tag.putInt("IvAtt", att);
        tag.putInt("IvDef", def);
        tag.putInt("IvSpAtt", spAtt);
        tag.putInt("IvSpDef", spDef);
        tag.putInt("IvSpeed", speed);
    }

    public static IvStorage readFromNbt(NbtCompound tag) {
        IvStorage storage = new IvStorage();
        storage.hp = tag.getInt("IvHp");
        storage.att = tag.getInt("IvAtt");
        storage.def = tag.getInt("IvDef");
        storage.spAtt = tag.getInt("IvSpAtt");
        storage.spDef = tag.getInt("IvSpDef");
        storage.speed = tag.getInt("IvSpeed");
        return storage;
    }

    private int randBetween(int from, int to) {
        return RANDOM.nextInt(to - from + 1) + from;
    }

    public IvStorage copy() {
        return this; // TODO
    }
}
