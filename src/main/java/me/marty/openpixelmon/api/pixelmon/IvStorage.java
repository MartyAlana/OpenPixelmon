package me.marty.openpixelmon.api.pixelmon;

import me.marty.openpixelmon.api.util.PixelmonUtils;
import net.minecraft.nbt.NbtCompound;

/**
 * Used for storing Pixelmon Individual Values (IV's)
 */
public class IvStorage {
    public static final int MAX_IV = 31;

    public int hp;
    public int att;
    public int def;
    public int spAtt;
    public int spDef;
    public int speed;

    public IvStorage() {
        this.hp = PixelmonUtils.randBetween(0, MAX_IV);
        this.att = PixelmonUtils.randBetween(0, MAX_IV);
        this.def = PixelmonUtils.randBetween(0, MAX_IV);
        this.spAtt = PixelmonUtils.randBetween(0, MAX_IV);
        this.spDef = PixelmonUtils.randBetween(0, MAX_IV);
        this.speed = PixelmonUtils.randBetween(0, MAX_IV);
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

    public IvStorage copy() {
        return this; // TODO
    }
}
