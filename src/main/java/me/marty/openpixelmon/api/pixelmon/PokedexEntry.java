package me.marty.openpixelmon.api.pixelmon;

import com.mojang.serialization.Codec;
import me.marty.openpixelmon.api.util.Codecs;
import me.marty.openpixelmon.data.DataLoaders;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

/**
 * One of the most important classes in the mod.
 * This class is deserialized from json so its easier to manage this amount of data.
 */
public class PokedexEntry {

	public static final Codec<PokedexEntry> CODEC = Codecs.withGson(PokedexEntry.class);

	public final int specialAttack;
	public final int specialDefense;
	public final int attack;
	public final int defense;
	public final int speed;
	public final int hp;

	public final int baseFriendship;
	public final int baseExp;

	public final int catchRate;
	public final int maleRatio;
	public final int pokedexId;

	public final float width;
	public final float height;
	public final float length;
	public final float weight;

	public final int minSpawnGroupSize;
	public final int maxSpawnGroupSize;

	public final float[] renderScale;
	public final float[] guiScale;

	public final int hoveringHeight;

	public final int eggCycles;

	public final Map<String, Integer> spawnInformation;
	public final Map<String, Integer> aggression;
	public final Map<String, Integer> evGain;
	public final Map<String, List<String>> levelMoves;
	public final Map<String, List<String>> moves;
	public final Map<String, List<String>> tutorMoves;

	public final List<Map<String, Integer>> evolutions;

	public final PokeType[] types;
	public final String[] previousEvolutions;
	public final String[] validSpawnLocations;
	public final String[] abilities;
	public final EggGroup[] eggGroups;

	public final boolean canFly;
	public final boolean canSurf;
	public final boolean doesHover;
	public final boolean isRideable;
	public final boolean legendary;

	public final int expGainSpeed;

	public PokedexEntry(int specialAttack, int specialDefense, int attack, int defense, int speed, int hp, int baseFriendship, int baseExp, int catchRate, int maleRatio, int pokedexId, float width, float height, float length, float weight, int minSpawnGroupSize, int maxSpawnGroupSize, float[] renderScale, float[] guiScale, int hoveringHeight, int eggCycles, Map<String, Integer> spawnInformation, Map<String, Integer> aggression, Map<String, Integer> evGain, Map<String, List<String>> levelMoves, Map<String, List<String>> moves, Map<String, List<String>> tutorMoves, List<Map<String, Integer>> evolutions, PokeType[] types, String[] previousEvolutions, String[] validSpawnLocations, String[] abilities, EggGroup[] eggGroups, boolean canFly, boolean canSurf, boolean doesHover, boolean isRideable, boolean legendary, int expGainSpeed) {
		this.specialAttack = specialAttack;
		this.specialDefense = specialDefense;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
		this.hp = hp;
		this.baseFriendship = baseFriendship;
		this.baseExp = baseExp;
		this.catchRate = catchRate;
		this.maleRatio = maleRatio;
		this.pokedexId = pokedexId;
		this.width = width;
		this.height = height;
		this.length = length;
		this.weight = weight;
		this.minSpawnGroupSize = minSpawnGroupSize;
		this.maxSpawnGroupSize = maxSpawnGroupSize;
		this.renderScale = renderScale;
		this.guiScale = guiScale;
		this.hoveringHeight = hoveringHeight;
		this.eggCycles = eggCycles;
		this.spawnInformation = spawnInformation;
		this.aggression = aggression;
		this.evGain = evGain;
		this.levelMoves = levelMoves;
		this.moves = moves;
		this.tutorMoves = tutorMoves;
		this.evolutions = evolutions;
		this.types = types;
		this.previousEvolutions = previousEvolutions;
		this.validSpawnLocations = validSpawnLocations;
		this.abilities = abilities;
		this.eggGroups = eggGroups;
		this.canFly = canFly;
		this.canSurf = canSurf;
		this.doesHover = doesHover;
		this.isRideable = isRideable;
		this.legendary = legendary;
		this.expGainSpeed = expGainSpeed;
	}

	public Identifier getIdentifier() {
		return DataLoaders.PIXELMON_MANAGER.getPixelmon().inverse().get(this);
	}
}
