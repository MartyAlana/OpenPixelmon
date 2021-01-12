package me.marty.openpixelmon.api.pixelmon;

import com.mojang.serialization.*;
import me.marty.openpixelmon.api.util.Codecs;
import me.marty.openpixelmon.data.DataLoaders;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * One of the most important classes in the mod.
 * This class is deserialized from json so its easier to manage this amount of data.
 */
public class PokedexEntry {

	public static final Codec<PokedexEntry> CODEC = new MapCodec<PokedexEntry>() {
		@Override
		public <T> RecordBuilder<T> encode(PokedexEntry input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
			return prefix
					.add("specialAttack", Codec.INT.encodeStart(ops, input.specialAttack))
					.add("specialDefense", Codec.INT.encodeStart(ops, input.specialDefense))
					.add("attack", Codec.INT.encodeStart(ops, input.attack))
					.add("defense", Codec.INT.encodeStart(ops, input.defense))
					.add("speed", Codec.INT.encodeStart(ops, input.speed))
					.add("hp", Codec.INT.encodeStart(ops, input.hp))
					.add("baseFriendship", Codec.INT.encodeStart(ops, input.baseFriendship))
					.add("baseExp", Codec.INT.encodeStart(ops, input.baseExp))
					.add("catchRate", Codec.INT.encodeStart(ops, input.catchRate))
					.add("maleRatio", Codec.INT.encodeStart(ops, input.maleRatio))
					.add("pokedexId", Codec.INT.encodeStart(ops, input.pokedexId))
					.add("width", Codec.FLOAT.encodeStart(ops, input.width))
					.add("height", Codec.FLOAT.encodeStart(ops, input.height))
					.add("length", Codec.FLOAT.encodeStart(ops, input.length))
					.add("weight", Codec.FLOAT.encodeStart(ops, input.weight))
					.add("minSpawnGroupSize", Codec.INT.encodeStart(ops, input.minSpawnGroupSize))
					.add("maxSpawnGroupSize", Codec.INT.encodeStart(ops, input.maxSpawnGroupSize))
					.add("renderScale", Codecs.ofArray(Codec.FLOAT).encodeStart(ops, input.renderScale))
					.add("guiScale", Codecs.ofArray(Codec.FLOAT).encodeStart(ops, input.guiScale))
					.add("hoveringHeight", Codec.INT.encodeStart(ops, input.hoveringHeight))
					.add("eggCycles", Codec.INT.encodeStart(ops, input.eggCycles))
					.add("spawnInformation", Codecs.ofMap(Codec.STRING, Codec.INT).encodeStart(ops, input.spawnInformation))
					.add("aggression", Codecs.ofMap(Codec.STRING, Codec.INT).encodeStart(ops, input.aggression))
					.add("evGain", Codecs.ofMap(Codec.STRING, Codec.INT).encodeStart(ops, input.evGain))
					.add("levelMoves", Codecs.ofMap(Codec.STRING, Codec.STRING.listOf()).encodeStart(ops, input.levelMoves))
					.add("moves", Codecs.ofMap(Codec.STRING, Codec.STRING.listOf()).encodeStart(ops, input.moves))
					.add("tutorMoves", Codecs.ofMap(Codec.STRING, Codec.STRING.listOf()).encodeStart(ops, input.tutorMoves))
					.add("evolutions", Codecs.ofMap(Codec.STRING, Codec.INT).listOf().encodeStart(ops, input.evolutions))
					.add("types", Codecs.ofArray(Codecs.ofEnum(PokeType.class)).encodeStart(ops, input.types))
					.add("previousEvolutions", Codecs.ofArray(Codec.STRING).encodeStart(ops, input.previousEvolutions))
					.add("validSpawnLocations", Codecs.ofArray(Codec.STRING).encodeStart(ops, input.validSpawnLocations))
					.add("abilities", Codecs.ofArray(Codec.STRING).encodeStart(ops, input.abilities))
					.add("eggGroups", Codecs.ofArray(Codecs.ofEnum(EggGroup.class)).encodeStart(ops, input.eggGroups))
					.add("canFly", Codec.BOOL.encodeStart(ops, input.canFly))
					.add("canSurf", Codec.BOOL.encodeStart(ops, input.canSurf))
					.add("doesHover", Codec.BOOL.encodeStart(ops, input.doesHover))
					.add("isRideable", Codec.BOOL.encodeStart(ops, input.isRideable))
					.add("legendary", Codec.BOOL.encodeStart(ops, input.legendary))
					.add("expGainSpeed", Codec.STRING.encodeStart(ops, input.expGainSpeed));
		}

		@Override
		public <T> DataResult<PokedexEntry> decode(DynamicOps<T> ops, MapLike<T> input) {
			return DataResult.success(new PokedexEntry(
					decode(ops, input, Codec.INT, "specialAttack"),
					decode(ops, input, Codec.INT, "specialDefense"),
					decode(ops, input, Codec.INT, "attack"),
					decode(ops, input, Codec.INT, "defense"),
					decode(ops, input, Codec.INT, "speed"),
					decode(ops, input, Codec.INT, "hp"),
					decode(ops, input, Codec.INT, "baseFriendship"),
					decode(ops, input, Codec.INT, "baseExp"),
					decode(ops, input, Codec.INT, "catchRate"),
					decode(ops, input, Codec.INT, "maleRatio"),
					decode(ops, input, Codec.INT, "pokedexId"),
					decode(ops, input, Codec.FLOAT, "width"),
					decode(ops, input, Codec.FLOAT, "height"),
					decode(ops, input, Codec.FLOAT, "length"),
					decode(ops, input, Codec.FLOAT, "weight"),
					decode(ops, input, Codec.INT, "minSpawnGroupSize"),
					decode(ops, input, Codec.INT, "maxSpawnGroupSize"),
					decode(ops, input, Codecs.ofArray(Codec.FLOAT), "renderScale"),
					decode(ops, input, Codecs.ofArray(Codec.FLOAT), "guiScale"),
					decode(ops, input, Codec.INT, "hoveringHeight"),
					decode(ops, input, Codec.INT, "eggCycles"),
					decode(ops, input, Codecs.ofMap(Codec.STRING, Codec.INT), "spawnInformation"),
					decode(ops, input, Codecs.ofMap(Codec.STRING, Codec.INT), "aggression"),
					decode(ops, input, Codecs.ofMap(Codec.STRING, Codec.INT), "evGain"),
					decode(ops, input, Codecs.ofMap(Codec.STRING, Codec.STRING.listOf()), "levelMoves"),
					decode(ops, input, Codecs.ofMap(Codec.STRING, Codec.STRING.listOf()), "moves"),
					decode(ops, input, Codecs.ofMap(Codec.STRING, Codec.STRING.listOf()), "tutorMoves"),
					decode(ops, input, Codecs.ofMap(Codec.STRING, Codec.INT).listOf(), "evolutions"),
					decode(ops, input, Codecs.ofArray(Codecs.ofEnum(PokeType.class)), "types"),
					decode(ops, input, Codecs.ofArray(Codec.STRING), "previousEvolutions"),
					decode(ops, input, Codecs.ofArray(Codec.STRING), "validSpawnLocations"),
					decode(ops, input, Codecs.ofArray(Codec.STRING), "abilities"),
					decode(ops, input, Codecs.ofArray(Codecs.ofEnum(EggGroup.class)), "eggGroups"),
					decode(ops, input, Codec.BOOL, "canFly"),
					decode(ops, input, Codec.BOOL, "canSurf"),
					decode(ops, input, Codec.BOOL, "doesHover"),
					decode(ops, input, Codec.BOOL, "isRideable"),
					decode(ops, input, Codec.BOOL, "legendary"),
					decode(ops, input, Codec.STRING, "expGainSpeed")
			), Lifecycle.stable());
		}

		@Override
		public <T> Stream<T> keys(DynamicOps<T> ops) {
			return Stream.of("specialAttack", "specialDefense", "attack", "defense", "speed", "hp", "baseFriendship",
					"baseExp", "catchRate", "maleRatio", "pokedexId", "width", "height", "length", "weight",
					"minSpawnGroupSize", "maxSpawnGroupSize", "renderScale", "guiScale", "hoveringHeight", "eggCycles",
					"spawnInformation", "aggression", "evGain", "levelMoves", "moves", "tutorMoves", "evolutions",
					"types", "previousEvolutions", "validSpawnLocations", "abilities", "eggGroups", "canFly", "canSurf",
					"doesHover", "isRideable", "legendary", "expGainSpeed")
					.map(ops::createString);
		}

		private <T, U> U decode(DynamicOps<T> ops, MapLike<T> input, Codec<U> codec, String name) {
			return codec.decode(ops, input.get(ops.createString(name))).result().get().getFirst();
		}
	}.codec();

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

	public final Float[] renderScale;
	public final Float[] guiScale;

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

	public final String expGainSpeed;

	public PokedexEntry(int specialAttack, int specialDefense, int attack, int defense, int speed, int hp, int baseFriendship, int baseExp, int catchRate, int maleRatio, int pokedexId, float width, float height, float length, float weight, int minSpawnGroupSize, int maxSpawnGroupSize, Float[] renderScale, Float[] guiScale, int hoveringHeight, int eggCycles, Map<String, Integer> spawnInformation, Map<String, Integer> aggression, Map<String, Integer> evGain, Map<String, List<String>> levelMoves, Map<String, List<String>> moves, Map<String, List<String>> tutorMoves, List<Map<String, Integer>> evolutions, PokeType[] types, String[] previousEvolutions, String[] validSpawnLocations, String[] abilities, EggGroup[] eggGroups, boolean canFly, boolean canSurf, boolean doesHover, boolean isRideable, boolean legendary, String expGainSpeed) {
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
