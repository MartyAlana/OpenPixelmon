package me.martyalana.openpixelmon.item;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.item.misc.PokeballItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class OpenPixelmonItems {
	/**
	 * Pokeballs
	 */
	public static final Item POKEBALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item GREATBALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item ULTRABALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item MASTERBALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item QUICKBALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));

	/**
	 * Acorns
	 */
	public static final Item WHITE_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item BlUE_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item YELLOW_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item GREEN_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item PINK_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item RED_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item BLACK_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));

	public static final Item BAKED_WHITE_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item BAKED_BlUE_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item BAKED_YELLOW_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item BAKED_GREEN_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item BAKED_PINK_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item BAKED_RED_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));
	public static final Item BAKED_BLACK_ACORN = new Item(new Item.Settings().group(ItemGroups.ORGANIC));

	public static void initialize() {
		registerPokeballs();
		registerAcorns();
	}

	private static void registerAcorns() {
		Registry.register(Registry.ITEM, OpenPixelmon.id("white_acorn"), WHITE_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("blue_acorn"), BlUE_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("yellow_acorn"), YELLOW_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("green_acorn"), GREEN_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("pink_acorn"), PINK_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("red_acorn"), RED_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("black_acorn"), BLACK_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("baked_white_acorn"), WHITE_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("baked_blue_acorn"), BlUE_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("baked_yellow_acorn"), YELLOW_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("baked_green_acorn"), GREEN_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("baked_pink_acorn"), PINK_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("baked_red_acorn"), RED_ACORN);
		Registry.register(Registry.ITEM, OpenPixelmon.id("baked_black_acorn"), BLACK_ACORN);
	}

	private static void registerPokeballs() {
		Registry.register(Registry.ITEM, OpenPixelmon.id("pokeball"), POKEBALL);
		Registry.register(Registry.ITEM, OpenPixelmon.id("greatball"), GREATBALL);
		Registry.register(Registry.ITEM, OpenPixelmon.id("ultraball"), ULTRABALL);
		Registry.register(Registry.ITEM, OpenPixelmon.id("masterball"), MASTERBALL);
		Registry.register(Registry.ITEM, OpenPixelmon.id("quickball"), QUICKBALL);
	}
}
