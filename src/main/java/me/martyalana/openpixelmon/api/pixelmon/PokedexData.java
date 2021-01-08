package me.martyalana.openpixelmon.api.pixelmon;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.entity.pixelmon.BasicPixelmonEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Holds the entity type and pokemon info. essential to the core of Open Pixelmon
 *
 * @author hydos
 */
public class PokedexData {

	public EntityType<? extends BasicPixelmonEntity> type;
	public Identifier name;
	public int maleRatio;
	public PokeType[] pokeTypes;
	public int catchRate;
	public EggGroup[] eggGroups;
	public int stepHatchCount;
	public double height;
	public double weight;
	public int colour;
	public int baseFriendship;

	public PokedexData(EntityDimensions dimensions, String pokemonName, int maleRatio, PokeType[] pokeTypes, int catchRate, EggGroup[] eggGroups, int stepHatchCount, double height, double weight, int colour, int baseFriendship) {
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
		this.type = Registry.register(Registry.ENTITY_TYPE, OpenPixelmon.id(pokemonName), FabricEntityTypeBuilder.create(
				SpawnGroup.MISC,
				BasicPixelmonEntity::new)
				.dimensions(dimensions)
				.build());
	}
}
