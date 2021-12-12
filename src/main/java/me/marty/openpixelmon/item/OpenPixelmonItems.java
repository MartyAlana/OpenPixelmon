package me.marty.openpixelmon.item;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.block.OpenPixelmonBlocks;
import me.marty.openpixelmon.item.plant.ApricornBlockItem;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Defines all items inside of Pixlemon.
 */
@SuppressWarnings("unused")
public class OpenPixelmonItems implements ModInitializer {

    /**
     * Pokeballs
     */
    public static final Item POKE_BALL = createPokeball("poke_ball");
    public static final Item GREAT_BALL = createPokeball("great_ball");
    public static final Item ULTRA_BALL = createPokeball("ultra_ball");
    public static final Item MASTER_BALL = createPokeball("master_ball");
    public static final Item SAFARI_BALL = createPokeball("safari_ball");
    public static final Item FAST_BALL = createPokeball("fast_ball");
    public static final Item LEVEL_BALL = createPokeball("level_ball");
    public static final Item LURE_BALL = createPokeball("lure_ball");
    public static final Item HEAVY_BALL = createPokeball("heavy_ball");
    public static final Item LOVE_BALL = createPokeball("love_ball");
    public static final Item FRIEND_BALL = createPokeball("friend_ball");
    public static final Item MOON_BALL = createPokeball("moon_ball");
    public static final Item SPORT_BALL = createPokeball("sport_ball");
    public static final Item NET_BALL = createPokeball("net_ball");
    public static final Item NEST_BALL = createPokeball("nest_ball");
    public static final Item REPEAT_BALL = createPokeball("repeat_ball");
    public static final Item TIMER_BALL = createPokeball("timer_ball");
    public static final Item LUXURY_BALL = createPokeball("luxury_ball");
    public static final Item PREMIER_BALL = createPokeball("premier_ball");
    public static final Item DIVE_BALL = createPokeball("dive_ball");
    public static final Item DUSK_BALL = createPokeball("dusk_ball");
    public static final Item HEAL_BALL = createPokeball("heal_ball");
    public static final Item QUICK_BALL = createPokeball("quick_ball");
    public static final Item CHERISH_BALL = createPokeball("cherish_ball");
    public static final Item PARK_BALL = createPokeball("park_ball");
    public static final Item DREAM_BALL = createPokeball("dream_ball");
    public static final Item BEAST_BALL = createPokeball("beast_ball");

    /**
     * Acorns
     */
    public static final Item WHITE_APRICORN = createApricorn("white_apricorn");
    public static final Item BlUE_APRICORN = createApricorn("blue_apricorn");
    public static final Item YELLOW_APRICORN = createApricorn("yellow_apricorn");
    public static final Item GREEN_APRICORN = createApricorn("green_apricorn");
    public static final Item PINK_APRICORN = createApricorn("pink_apricorn");
    public static final Item RED_APRICORN = createApricorn("red_apricorn");
    public static final Item BLACK_APRICORN = createApricorn("black_apricorn");

    public static final Item BAKED_WHITE_APRICORN = createBakedApricorn("white_apricorn");
    public static final Item BAKED_BlUE_APRICORN = createBakedApricorn("blue_apricorn");
    public static final Item BAKED_YELLOW_APRICORN = createBakedApricorn("yellow_apricorn");
    public static final Item BAKED_GREEN_APRICORN = createBakedApricorn("green_apricorn");
    public static final Item BAKED_PINK_APRICORN = createBakedApricorn("pink_apricorn");
    public static final Item BAKED_RED_APRICORN = createBakedApricorn("red_apricorn");
    public static final Item BAKED_BLACK_APRICORN = createBakedApricorn("black_apricorn");

    private static Item createApricorn(String name) {
        return generate(new ApricornBlockItem(OpenPixelmonBlocks.WHITE_APRICORN_BUSH, new Item.Settings().group(ItemGroups.ORGANIC)), "white_apricorn");
    }

    private static Item createBakedApricorn(String name) {
        return generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_");
    }

    private static Item createPokeball(String name) {
        return generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), name);
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

    @Override
    public void onInitialize() {
    }
}
