package me.martyalana.openpixelmon.item;

import me.martyalana.openpixelmon.OpenPixelmon;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {
	public static final ItemGroup POKEBALLS = FabricItemGroupBuilder.build(
			OpenPixelmon.id("pokeballs"),
			() -> new ItemStack(OpenPixelmonItems.POKE_BALL)
	);

	public static final ItemGroup ORGANIC = FabricItemGroupBuilder.build(
			OpenPixelmon.id("organic"),
			() -> new ItemStack(OpenPixelmonItems.RED_ACORN)
	);
}
