package me.marty.openpixelmon.api.pixelmon;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.entity.pixelmon.PixelmonEntity;
import me.marty.openpixelmon.entity.pixelmon.PokeGeneration;
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

/**
 * Holds the entity type and pokemon info. essential to the core of Open Pixelmon
 *
 * @author hydos
 */
public class PokedexEntry {

	public final int minLevel;
	public final int evolutionLevel;
	public final EntityType<? extends PixelmonEntity> type;
	public final Identifier name;
	public final int maleRatio;
	public final PokeType[] pokeTypes;
	public final int catchRate;
	public final EggGroup[] eggGroups;
	public final int stepHatchCount;
	public final double height;
	public final double weight;
	public final int colour;
	public final int baseFriendship;
	public final boolean needsStoneToEvolve;
	public final boolean legendary;

	public PokedexEntry(EntityDimensions dimensions, String pokemonName, int maleRatio, PokeType[] pokeTypes, int catchRate, EggGroup[] eggGroups, int stepHatchCount, double height, double weight, int colour, int baseFriendship, int minLevel, int evolutionLevel, boolean legendary, boolean needsStoneToEvolve) {
		this.name = OpenPixelmon.id(pokemonName);
		this.maleRatio = maleRatio;
		this.pokeTypes = pokeTypes;
		this.catchRate = catchRate;
		this.eggGroups = eggGroups;
		this.stepHatchCount = stepHatchCount;
		this.height = height;
		this.weight = weight;
		this.colour = colour;
		this.baseFriendship = baseFriendship;
		this.legendary = legendary;
		this.minLevel = minLevel;
		this.evolutionLevel = evolutionLevel;
		this.needsStoneToEvolve = needsStoneToEvolve;
		this.type = Registry.register(Registry.ENTITY_TYPE, OpenPixelmon.id(pokemonName), FabricEntityTypeBuilder.create(
				SpawnGroup.CREATURE,
				PixelmonEntity::new)
				.dimensions(dimensions)
				.build());

		//TODO: spawn testing stuff below.
		SpawnRestriction.register(type, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
		BiomeModifications.addSpawn(biomeSelectionContext -> true, SpawnGroup.AMBIENT, PokeGeneration.getPixelmonById(OpenPixelmon.id("seedot")).type, 1, 1, 1);
	}

	public PokedexEntry(EntityDimensions dimensions, String pokemonName, int maleRatio, PokeType[] pokeTypes, int catchRate, EggGroup[] eggGroups, int stepHatchCount, double height, double weight, int colour, int baseFriendship, int evolutionLevel, boolean needsStoneToEvolve, boolean legendary) {
		this(dimensions, pokemonName, maleRatio, pokeTypes, catchRate, eggGroups, stepHatchCount, height, weight, colour, baseFriendship, 1, evolutionLevel, legendary, needsStoneToEvolve);
	}
}
