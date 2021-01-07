package me.martyalana.openpixelmon.item;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.item.misc.PokeballItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class OpenPixelmonItems {
	public static final Item POKEBALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item GREATBALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item ULTRABALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item MASTERBALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));
	public static final Item QUICKBALL = new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS));

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
