package me.martyalana.openpixelmon.item;

import me.martyalana.openpixelmon.OpenPixelmon;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class OpenPixelmonItems {
	public static final Item POKEBALL = new Item(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item GREATBALL = new Item(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item ULTRABALL = new Item(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item MASTERBALL = new Item(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item QUICKBALL = new Item(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));

	public static void initialize() {
		registerPokeballs();
	}

	private static void registerPokeballs() {
		Registry.register(Registry.ITEM, OpenPixelmon.id("pokeball"), POKEBALL);
		Registry.register(Registry.ITEM, OpenPixelmon.id("greatball"), GREATBALL);
		Registry.register(Registry.ITEM, OpenPixelmon.id("ultraball"), ULTRABALL);
		Registry.register(Registry.ITEM, OpenPixelmon.id("masterball"), MASTERBALL);
		Registry.register(Registry.ITEM, OpenPixelmon.id("quickball"), QUICKBALL);
	}
}
