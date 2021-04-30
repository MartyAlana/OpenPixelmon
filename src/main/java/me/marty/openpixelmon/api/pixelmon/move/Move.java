package me.marty.openpixelmon.api.pixelmon.move;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

public class Move {

    public static final Codec<Move> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("type").forGetter(Move::getType),
            Identifier.CODEC.fieldOf("category").forGetter(Move::getCategory),
            Identifier.CODEC.fieldOf("condition").forGetter(Move::getCondition),
            Codec.INT.fieldOf("pp").forGetter(Move::getPp),
            Codec.INT.fieldOf("power").forGetter(Move::getPower),
            Codec.DOUBLE.fieldOf("accuracy").forGetter(Move::getAccuracy)
    ).apply(instance, Move::new));

    private final Identifier type;
    private final Identifier category;
    private final Identifier condition;

    private final int pp;
    private final int power;

    private final double accuracy;

    public Move(Identifier type, Identifier category, Identifier condition, int pp, int power, double accuracy) {
        this.type = type;
        this.category = category;
        this.condition = condition;
        this.pp = pp;
        this.power = power;
        this.accuracy = accuracy;
    }

    public Identifier getType() {
        return type;
    }

    public Identifier getCategory() {
        return category;
    }

    public Identifier getCondition() {
        return condition;
    }

    public int getPp() {
        return pp;
    }

    public int getPower() {
        return power;
    }

    public double getAccuracy() {
        return accuracy;
    }
}
