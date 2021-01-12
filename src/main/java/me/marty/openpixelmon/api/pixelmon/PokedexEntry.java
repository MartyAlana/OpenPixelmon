package me.marty.openpixelmon.api.pixelmon;

import me.marty.openpixelmon.data.DataLoaders;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

/**
 * One of the most important classes in the mod.
 * This class is deserialized from json so its easier to manage this amount of data.
 */
public class PokedexEntry {

	public int specialAttack;
	public int specialDefense;
	public int attack;
	public int defense;
	public int speed;
	public int hp;

	public int baseFriendship;
	public int baseExp;

	public int catchRate;
	public int maleRatio;
	public int pokedexId;

	public int width;
	public int height;
	public int length;
	public int weight;

	public int minSpawnGroupSize;
	public int maxSpawnGroupSize;

	public float[] renderScale;
	public float[] guiScale;

	public int hoveringHeight;

	public int eggCycles;

	public Map<String, Integer> spawnInformation;
	public Map<String, Integer> aggression;
	public Map<String, Integer> evGain;
	public Map<Integer, List<String>> levelMoves;
	public Map<String, List<String>> moves;
	public Map<String, List<String>> tutorMoves;

	public List<Map<String, Integer>> evolutions;

	public PokeType[] types;
	public String[] previousEvolutions;
	public String[] validSpawnLocations;
	public String[] abilities;
	public EggGroup[] eggGroups;

	public boolean canFly;
	public boolean canSurf;
	public boolean doesHover;
	public boolean isRideable;
	public boolean legendary;

	public String expGainSpeed;

	public Identifier getIdentifier() {
		return DataLoaders.PIXELMON_MANAGER.getPixelmon().inverse().get(this);
	}
}
