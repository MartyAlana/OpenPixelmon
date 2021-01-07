package me.martyalana.openpixelmon.item;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.item.misc.PokeballItem;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
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
	public static final Item WHITE_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "white_acorn");
	public static final Item BlUE_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "blue_acorn");
	public static final Item YELLOW_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "yellow_acorn");
	public static final Item GREEN_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "green_acorn");
	public static final Item PINK_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "pink_acorn");
	public static final Item RED_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "red_acorn");
	public static final Item BLACK_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "black_acorn");

	public static final Item BAKED_WHITE_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_white_acorn");
	public static final Item BAKED_BlUE_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_blue_acorn");
	public static final Item BAKED_YELLOW_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_yellow_acorn");
	public static final Item BAKED_GREEN_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_green_acorn");
	public static final Item BAKED_PINK_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_pink_acorn");
	public static final Item BAKED_RED_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_red_acorn");
	public static final Item BAKED_BLACK_ACORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_black_acorn");

	public static void initialize() {
	}

	/**
	 * Used to generate a basic item model
	 *
	 * @param item the item to generate the model for
	 * @param id   the id of the item
	 * @return the item that was passed in
	 */
	public static Item generate(Item item, String id) {
		Identifier identifier = OpenPixelmon.id(id);
		Identifier modelIdentifier = OpenPixelmon.id("item/" + id);
		OpenPixelmon.RESOURCE_PACK.addModel(new JModel()
						.parent("item/generated")
						.textures(new JTextures()
								.layer0("open_pixelmon:item/" + id)),
				modelIdentifier);
		Registry.register(Registry.ITEM, identifier, item);
		return item;
	}
}
