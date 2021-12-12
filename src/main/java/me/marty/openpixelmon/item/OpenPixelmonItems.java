package me.marty.openpixelmon.item;

import me.marty.openpixelmon.OpenPixelmon;
import me.marty.openpixelmon.block.OpenPixelmonBlocks;
import me.marty.openpixelmon.item.plant.ApricornBlockItem;
import me.marty.openpixelmon.item.pokeball.PokeballItem;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
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
     * Unbaked Apricorns
     */
    public static final Item WHITE_APRICORN = createApricorn("white_apricorn", OpenPixelmonBlocks.WHITE_APRICORN_BUSH);
    public static final Item BlUE_APRICORN = createApricorn("blue_apricorn", OpenPixelmonBlocks.BLUE_APRICORN_BUSH);
    public static final Item YELLOW_APRICORN = createApricorn("yellow_apricorn", OpenPixelmonBlocks.YELLOW_APRICORN_BUSH);
    public static final Item GREEN_APRICORN = createApricorn("green_apricorn", OpenPixelmonBlocks.GREEN_APRICORN_BUSH);
    public static final Item PINK_APRICORN = createApricorn("pink_apricorn", OpenPixelmonBlocks.PINK_APRICORN_BUSH);
    public static final Item RED_APRICORN = createApricorn("red_apricorn", OpenPixelmonBlocks.RED_APRICORN_BUSH);
    public static final Item BLACK_APRICORN = createApricorn("black_apricorn", OpenPixelmonBlocks.BLACK_APRICORN_BUSH);

    /**
     * Baked Apricorns
     */
    public static final Item BAKED_WHITE_APRICORN = createBakedApricorn("white_apricorn");
    public static final Item BAKED_BlUE_APRICORN = createBakedApricorn("blue_apricorn");
    public static final Item BAKED_YELLOW_APRICORN = createBakedApricorn("yellow_apricorn");
    public static final Item BAKED_GREEN_APRICORN = createBakedApricorn("green_apricorn");
    public static final Item BAKED_PINK_APRICORN = createBakedApricorn("pink_apricorn");
    public static final Item BAKED_RED_APRICORN = createBakedApricorn("red_apricorn");
    public static final Item BAKED_BLACK_APRICORN = createBakedApricorn("black_apricorn");

    /**
     * Badges
     */
    public static final BadgeItem balanceBadge = createBadge("balance_badge", null);
    public static final BadgeItem beaconBadge = createBadge("beacon_badge", null);
    public static final BadgeItem boulderBadge = createBadge("boulder_badge", null);
    public static final BadgeItem cascadeBadge = createBadge("cascade_badge", null);
    public static final BadgeItem coalBadge = createBadge("coal_badge", null);
    public static final BadgeItem cobbleBadge = createBadge("cobble_badge", null);
    public static final BadgeItem dynamoBadge = createBadge("dynamo_badge", null);
    public static final BadgeItem earthBadge = createBadge("earth_badge", null);
    public static final BadgeItem featherBadge = createBadge("feather_badge", null);
    public static final BadgeItem fenBadge = createBadge("fen_badge", null);
    public static final BadgeItem fogBadge = createBadge("fog_badge", null);
    public static final BadgeItem forestBadge = createBadge("forest_badge", null);
    public static final BadgeItem glacierBadge = createBadge("glacier_badge", null);
    public static final BadgeItem heatBadge = createBadge("heat_badge", null);
    public static final BadgeItem hiveBadge = createBadge("hive_badge", null);
    public static final BadgeItem icicleBadge = createBadge("icicle_badge", null);
    public static final BadgeItem knuckleBadge = createBadge("knuckle_badge", null);
    public static final BadgeItem marshBadge = createBadge("marsh_badge", null);
    public static final BadgeItem mindBadge = createBadge("mind_badge", null);
    public static final BadgeItem mineBadge = createBadge("mine_badge", null);
    public static final BadgeItem mineralBadge = createBadge("mineral_badge", null);
    public static final BadgeItem plainBadge = createBadge("plain_badge", null);
    public static final BadgeItem rainbowBadge = createBadge("rainbow_badge", null);
    public static final BadgeItem rainBadge = createBadge("rain_badge", null);
    public static final BadgeItem relicBadge = createBadge("relic_badge", null);
    public static final BadgeItem risingBadge = createBadge("rising_badge", null);
    public static final BadgeItem soulBadge = createBadge("soul_badge", null);
    public static final BadgeItem stoneBadge = createBadge("stone_badge", null);
    public static final BadgeItem stormBadge = createBadge("storm_badge", null);
    public static final BadgeItem thunderBadge = createBadge("thunder_badge", null);
    public static final BadgeItem volcanoBadge = createBadge("volcano_badge", null);
    public static final BadgeItem zephyrBadge = createBadge("zephyr_badge", null);
    public static final BadgeItem basicBadge = createBadge("basic_badge", null);
    public static final BadgeItem boltBadge = createBadge("bolt_badge", null);
    public static final BadgeItem freezeBadge = createBadge("freeze_badge", null);
    public static final BadgeItem insectBadge = createBadge("insect_badge", null);
    public static final BadgeItem jetBadge = createBadge("jet_badge", null);
    public static final BadgeItem legendBadge = createBadge("legend_badge", null);
    public static final BadgeItem quakeBadge = createBadge("quake_badge", null);
    public static final BadgeItem toxicBadge = createBadge("toxic_badge", null);
    public static final BadgeItem trioBadge = createBadge("trio_badge", null);
    public static final BadgeItem waveBadge = createBadge("wave_badge", null);
    public static final BadgeItem bugBadge = createBadge("bug_badge", null);
    public static final BadgeItem cliffBadge = createBadge("cliff_badge", null);
    public static final BadgeItem rumbleBadge = createBadge("rumble_badge", null);
    public static final BadgeItem plantBadge = createBadge("plant_badge", null);
    public static final BadgeItem voltageBadge = createBadge("voltage_badge", null);
    public static final BadgeItem fairyBadge = createBadge("fairy_badge", null);
    public static final BadgeItem psychicBadge = createBadge("psychic_badge", null);
    public static final BadgeItem icebergBadge = createBadge("iceberg_badge", null);
    public static final BadgeItem spikeShellBadge = createBadge("spikeshell_badge", null);
    public static final BadgeItem seaRubyBadge = createBadge("searuby_badge", null);
    public static final BadgeItem jadeStarBadge = createBadge("jadestar_badge", null);
    public static final BadgeItem coralEyeBadge = createBadge("coraleye_badge", null);
    public static final BadgeItem freedomBadge = createBadge("freedom_badge", null);
    public static final BadgeItem harmonyBadge = createBadge("harmony_badge", null);
    public static final BadgeItem patienceBadge = createBadge("patience_badge", null);
    public static final BadgeItem prideBadge = createBadge("pride_badge", null);
    public static final BadgeItem tranquilityBadge = createBadge("tranquility_badge", null);
    public static final BadgeItem darkBadge = createBadge("dark_badge", null);
    public static final BadgeItem dragonBadge = createBadge("dragon_badge", null);
    public static final BadgeItem fairyBadge2 = createBadge("fairy_badge2", null);
    public static final BadgeItem fireBadge = createBadge("fire_badge", null);
    public static final BadgeItem grassBadge = createBadge("grass_badge", null);
    public static final BadgeItem iceBadge = createBadge("ice_badge", null);
    public static final BadgeItem rockBadge = createBadge("rock_badge", null);
    public static final BadgeItem waterBadge = createBadge("water_badge", null);
    public static final BadgeItem fightingBadge = createBadge("fighting_badge", null);
    public static final BadgeItem ghostBadge = createBadge("ghost_badge", null);

    /**
     * General Pixelmon Items
     */
    public static final Item ITEM_FINDER = createItem("item_finder", ItemGroups.ITEMS);
    public static final Item RARE_CANDY = createItem("rare_candy", ItemGroups.ITEMS);
    public static final Item DYNAMAX_CANDY = createItem("dynamax_candy", ItemGroups.ITEMS);
    public static final Item POTION = createItem("potion", ItemGroups.ITEMS);
    public static final Item SUPER_POTION = createItem("super_potion", ItemGroups.ITEMS);
    public static final Item HYPER_POTION = createItem("hyper_potion", ItemGroups.ITEMS);
    public static final Item MAX_POTION = createItem("max_potion", ItemGroups.ITEMS);
    public static final Item FRESH_WATER = createItem("fresh_water", ItemGroups.ITEMS);
    public static final Item SODA_POP = createItem("soda_pop", ItemGroups.ITEMS);
    public static final Item LEMONADE = createItem("lemonade", ItemGroups.ITEMS);
    public static final Item MOOMOO_MILK = createItem("moomoo_milk", ItemGroups.ITEMS);
    public static final Item ETHER = createItem("ether", ItemGroups.ITEMS);
    public static final Item MAX_ETHER = createItem("max_ether", ItemGroups.ITEMS);
    public static final Item ELIXIR = createItem("elixir", ItemGroups.ITEMS);
    public static final Item MAX_ELIXIR = createItem("max_elixir", ItemGroups.ITEMS);
    public static final Item FULL_RESTORE = createItem("full_restore", ItemGroups.ITEMS);
    public static final Item REVIVE = createItem("revive", ItemGroups.ITEMS);
    public static final Item MAX_REVIVE = createItem("max_revive", ItemGroups.ITEMS);
    public static final Item ANTIDOTE = createItem("antidote", ItemGroups.ITEMS);
    public static final Item PARALYZE_HEAL = createItem("paralyze_heal", ItemGroups.ITEMS);
    public static final Item AWAKENING = createItem("awakening", ItemGroups.ITEMS);
    public static final Item BURN_HEAL = createItem("burn_heal", ItemGroups.ITEMS);
    public static final Item ICE_HEAL = createItem("ice_heal", ItemGroups.ITEMS);
    public static final Item FULL_HEAL = createItem("full_heal", ItemGroups.ITEMS);
    public static final Item HP_UP = createItem("hp_up", ItemGroups.ITEMS);
    public static final Item PROTEIN = createItem("protein", ItemGroups.ITEMS);
    public static final Item IRON = createItem("iron", ItemGroups.ITEMS);
    public static final Item CALCIUM = createItem("calcium", ItemGroups.ITEMS);
    public static final Item ZINC = createItem("zinc", ItemGroups.ITEMS);
    public static final Item CARBOS = createItem("carbos", ItemGroups.ITEMS);
    public static final Item POMEG_BERRY = createItem("pomeg_berry", ItemGroups.ITEMS);
    public static final Item KELPSY_BERRY = createItem("kelpsy_berry", ItemGroups.ITEMS);
    public static final Item QUALOT_BERRY = createItem("qualot_berry", ItemGroups.ITEMS);
    public static final Item HONDEW_BERRY = createItem("hondew_berry", ItemGroups.ITEMS);
    public static final Item GREPA_BERRY = createItem("grepa_berry", ItemGroups.ITEMS);
    public static final Item TAMATO_BERRY = createItem("tamato_berry", ItemGroups.ITEMS);
    public static final Item HEAL_POWDER = createItem("heal_powder", ItemGroups.ITEMS);
    public static final Item ENERGY_POWDER = createItem("energy_powder", ItemGroups.ITEMS);
    public static final Item ENERGY_ROOT = createItem("energy_root", ItemGroups.ITEMS);
    public static final Item REVIVAL_HERB = createItem("revival_herb", ItemGroups.ITEMS);
    public static final Item X_ATTACK = createItem("x_attack", ItemGroups.ITEMS);
    public static final Item X_DEFENCE = createItem("x_defence", ItemGroups.ITEMS);
    public static final Item X_SPECIAL_ATTACK = createItem("x_special_attack", ItemGroups.ITEMS);
    public static final Item X_SPECIAL_DEFENCE = createItem("x_special_defence", ItemGroups.ITEMS);
    public static final Item X_SPEED = createItem("x_speed", ItemGroups.ITEMS);
    public static final Item X_ACCURACY = createItem("x_accuracy", ItemGroups.ITEMS);
    public static final Item DIRE_HIT = createItem("dire_hit", ItemGroups.ITEMS);
    public static final Item GUARD_SPEC = createItem("guard_spec", ItemGroups.ITEMS);
    public static final Item FIRE_STONE = createItem("fire_stone", ItemGroups.ITEMS);
    public static final Item WATER_STONE = createItem("water_stone", ItemGroups.ITEMS);
    public static final Item MOON_STONE = createItem("moon_stone", ItemGroups.ITEMS);
    public static final Item THUNDER_STONE = createItem("thunder_stone", ItemGroups.ITEMS);
    public static final Item LEAF_STONE = createItem("leaf_stone", ItemGroups.ITEMS);
    public static final Item SUN_STONE = createItem("sun_stone", ItemGroups.ITEMS);
    public static final Item DAWN_STONE = createItem("dawn_stone", ItemGroups.ITEMS);
    public static final Item DUSK_STONE = createItem("dusk_stone", ItemGroups.ITEMS);
    public static final Item SHINY_STONE = createItem("shiny_stone", ItemGroups.ITEMS);
    public static final Item THUNDER_STONE_SHARD = createItem("thunder_stone_shard", ItemGroups.ITEMS);
    public static final Item LEAF_STONE_SHARD = createItem("leaf_stone_shard", ItemGroups.ITEMS);
    public static final Item WATER_STONE_SHARD = createItem("water_stone_shard", ItemGroups.ITEMS);
    public static final Item FIRE_STONE_SHARD = createItem("fire_stone_shard", ItemGroups.ITEMS);
    public static final Item SUN_STONE_SHARD = createItem("sun_stone_shard", ItemGroups.ITEMS);
    public static final Item MOON_STONE_SHARD = createItem("moon_stone_shard", ItemGroups.ITEMS);
    public static final Item DAWN_STONE_SHARD = createItem("dawn_stone_shard", ItemGroups.ITEMS);
    public static final Item DUSK_STONE_SHARD = createItem("dusk_stone_shard", ItemGroups.ITEMS);
    public static final Item SHINY_STONE_SHARD = createItem("shiny_stone_shard", ItemGroups.ITEMS);
    public static final Item SWEET_APPLE = createItem("sweet_apple", ItemGroups.ITEMS);
    public static final Item TART_APPLE = createItem("tart_apple", ItemGroups.ITEMS);
    public static final Item GALARICA_CUFF = createItem("galarica_cuff", ItemGroups.ITEMS);
    public static final Item GALARICA_WREATH = createItem("galarica_wreath", ItemGroups.ITEMS);
    public static final Item CRACKED_POT = createItem("cracked_pot", ItemGroups.ITEMS);
    public static final Item CHIPPED_POT = createItem("chipped_pot", ItemGroups.ITEMS);
    public static final Item STAR_SWEET = createItem("star_sweet", ItemGroups.ITEMS);
    public static final Item STRAWBERRY_SWEET = createItem("strawberry_sweet", ItemGroups.ITEMS);
    public static final Item BERRY_SWEET = createItem("berry_sweet", ItemGroups.ITEMS);
    public static final Item CLOVER_SWEET = createItem("clover_sweet", ItemGroups.ITEMS);
    public static final Item FLOWER_SWEET = createItem("flower_sweet", ItemGroups.ITEMS);
    public static final Item LOVE_SWEET = createItem("love_sweet", ItemGroups.ITEMS);
    public static final Item RIBBON_SWEET = createItem("ribbon_sweet", ItemGroups.ITEMS);
    public static final Item WAILMER_PAIL = createItem("wailmer_pail", ItemGroups.ITEMS);
    public static final Item OLD_ROD = createItem("old_rod", ItemGroups.ITEMS);
    public static final Item GOOD_ROD = createItem("good_rod", ItemGroups.ITEMS);
    public static final Item SUPER_ROD = createItem("super_rod", ItemGroups.ITEMS);
    public static final Item CHISEL = createItem("chisel", ItemGroups.ITEMS);
    public static final Item RANCH_UPGRADE = createItem("ranch_upgrade", ItemGroups.ITEMS);
    public static final Item TRADE_MONITOR = createItem("trade_monitor", ItemGroups.ITEMS);
    public static final Item TRADE_HOLDER_RIGHT = createItem("trade_holder_right", ItemGroups.ITEMS);
    public static final Item LTRADE_HOLDER_LEFT = createItem("ltrade_holder_left", ItemGroups.ITEMS);
    public static final Item TRADE_PANEL = createItem("trade_panel", ItemGroups.ITEMS);
    public static final Item ORB = createItem("orb", ItemGroups.ITEMS);
    public static final Item UNO_ORB = createItem("uno_orb", ItemGroups.ITEMS);
    public static final Item DOS_ORB = createItem("dos_orb", ItemGroups.ITEMS);
    public static final Item TRES_ORB = createItem("tres_orb", ItemGroups.ITEMS);
    public static final Item MYSTICAL_ORB = createItem("mystical_orb", ItemGroups.ITEMS);
    public static final Item MARTIAL_ORB = createItem("martial_orb", ItemGroups.ITEMS);
    public static final Item MALEVOLENT_ORB = createItem("malevolent_orb", ItemGroups.ITEMS);
    public static final Item PIXELMON_EDITOR = createItem("pixelmon_editor", ItemGroups.ITEMS);
    public static final Item GROTTO_SPAWNER = createItem("grotto_spawner", ItemGroups.ITEMS);
    public static final Item CLONING_MACHINE_GREEN_ITEM = createItem("cloning_machine_green_item", ItemGroups.ITEMS);
    public static final Item CLONING_MACHINE_ORANGE_ITEM = createItem("cloning_machine_orange_item", ItemGroups.ITEMS);
    public static final Item CLONING_MACHINE_CORD_ITEM = createItem("cloning_machine_cord_item", ItemGroups.ITEMS);
    public static final Item BIKE_FRAME = createItem("bike_frame", ItemGroups.ITEMS);
    public static final Item BIKE_HANDLEBARS = createItem("bike_handlebars", ItemGroups.ITEMS);
    public static final Item BIKE_SEAT = createItem("bike_seat", ItemGroups.ITEMS);
    public static final Item BIKE_WHEEL = createItem("bike_wheel", ItemGroups.ITEMS);
    public static final Item HOURGLASS_GOLD = createItem("hourglass_gold", ItemGroups.ITEMS);
    public static final Item HOURGLASS_SILVER = createItem("hourglass_silver", ItemGroups.ITEMS);
    public static final Item HIDDEN_IRON_DOOR_ITEM = createItem("hidden_iron_door_item", ItemGroups.ITEMS);
    public static final Item HIDDEN_WOODEN_DOOR_ITEM = createItem("hidden_wooden_door_item", ItemGroups.ITEMS);
    public static final Item GIFT = createItem("gift", ItemGroups.ITEMS);
    public static final Item CANDY = createItem("candy", ItemGroups.ITEMS);
    public static final Item ITEM_PIXELMON_SPRITE = createItem("item_pixelmon_sprite", ItemGroups.ITEMS);
    public static final Item ITEM_CUSTOM_ICON = createItem("item_custom_icon", ItemGroups.ITEMS);
    public static final Item PIXELMON_PAINTING_ITEM = createItem("pixelmon_painting_item", ItemGroups.ITEMS);
    public static final Item CAMERA_ITEM = createItem("camera_item", ItemGroups.ITEMS);
    public static final Item FILM_ITEM = createItem("film_item", ItemGroups.ITEMS);
    public static final Item TREE_ITEM = createItem("tree_item", ItemGroups.ITEMS);
    public static final Item AMETHYST = createItem("amethyst", ItemGroups.ITEMS);
    public static final Item RUBY = createItem("ruby", ItemGroups.ITEMS);
    public static final Item CRYSTAL = createItem("crystal", ItemGroups.ITEMS);
    public static final Item SAPPHIRE = createItem("sapphire", ItemGroups.ITEMS);
    public static final Item SILICON_ITEM = createItem("silicon_item", ItemGroups.ITEMS);
    public static final Item PICKET_FENCE_ITEM = createItem("picket_fence_item", ItemGroups.ITEMS);
    public static final Item ALUMINIUM_INGOT = createItem("aluminium_ingot", ItemGroups.ITEMS);
    public static final Item ALUMINIUM_PLATE = createItem("aluminium_plate", ItemGroups.ITEMS);
    public static final Item ABILITY_CAPSULE = createItem("ability_capsule", ItemGroups.ITEMS);
    public static final Item ABILITY_PATCH = createItem("ability_patch", ItemGroups.ITEMS);
    public static final Item EXP_ALL = createItem("exp_all", ItemGroups.ITEMS);
    public static final Item METEORITE = createItem("meteorite", ItemGroups.ITEMS);
    public static final Item GRACIDEA = createItem("gracidea", ItemGroups.ITEMS);
    public static final Item REVEAL_GLASS = createItem("reveal_glass", ItemGroups.ITEMS);
    public static final Item MIRROR = createItem("mirror", ItemGroups.ITEMS);
    public static final Item REDCHAIN = createItem("redchain", ItemGroups.ITEMS);
    public static final Item REPEL = createItem("repel", ItemGroups.ITEMS);
    public static final Item SUPER_REPEL = createItem("super_repel", ItemGroups.ITEMS);
    public static final Item MAX_REPEL = createItem("max_repel", ItemGroups.ITEMS);
    public static final Item DNA_SPLICERS = createItem("dna_splicers", ItemGroups.ITEMS);
    public static final Item REINS_OF_UNITY = createItem("reins_of_unity", ItemGroups.ITEMS);
    public static final Item CORRUPTED_GEM = createItem("corrupted_gem", ItemGroups.ITEMS);
    public static final Item N_SOLARIZER = createItem("n_solarizer", ItemGroups.ITEMS);
    public static final Item N_LUNARIZER = createItem("n_lunarizer", ItemGroups.ITEMS);
    public static final Item PORYGON_PIECES = createItem("porygon_pieces", ItemGroups.ITEMS);
    public static final Item LEGEND_FINDER = createItem("legend_finder", ItemGroups.ITEMS);
    public static final Item FADED_RED_ORB = createItem("faded_red_orb", ItemGroups.ITEMS);
    public static final Item FADED_BLUE_ORB = createItem("faded_blue_orb", ItemGroups.ITEMS);
    public static final Item ROCK_STAR_COSTUME = createItem("rock_star_costume", ItemGroups.ITEMS);
    public static final Item BELLE_COSTUME = createItem("belle_costume", ItemGroups.ITEMS);
    public static final Item POP_STAR_COSTUME = createItem("pop_star_costume", ItemGroups.ITEMS);
    public static final Item PHD_COSTUME = createItem("phd_costume", ItemGroups.ITEMS);
    public static final Item LIBRE_COSTUME = createItem("libre_costume", ItemGroups.ITEMS);
    public static final Item MEWTWO_ARMOR = createItem("mewtwo_armor", ItemGroups.ITEMS);
    public static final Item BALM_MUSHROOM = createItem("balm_mushroom", ItemGroups.ITEMS);
    public static final Item BIG_MUSHROOM = createItem("big_mushroom", ItemGroups.ITEMS);
    public static final Item BIG_NUGGET = createItem("big_nugget", ItemGroups.ITEMS);
    public static final Item BIG_PEARL = createItem("big_pearl", ItemGroups.ITEMS);
    public static final Item GOLD_LEAF = createItem("gold_leaf", ItemGroups.ITEMS);
    public static final Item SMALL_BOUQUET = createItem("small_bouquet", ItemGroups.ITEMS);
    public static final Item BLUE_SHARD = createItem("blue_shard", ItemGroups.ITEMS);
    public static final Item COMET_SHARD = createItem("comet_shard", ItemGroups.ITEMS);
    public static final Item GREEN_SHARD = createItem("green_shard", ItemGroups.ITEMS);
    public static final Item NUGGET = createItem("nugget", ItemGroups.ITEMS);
    public static final Item PEARL = createItem("pearl", ItemGroups.ITEMS);
    public static final Item PEARL_STRING = createItem("pearl_string", ItemGroups.ITEMS);
    public static final Item RED_SHARD = createItem("red_shard", ItemGroups.ITEMS);
    public static final Item STAR_PIECE = createItem("star_piece", ItemGroups.ITEMS);
    public static final Item TINY_MUSHROOM = createItem("tiny_mushroom", ItemGroups.ITEMS);
    public static final Item YELLOW_SHARD = createItem("yellow_shard", ItemGroups.ITEMS);
    public static final Item ICE_STONE = createItem("ice_stone", ItemGroups.ITEMS);
    public static final Item ICE_STONE_SHARD = createItem("ice_stone_shard", ItemGroups.ITEMS);
    public static final Item PINK_NECTAR = createItem("pink_nectar", ItemGroups.ITEMS);
    public static final Item PURPLE_NECTAR = createItem("purple_nectar", ItemGroups.ITEMS);
    public static final Item RED_NECTAR = createItem("red_nectar", ItemGroups.ITEMS);
    public static final Item YELLOW_NECTAR = createItem("yellow_nectar", ItemGroups.ITEMS);
    public static final Item PP_UP = createItem("pp_up", ItemGroups.ITEMS);
    public static final Item MAX_PP = createItem("max_pp", ItemGroups.ITEMS);
    public static final Item RED_BIKE = createItem("red_bike", ItemGroups.ITEMS);
    public static final Item ORANGE_BIKE = createItem("orange_bike", ItemGroups.ITEMS);
    public static final Item YELLOW_BIKE = createItem("yellow_bike", ItemGroups.ITEMS);
    public static final Item GREEN_BIKE = createItem("green_bike", ItemGroups.ITEMS);
    public static final Item BLUE_BIKE = createItem("blue_bike", ItemGroups.ITEMS);
    public static final Item PURPLE_BIKE = createItem("purple_bike", ItemGroups.ITEMS);
    public static final Item SUN_FLUTE = createItem("sun_flute", ItemGroups.ITEMS);
    public static final Item MOON_FLUTE = createItem("moon_flute", ItemGroups.ITEMS);
    public static final Item RUSTY_FRAGMENT = createItem("rusty_fragment", ItemGroups.ITEMS);
    public static final Item RUSTY_SWORD = createItem("rusty_sword", ItemGroups.ITEMS);
    public static final Item RUSTY_SHIELD = createItem("rusty_shield", ItemGroups.ITEMS);
    public static final Item MINT_SERIOUS = createItem("mint_serious", ItemGroups.ITEMS);
    public static final Item MINT_LONELY = createItem("mint_lonely", ItemGroups.ITEMS);
    public static final Item MINT_BRAVE = createItem("mint_brave", ItemGroups.ITEMS);
    public static final Item MINT_ADAMANT = createItem("mint_adamant", ItemGroups.ITEMS);
    public static final Item MINT_NAUGHTY = createItem("mint_naughty", ItemGroups.ITEMS);
    public static final Item MINT_BOLD = createItem("mint_bold", ItemGroups.ITEMS);
    public static final Item MINT_RELAXED = createItem("mint_relaxed", ItemGroups.ITEMS);
    public static final Item MINT_IMPISH = createItem("mint_impish", ItemGroups.ITEMS);
    public static final Item MINT_GENTLE = createItem("mint_gentle", ItemGroups.ITEMS);
    public static final Item MINT_LAX = createItem("mint_lax", ItemGroups.ITEMS);
    public static final Item MINT_TIMID = createItem("mint_timid", ItemGroups.ITEMS);
    public static final Item MINT_HASTY = createItem("mint_hasty", ItemGroups.ITEMS);
    public static final Item MINT_JOLLY = createItem("mint_jolly", ItemGroups.ITEMS);
    public static final Item MINT_NAIVE = createItem("mint_naive", ItemGroups.ITEMS);
    public static final Item MINT_MODEST = createItem("mint_modest", ItemGroups.ITEMS);
    public static final Item MINT_MILD = createItem("mint_mild", ItemGroups.ITEMS);
    public static final Item MINT_QUIET = createItem("mint_quiet", ItemGroups.ITEMS);
    public static final Item MINT_RASH = createItem("mint_rash", ItemGroups.ITEMS);
    public static final Item MINT_CALM = createItem("mint_calm", ItemGroups.ITEMS);
    public static final Item MINT_SASSY = createItem("mint_sassy", ItemGroups.ITEMS);
    public static final Item MINT_CAREFUL = createItem("mint_careful", ItemGroups.ITEMS);
    public static final Item EXP_CANDY_X_S = createItem("exp_candy_x_s", ItemGroups.ITEMS);
    public static final Item EXP_CANDY_S = createItem("exp_candy_s", ItemGroups.ITEMS);
    public static final Item EXP_CANDY_M = createItem("exp_candy_m", ItemGroups.ITEMS);
    public static final Item EXP_CANDY_L = createItem("exp_candy_l", ItemGroups.ITEMS);
    public static final Item EXP_CANDY_X_L = createItem("exp_candy_x_l", ItemGroups.ITEMS);
    public static final Item ZYGARDE_CUBE = createItem("zygarde_cube", ItemGroups.ITEMS);
    public static final Item MELTAN_BOX = createItem("meltan_box", ItemGroups.ITEMS);
    public static final Item LURE_MODULE = createItem("lure_module", ItemGroups.ITEMS);
    public static final Item TIME_GLASS = createItem("time_glass", ItemGroups.ITEMS);
    public static final Item ROTOM_CATALOG = createItem("rotom_catalog", ItemGroups.ITEMS);
    public static final Item JEWEL_OF_LIFE = createItem("jewel_of_life", ItemGroups.ITEMS);
    public static final Item BASIC_SWEET_POKE_PUFF = createItem("basic_sweet_poke_puff", ItemGroups.ITEMS);
    public static final Item BASIC_MINT_POKE_PUFF = createItem("basic_mint_poke_puff", ItemGroups.ITEMS);
    public static final Item BASIC_CITRUS_POKE_PUFF = createItem("basic_citrus_poke_puff", ItemGroups.ITEMS);
    public static final Item BASIC_MOCHA_POKE_PUFF = createItem("basic_mocha_poke_puff", ItemGroups.ITEMS);
    public static final Item BASIC_SPICE_POKE_PUFF = createItem("basic_spice_poke_puff", ItemGroups.ITEMS);
    public static final Item ROOM_SERVICE = createItem("room_service", ItemGroups.ITEMS);
    public static final Item POKEDEX = createItem("pokedex", ItemGroups.ITEMS);
    public static final Item HOOPA_RING = createItem("hoopa_ring", ItemGroups.ITEMS);
    public static final Item ZONE_WAND = createItem("zone_wand", ItemGroups.ITEMS);
    public static final Item LAVA_CRYSTAL = createItem("lava_crystal", ItemGroups.ITEMS);
    public static final Item MAGMA_CRYSTAL = createItem("magma_crystal", ItemGroups.ITEMS);
    public static final Item RELIC_SONG_RECORD = createItem("relic_song_record", ItemGroups.ITEMS);
    public static final Item SHATTERED_RELIC_SONG1 = createItem("shattered_relic_song1", ItemGroups.ITEMS);
    public static final Item SHATTERED_RELIC_SONG2 = createItem("shattered_relic_song2", ItemGroups.ITEMS);
    public static final Item SHATTERED_RELIC_SONG3 = createItem("shattered_relic_song3", ItemGroups.ITEMS);
    public static final Item SHATTERED_RELIC_SONG4 = createItem("shattered_relic_song4", ItemGroups.ITEMS);
    public static final Item ROCK_PEAK_KEY = createItem("rock_peak_key", ItemGroups.ITEMS);
    public static final Item ICEBERG_KEY = createItem("iceberg_key", ItemGroups.ITEMS);
    public static final Item IRON_KEY = createItem("iron_key", ItemGroups.ITEMS);
    public static final Item DRAGO_KEY = createItem("drago_key", ItemGroups.ITEMS);
    public static final Item ELEKI_KEY = createItem("eleki_key", ItemGroups.ITEMS);
    public static final Item SHATTERED_ICE_KEY1 = createItem("shattered_ice_key1", ItemGroups.ITEMS);
    public static final Item SHATTERED_ICE_KEY2 = createItem("shattered_ice_key2", ItemGroups.ITEMS);
    public static final Item SHATTERED_ICE_KEY3 = createItem("shattered_ice_key3", ItemGroups.ITEMS);
    public static final Item SHATTERED_ICE_KEY4 = createItem("shattered_ice_key4", ItemGroups.ITEMS);
    public static final Item CRUMBLED_ROCK_KEY1 = createItem("crumbled_rock_key1", ItemGroups.ITEMS);
    public static final Item CRUMBLED_ROCK_KEY2 = createItem("crumbled_rock_key2", ItemGroups.ITEMS);
    public static final Item CRUMBLED_ROCK_KEY3 = createItem("crumbled_rock_key3", ItemGroups.ITEMS);
    public static final Item CRUMBLED_ROCK_KEY4 = createItem("crumbled_rock_key4", ItemGroups.ITEMS);
    public static final Item RUSTY_IRON_KEY1 = createItem("rusty_iron_key1", ItemGroups.ITEMS);
    public static final Item RUSTY_IRON_KEY2 = createItem("rusty_iron_key2", ItemGroups.ITEMS);
    public static final Item RUSTY_IRON_KEY3 = createItem("rusty_iron_key3", ItemGroups.ITEMS);
    public static final Item RUSTY_IRON_KEY4 = createItem("rusty_iron_key4", ItemGroups.ITEMS);
    public static final Item FRAGMENTED_DRAGO_KEY1 = createItem("fragmented_drago_key1", ItemGroups.ITEMS);
    public static final Item FRAGMENTED_DRAGO_KEY2 = createItem("fragmented_drago_key2", ItemGroups.ITEMS);
    public static final Item FRAGMENTED_DRAGO_KEY3 = createItem("fragmented_drago_key3", ItemGroups.ITEMS);
    public static final Item FRAGMENTED_DRAGO_KEY4 = createItem("fragmented_drago_key4", ItemGroups.ITEMS);
    public static final Item DISCHARGED_ELEKI_KEY1 = createItem("discharged_eleki_key1", ItemGroups.ITEMS);
    public static final Item DISCHARGED_ELEKI_KEY2 = createItem("discharged_eleki_key2", ItemGroups.ITEMS);
    public static final Item DISCHARGED_ELEKI_KEY3 = createItem("discharged_eleki_key3", ItemGroups.ITEMS);
    public static final Item DISCHARGED_ELEKI_KEY4 = createItem("discharged_eleki_key4", ItemGroups.ITEMS);
    public static final Item SCROLL_PAGE = createItem("scroll_page", ItemGroups.ITEMS);
    public static final Item SECRET_ARMOR_SCROLL = createItem("secret_armor_scroll", ItemGroups.ITEMS);
    public static final Item LIGHT_STONE = createItem("light_stone", ItemGroups.ITEMS);
    public static final Item DRAGON_STONE = createItem("dragon_stone", ItemGroups.ITEMS);
    public static final Item DARK_STONE = createItem("dark_stone", ItemGroups.ITEMS);
    public static final Item RAINBOW_WING = createItem("rainbow_wing", ItemGroups.ITEMS);
    public static final Item DARK_SOUL = createItem("dark_soul", ItemGroups.ITEMS);
    public static final Item DRAGON_SOUL = createItem("dragon_soul", ItemGroups.ITEMS);
    public static final Item SPARKLING_STONE = createItem("sparkling_stone", ItemGroups.ITEMS);
    public static final Item SPARKLING_SHARD = createItem("sparkling_shard", ItemGroups.ITEMS);
    public static final Item MELODY_FLUTE = createItem("melody_flute", ItemGroups.ITEMS);
    public static final Item CURRY = createItem("curry", ItemGroups.ITEMS);
    public static final Item LAVA_COOKIE = createItem("lava_cookie", ItemGroups.ITEMS);
    public static final Item RAGE_CANDY_BAR = createItem("rage_candy_bar", ItemGroups.ITEMS);
    public static final Item BIG_MALASADA = createItem("big_malasada", ItemGroups.ITEMS);
    public static final Item OLD_GATEAU = createItem("old_gateau", ItemGroups.ITEMS);
    public static final Item CASTELIACONE = createItem("casteliacone", ItemGroups.ITEMS);
    public static final Item LUMIOSE_GALETTE = createItem("lumiose_galette", ItemGroups.ITEMS);
    public static final Item SHALOUR_SABLE = createItem("shalour_sable", ItemGroups.ITEMS);
    public static final Item BOTTLE_CAP = createItem("bottle_cap", ItemGroups.ITEMS);
    public static final Item GOLD_BOTTLE_CAP = createItem("gold_bottle_cap", ItemGroups.ITEMS);
    public static final Item RELIC_GOLD = createItem("relic_gold", ItemGroups.ITEMS);
    public static final Item RELIC_COPPER = createItem("relic_copper", ItemGroups.ITEMS);
    public static final Item RELIC_BAND = createItem("relic_band", ItemGroups.ITEMS);
    public static final Item RELIC_SILVER = createItem("relic_silver", ItemGroups.ITEMS);
    public static final Item RELIC_STATUE = createItem("relic_statue", ItemGroups.ITEMS);
    public static final Item RELIC_VASE = createItem("relic_vase", ItemGroups.ITEMS);
    public static final Item RELIC_CROWN = createItem("relic_crown", ItemGroups.ITEMS);
    public static final Item HEART_SCALE = createItem("heart_scale", ItemGroups.ITEMS);

    private static BadgeItem createBadge(String name, Object elementType) {
        return generate(new BadgeItem(new Item.Settings().group(ItemGroups.BADGES)), name);
    }

    /**
     * Creates a generic item.
     *
     * @param name the name of the item
     */
    private static Item createItem(String name) {
        return generate(new Item(new Item.Settings()), name);
    }

    /**
     * Creates a generic item.
     *
     * @param name  the name of the item
     * @param group the {@link net.minecraft.item.ItemGroup} it should be placed in.
     */
    private static Item createItem(String name, ItemGroup group) {
        return generate(new Item(new Item.Settings().group(group)), name);
    }

    private static Item createApricorn(String name, Block bush) {
        return generate(new ApricornBlockItem(bush, new Item.Settings().group(ItemGroups.ORGANIC)), name);
    }

    private static Item createBakedApricorn(String name) {
        return generate(new Item(new Item.Settings().group(ItemGroups.ORGANIC)), "baked_" + name);
    }

    private static Item createPokeball(String name) {
        return generate(new PokeballItem(new Item.Settings().maxCount(16).group(ItemGroups.POKEBALLS)), name);
    }

    /**
     * Used to generate a basic item model
     *
     * @param item the item to generate the model for
     * @param id   the id of the item
     */
    public static <T extends ItemConvertible> T generate(T item, String id) {
        Identifier identifier = OpenPixelmon.id(id);
        Identifier modelIdentifier = OpenPixelmon.id("item/" + id);
        OpenPixelmon.RESOURCE_PACK.addModel(new JModel()
                        .parent("item/generated")
                        .textures(new JTextures()
                                .layer0("pixelmon:item/" + id)),
                modelIdentifier);
        Registry.register(Registry.ITEM, identifier, (Item) item);
        return item;
    }

    @Override
    public void onInitialize() {
    }
}
