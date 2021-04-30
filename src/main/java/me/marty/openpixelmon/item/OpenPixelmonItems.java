package me.marty.openpixelmon.item;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.block.OpenPixelmonBlocks;
import me.marty.openpixelmon.item.plant.ApricornBlockItem;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OpenPixelmonItems {
    /**
     * Pokeballs
     */
    public static final Item POKE_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "poke_ball");
    public static final Item GREAT_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "great_ball");
    public static final Item ULTRA_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "ultra_ball");
    public static final Item MASTER_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "master_ball");
    public static final Item SAFARI_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "safari_ball");
    public static final Item FAST_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "fast_ball");
    public static final Item LEVEL_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "level_ball");
    public static final Item LURE_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "lure_ball");
    public static final Item HEAVY_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "heavy_ball");
    public static final Item LOVE_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "love_ball");
    public static final Item FRIEND_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "friend_ball");
    public static final Item MOON_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "moon_ball");
    public static final Item SPORT_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "sport_ball");
    public static final Item NET_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "net_ball");
    public static final Item NEST_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "nest_ball");
    public static final Item REPEAT_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "repeat_ball");
    public static final Item TIMER_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "timer_ball");
    public static final Item LUXURY_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "luxury_ball");
    public static final Item PREMIER_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "premier_ball");
    public static final Item DIVE_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "dive_ball");
    public static final Item DUSK_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "dusk_ball");
    public static final Item HEAL_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "heal_ball");
    public static final Item QUICK_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "quick_ball");
    public static final Item CHERISH_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "cherish_ball");
    public static final Item PARK_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "park_ball");
    public static final Item DREAM_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "dream_ball");
    public static final Item BEAST_BALL = generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), "beast_ball");

    /**
     * Acorns
     */
    public static final Item WHITE_APRICORN = generate(new ApricornBlockItem(OpenPixelmonBlocks.WHITE_APRICORN_BUSH, new Item.Settings().group(ItemGroups.ORGANIC)), "white_apricorn");
    public static final Item BlUE_APRICORN = generate(new ApricornBlockItem(OpenPixelmonBlocks.BLUE_APRICORN_BUSH, new Item.Settings().group(ItemGroups.ORGANIC)), "blue_apricorn");
    public static final Item YELLOW_APRICORN = generate(new ApricornBlockItem(OpenPixelmonBlocks.YELLOW_APRICORN_BUSH, new Item.Settings().group(ItemGroups.ORGANIC)), "yellow_apricorn");
    public static final Item GREEN_APRICORN = generate(new ApricornBlockItem(OpenPixelmonBlocks.GREEN_APRICORN_BUSH, new Item.Settings().group(ItemGroups.ORGANIC)), "green_apricorn");
    public static final Item PINK_APRICORN = generate(new ApricornBlockItem(OpenPixelmonBlocks.PINK_APRICORN_BUSH, new Item.Settings().group(ItemGroups.ORGANIC)), "pink_apricorn");
    public static final Item RED_APRICORN = generate(new ApricornBlockItem(OpenPixelmonBlocks.RED_APRICORN_BUSH, new Item.Settings().group(ItemGroups.ORGANIC)), "red_apricorn");
    public static final Item BLACK_APRICORN = generate(new ApricornBlockItem(OpenPixelmonBlocks.BLACK_APRICORN_BUSH, new Item.Settings().group(ItemGroups.ORGANIC)), "black_apricorn");

    public static final Item BAKED_WHITE_APRICORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_white_apricorn");
    public static final Item BAKED_BlUE_APRICORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_blue_apricorn");
    public static final Item BAKED_YELLOW_APRICORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_yellow_apricorn");
    public static final Item BAKED_GREEN_APRICORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_green_apricorn");
    public static final Item BAKED_PINK_APRICORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_pink_apricorn");
    public static final Item BAKED_RED_APRICORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_red_apricorn");
    public static final Item BAKED_BLACK_APRICORN = generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_black_apricorn");

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
                                .layer0("pixelmon:item/" + id)),
                modelIdentifier);
        Registry.register(Registry.ITEM, identifier, item);
        return item;
    }
}
