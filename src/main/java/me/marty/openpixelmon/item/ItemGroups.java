package me.marty.openpixelmon.item;

import me.marty.openpixelmon.OpenPixelmon;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroups {
	public static final ItemGroup POKEBALLS = FabricItemGroupBuilder.build(
			OpenPixelmon.id("pokeballs"),
			() -> new ItemStack(OpenPixelmonItems.POKE_BALL)
	);

	public static final ItemGroup RESTORATION = FabricItemGroupBuilder.build(
			OpenPixelmon.id("restoration"),
			() -> new ItemStack(OpenPixelmonItems.POKE_BALL)
	);

	public static final ItemGroup ORGANIC = FabricItemGroupBuilder.build(
			OpenPixelmon.id("organic"),
			() -> new ItemStack(OpenPixelmonItems.RED_APRICORN)
	);

	public static final ItemGroup TMS = FabricItemGroupBuilder.build(
			OpenPixelmon.id("tms"),
			() -> new ItemStack(OpenPixelmonItems.POKE_BALL)
	);

	public static final ItemGroup BLOCKS = FabricItemGroupBuilder.build(
			OpenPixelmon.id("blocks"),
			() -> new ItemStack(OpenPixelmonItems.POKE_BALL)
	);

	public static final ItemGroup ITEMS = FabricItemGroupBuilder.build(
			OpenPixelmon.id("items"),
			() -> new ItemStack(OpenPixelmonItems.POKE_BALL)
	);

	public static final ItemGroup BADGES = FabricItemGroupBuilder.build(
			OpenPixelmon.id("badges"),
			() -> new ItemStack(OpenPixelmonItems.POKE_BALL)
	);

	public static final ItemGroup DECORATION = FabricItemGroupBuilder.build(
			OpenPixelmon.id("decoration"),
			() -> new ItemStack(OpenPixelmonItems.POKE_BALL)
	);
}
