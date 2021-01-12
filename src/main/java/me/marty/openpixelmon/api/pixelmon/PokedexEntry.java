package me.marty.openpixelmon.api.pixelmon;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.api.generation.PixelmonGeneration;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Type;
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
}
