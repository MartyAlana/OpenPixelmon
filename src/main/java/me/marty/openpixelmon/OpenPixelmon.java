package me.marty.openpixelmon;

import me.marty.openpixelmon.command.Commands;
import me.marty.openpixelmon.compatibility.PixelmonAssetProvider;
import me.marty.openpixelmon.compatibility.PixelmonGenerationsCompatibility;
import me.marty.openpixelmon.entity.PixelmonDataTrackers;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OpenPixelmon implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("pixelmon");

    public static void main(String[] args) {
        String ugh = "    public static final Item ITEM_FINDER = createItem(\"item_finder\", ItemGroups.ITEMS);\n" +
                "    public static final Item RARE_CANDY = createItem(\"rare_candy\", ItemGroups.ITEMS);\n" +
                "    public static final Item DYNAMAX_CANDY = createItem(\"dynamax_candy\", ItemGroups.ITEMS);\n" +
                "    public static final Item POTION = createItem(\"potion\", ItemGroups.ITEMS);\n" +
                "    public static final Item SUPER_POTION = createItem(\"super_potion\", ItemGroups.ITEMS);\n" +
                "    public static final Item HYPER_POTION = createItem(\"hyper_potion\", ItemGroups.ITEMS);\n" +
                "    public static final Item MAX_POTION = createItem(\"max_potion\", ItemGroups.ITEMS);\n" +
                "    public static final Item FRESH_WATER = createItem(\"fresh_water\", ItemGroups.ITEMS);\n" +
                "    public static final Item SODA_POP = createItem(\"soda_pop\", ItemGroups.ITEMS);\n" +
                "    public static final Item LEMONADE = createItem(\"lemonade\", ItemGroups.ITEMS);\n" +
                "    public static final Item MOOMOO_MILK = createItem(\"moomoo_milk\", ItemGroups.ITEMS);\n" +
                "    public static final Item ETHER = createItem(\"ether\", ItemGroups.ITEMS);\n" +
                "    public static final Item MAX_ETHER = createItem(\"max_ether\", ItemGroups.ITEMS);\n" +
                "    public static final Item elixir = createItem(\"elixir\", ItemGroups.ITEMS);\n" +
                "    public static final Item maxElixir = createItem(\"max_elixir\", ItemGroups.ITEMS);\n" +
                "    public static final Item fullRestore = createItem(\"full_restore\", ItemGroups.ITEMS);\n" +
                "    public static final Item revive = createItem(\"revive\", ItemGroups.ITEMS);\n" +
                "    public static final Item maxRevive = createItem(\"max_revive\", ItemGroups.ITEMS);\n" +
                "    public static final Item antidote = createItem(\"antidote\", ItemGroups.ITEMS);\n" +
                "    public static final Item paralyzeHeal = createItem(\"paralyze_heal\", ItemGroups.ITEMS);\n" +
                "    public static final Item awakening = createItem(\"awakening\", ItemGroups.ITEMS);\n" +
                "    public static final Item burnHeal = createItem(\"burn_heal\", ItemGroups.ITEMS);\n" +
                "    public static final Item iceHeal = createItem(\"ice_heal\", ItemGroups.ITEMS);\n" +
                "    public static final Item fullHeal = createItem(\"full_heal\", ItemGroups.ITEMS);\n" +
                "    public static final Item HpUp = createItem(\"hp_up\", ItemGroups.ITEMS);\n" +
                "    public static final Item Protein = createItem(\"protein\", ItemGroups.ITEMS);\n" +
                "    public static final Item Iron = createItem(\"iron\", ItemGroups.ITEMS);\n" +
                "    public static final Item Calcium = createItem(\"calcium\", ItemGroups.ITEMS);\n" +
                "    public static final Item Zinc = createItem(\"zinc\", ItemGroups.ITEMS);\n" +
                "    public static final Item Carbos = createItem(\"carbos\", ItemGroups.ITEMS);\n" +
                "    public static final Item pomegBerry = createItem(\"pomeg_berry\", ItemGroups.ITEMS);\n" +
                "    public static final Item kelpsyBerry = createItem(\"kelpsy_berry\", ItemGroups.ITEMS);\n" +
                "    public static final Item qualotBerry = createItem(\"qualot_berry\", ItemGroups.ITEMS);\n" +
                "    public static final Item hondewBerry = createItem(\"hondew_berry\", ItemGroups.ITEMS);\n" +
                "    public static final Item grepaBerry = createItem(\"grepa_berry\", ItemGroups.ITEMS);\n" +
                "    public static final Item tamatoBerry = createItem(\"tamato_berry\", ItemGroups.ITEMS);\n" +
                "    public static final Item healPowder = createItem(\"heal_powder\", ItemGroups.ITEMS);\n" +
                "    public static final Item energyPowder = createItem(\"energy_powder\", ItemGroups.ITEMS);\n" +
                "    public static final Item energyRoot = createItem(\"energy_root\", ItemGroups.ITEMS);\n" +
                "    public static final Item revivalHerb = createItem(\"revival_herb\", ItemGroups.ITEMS);\n" +
                "    public static final Item xAttack = createItem(\"x_attack\", ItemGroups.ITEMS);\n" +
                "    public static final Item xDefence = createItem(\"x_defence\", ItemGroups.ITEMS);\n" +
                "    public static final Item xSpecialAttack = createItem(\"x_special_attack\", ItemGroups.ITEMS);\n" +
                "    public static final Item xSpecialDefence = createItem(\"x_special_defence\", ItemGroups.ITEMS);\n" +
                "    public static final Item xSpeed = createItem(\"x_speed\", ItemGroups.ITEMS);\n" +
                "    public static final Item xAccuracy = createItem(\"x_accuracy\", ItemGroups.ITEMS);\n" +
                "    public static final Item direHit = createItem(\"dire_hit\", ItemGroups.ITEMS);\n" +
                "    public static final Item guardSpec = createItem(\"guard_spec\", ItemGroups.ITEMS);\n" +
                "    public static final Item fireStone = createItem(\"fire_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item waterStone = createItem(\"water_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item moonStone = createItem(\"moon_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item thunderStone = createItem(\"thunder_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item leafStone = createItem(\"leaf_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item sunStone = createItem(\"sun_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item dawnStone = createItem(\"dawn_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item duskStone = createItem(\"dusk_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item shinyStone = createItem(\"shiny_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item thunderStoneShard = createItem(\"thunder_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item leafStoneShard = createItem(\"leaf_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item waterStoneShard = createItem(\"water_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item fireStoneShard = createItem(\"fire_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item sunStoneShard = createItem(\"sun_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item moonStoneShard = createItem(\"moon_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item dawnStoneShard = createItem(\"dawn_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item duskStoneShard = createItem(\"dusk_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item shinyStoneShard = createItem(\"shiny_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item sweetApple = createItem(\"sweet_apple\", ItemGroups.ITEMS);\n" +
                "    public static final Item tartApple = createItem(\"tart_apple\", ItemGroups.ITEMS);\n" +
                "    public static final Item galaricaCuff = createItem(\"galarica_cuff\", ItemGroups.ITEMS);\n" +
                "    public static final Item galaricaWreath = createItem(\"galarica_wreath\", ItemGroups.ITEMS);\n" +
                "    public static final Item crackedPot = createItem(\"cracked_pot\", ItemGroups.ITEMS);\n" +
                "    public static final Item chippedPot = createItem(\"chipped_pot\", ItemGroups.ITEMS);\n" +
                "    public static final Item starSweet = createItem(\"star_sweet\", ItemGroups.ITEMS);\n" +
                "    public static final Item strawberrySweet = createItem(\"strawberry_sweet\", ItemGroups.ITEMS);\n" +
                "    public static final Item berrySweet = createItem(\"berry_sweet\", ItemGroups.ITEMS);\n" +
                "    public static final Item cloverSweet = createItem(\"clover_sweet\", ItemGroups.ITEMS);\n" +
                "    public static final Item flowerSweet = createItem(\"flower_sweet\", ItemGroups.ITEMS);\n" +
                "    public static final Item loveSweet = createItem(\"love_sweet\", ItemGroups.ITEMS);\n" +
                "    public static final Item ribbonSweet = createItem(\"ribbon_sweet\", ItemGroups.ITEMS);\n" +
                "    public static final Item wailmerPail = createItem(\"wailmer_pail\", ItemGroups.ITEMS);\n" +
                "    public static final Item oldRod = createItem(\"old_rod\", ItemGroups.ITEMS);\n" +
                "    public static final Item goodRod = createItem(\"good_rod\", ItemGroups.ITEMS);\n" +
                "    public static final Item superRod = createItem(\"super_rod\", ItemGroups.ITEMS);\n" +
                "    public static final Item chisel = createItem(\"chisel\", ItemGroups.ITEMS);\n" +
                "    public static final Item ranchUpgrade = createItem(\"ranch_upgrade\", ItemGroups.ITEMS);\n" +
                "    public static final Item tradeMonitor = createItem(\"trade_monitor\", ItemGroups.ITEMS);\n" +
                "    public static final Item tradeHolderRight = createItem(\"trade_holder_right\", ItemGroups.ITEMS);\n" +
                "    public static final Item LtradeHolderLeft = createItem(\"ltrade_holder_left\", ItemGroups.ITEMS);\n" +
                "    public static final Item tradePanel = createItem(\"trade_panel\", ItemGroups.ITEMS);\n" +
                "    public static final Item orb = createItem(\"orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item unoOrb = createItem(\"uno_orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item dosOrb = createItem(\"dos_orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item tresOrb = createItem(\"tres_orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item mysticalOrb = createItem(\"mystical_orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item martialOrb = createItem(\"martial_orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item malevolentOrb = createItem(\"malevolent_orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item pixelmonEditor = createItem(\"pixelmon_editor\", ItemGroups.ITEMS);\n" +
                "    public static final Item grottoSpawner = createItem(\"grotto_spawner\", ItemGroups.ITEMS);\n" +
                "    public static final Item cloningMachineGreenItem = createItem(\"cloning_machine_green_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item cloningMachineOrangeItem = createItem(\"cloning_machine_orange_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item cloningMachineCordItem = createItem(\"cloning_machine_cord_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item bikeFrame = createItem(\"bike_frame\", ItemGroups.ITEMS);\n" +
                "    public static final Item bikeHandlebars = createItem(\"bike_handlebars\", ItemGroups.ITEMS);\n" +
                "    public static final Item bikeSeat = createItem(\"bike_seat\", ItemGroups.ITEMS);\n" +
                "    public static final Item bikeWheel = createItem(\"bike_wheel\", ItemGroups.ITEMS);\n" +
                "    public static final Item hourglassGold = createItem(\"hourglass_gold\", ItemGroups.ITEMS);\n" +
                "    public static final Item hourglassSilver = createItem(\"hourglass_silver\", ItemGroups.ITEMS);\n" +
                "    public static final Item hiddenIronDoorItem = createItem(\"hidden_iron_door_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item hiddenWoodenDoorItem = createItem(\"hidden_wooden_door_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item gift = createItem(\"gift\", ItemGroups.ITEMS);\n" +
                "    public static final Item candy = createItem(\"candy\", ItemGroups.ITEMS);\n" +
                "    public static final Item itemPixelmonSprite = createItem(\"item_pixelmon_sprite\", ItemGroups.ITEMS);\n" +
                "    public static final Item itemCustomIcon = createItem(\"item_custom_icon\", ItemGroups.ITEMS);\n" +
                "    public static final Item pixelmonPaintingItem = createItem(\"pixelmon_painting_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item cameraItem = createItem(\"camera_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item filmItem = createItem(\"film_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item treeItem = createItem(\"tree_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item amethyst = createItem(\"amethyst\", ItemGroups.ITEMS);\n" +
                "    public static final Item ruby = createItem(\"ruby\", ItemGroups.ITEMS);\n" +
                "    public static final Item crystal = createItem(\"crystal\", ItemGroups.ITEMS);\n" +
                "    public static final Item sapphire = createItem(\"sapphire\", ItemGroups.ITEMS);\n" +
                "    public static final Item siliconItem = createItem(\"silicon_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item picketFenceItem = createItem(\"picket_fence_item\", ItemGroups.ITEMS);\n" +
                "    public static final Item aluminiumIngot = createItem(\"aluminium_ingot\", ItemGroups.ITEMS);\n" +
                "    public static final Item aluminiumPlate = createItem(\"aluminium_plate\", ItemGroups.ITEMS);\n" +
                "    public static final Item abilityCapsule = createItem(\"ability_capsule\", ItemGroups.ITEMS);\n" +
                "    public static final Item abilityPatch = createItem(\"ability_patch\", ItemGroups.ITEMS);\n" +
                "    public static final Item EXP_ALL = createItem(\"exp_all\", ItemGroups.ITEMS);\n" +
                "    public static final Item meteorite = createItem(\"meteorite\", ItemGroups.ITEMS);\n" +
                "    public static final Item gracidea = createItem(\"gracidea\", ItemGroups.ITEMS);\n" +
                "    public static final Item reveal_glass = createItem(\"reveal_glass\", ItemGroups.ITEMS);\n" +
                "    public static final Item mirror = createItem(\"mirror\", ItemGroups.ITEMS);\n" +
                "    public static final Item redchain = createItem(\"redchain\", ItemGroups.ITEMS);\n" +
                "    public static final Item repel = createItem(\"repel\", ItemGroups.ITEMS);\n" +
                "    public static final Item superRepel = createItem(\"super_repel\", ItemGroups.ITEMS);\n" +
                "    public static final Item maxRepel = createItem(\"max_repel\", ItemGroups.ITEMS);\n" +
                "    public static final Item dnaSplicers = createItem(\"dna_splicers\", ItemGroups.ITEMS);\n" +
                "    public static final Item reinsOfUnity = createItem(\"reins_of_unity\", ItemGroups.ITEMS);\n" +
                "    public static final Item corruptedGem = createItem(\"corrupted_gem\", ItemGroups.ITEMS);\n" +
                "    public static final Item nSolarizer = createItem(\"n_solarizer\", ItemGroups.ITEMS);\n" +
                "    public static final Item nLunarizer = createItem(\"n_lunarizer\", ItemGroups.ITEMS);\n" +
                "    public static final Item porygonPieces = createItem(\"porygon_pieces\", ItemGroups.ITEMS);\n" +
                "    public static final Item legendFinder = createItem(\"legend_finder\", ItemGroups.ITEMS);\n" +
                "    public static final Item fadedRedOrb = createItem(\"faded_red_orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item fadedBlueOrb = createItem(\"faded_blue_orb\", ItemGroups.ITEMS);\n" +
                "    public static final Item rockStarCostume = createItem(\"rock_star_costume\", ItemGroups.ITEMS);\n" +
                "    public static final Item belleCostume = createItem(\"belle_costume\", ItemGroups.ITEMS);\n" +
                "    public static final Item popStarCostume = createItem(\"pop_star_costume\", ItemGroups.ITEMS);\n" +
                "    public static final Item phdCostume = createItem(\"phd_costume\", ItemGroups.ITEMS);\n" +
                "    public static final Item libreCostume = createItem(\"libre_costume\", ItemGroups.ITEMS);\n" +
                "    public static final Item mewtwoArmor = createItem(\"mewtwo_armor\", ItemGroups.ITEMS);\n" +
                "    public static final Item balmMushroom = createItem(\"balm_mushroom\", ItemGroups.ITEMS);\n" +
                "    public static final Item bigMushroom = createItem(\"big_mushroom\", ItemGroups.ITEMS);\n" +
                "    public static final Item bigNugget = createItem(\"big_nugget\", ItemGroups.ITEMS);\n" +
                "    public static final Item bigPearl = createItem(\"big_pearl\", ItemGroups.ITEMS);\n" +
                "    public static final Item goldLeaf = createItem(\"gold_leaf\", ItemGroups.ITEMS);\n" +
                "    public static final Item smallBouquet = createItem(\"small_bouquet\", ItemGroups.ITEMS);\n" +
                "    public static final Item blueShard = createItem(\"blue_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item cometShard = createItem(\"comet_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item greenShard = createItem(\"green_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item nugget = createItem(\"nugget\", ItemGroups.ITEMS);\n" +
                "    public static final Item pearl = createItem(\"pearl\", ItemGroups.ITEMS);\n" +
                "    public static final Item pearlString = createItem(\"pearl_string\", ItemGroups.ITEMS);\n" +
                "    public static final Item redShard = createItem(\"red_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item starPiece = createItem(\"star_piece\", ItemGroups.ITEMS);\n" +
                "    public static final Item tinyMushroom = createItem(\"tiny_mushroom\", ItemGroups.ITEMS);\n" +
                "    public static final Item yellowShard = createItem(\"yellow_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item iceStone = createItem(\"ice_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item iceStoneShard = createItem(\"ice_stone_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item pinkNectar = createItem(\"pink_nectar\", ItemGroups.ITEMS);\n" +
                "    public static final Item purpleNectar = createItem(\"purple_nectar\", ItemGroups.ITEMS);\n" +
                "    public static final Item redNectar = createItem(\"red_nectar\", ItemGroups.ITEMS);\n" +
                "    public static final Item yellowNectar = createItem(\"yellow_nectar\", ItemGroups.ITEMS);\n" +
                "    public static final Item ppUp = createItem(\"pp_up\", ItemGroups.ITEMS);\n" +
                "    public static final Item maxPp = createItem(\"max_pp\", ItemGroups.ITEMS);\n" +
                "    public static final Item redBike = createItem(\"red_bike\", ItemGroups.ITEMS);\n" +
                "    public static final Item orangeBike = createItem(\"orange_bike\", ItemGroups.ITEMS);\n" +
                "    public static final Item yellowBike = createItem(\"yellow_bike\", ItemGroups.ITEMS);\n" +
                "    public static final Item greenBike = createItem(\"green_bike\", ItemGroups.ITEMS);\n" +
                "    public static final Item blueBike = createItem(\"blue_bike\", ItemGroups.ITEMS);\n" +
                "    public static final Item purpleBike = createItem(\"purple_bike\", ItemGroups.ITEMS);\n" +
                "    public static final Item sunFlute = createItem(\"sun_flute\", ItemGroups.ITEMS);\n" +
                "    public static final Item moonFlute = createItem(\"moon_flute\", ItemGroups.ITEMS);\n" +
                "    public static final Item rustyFragment = createItem(\"rusty_fragment\", ItemGroups.ITEMS);\n" +
                "    public static final Item rustySword = createItem(\"rusty_sword\", ItemGroups.ITEMS);\n" +
                "    public static final Item rustyShield = createItem(\"rusty_shield\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintSerious = createItem(\"mint_serious\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintLonely = createItem(\"mint_lonely\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintBrave = createItem(\"mint_brave\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintAdamant = createItem(\"mint_adamant\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintNaughty = createItem(\"mint_naughty\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintBold = createItem(\"mint_bold\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintRelaxed = createItem(\"mint_relaxed\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintImpish = createItem(\"mint_impish\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintGentle = createItem(\"mint_gentle\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintLax = createItem(\"mint_lax\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintTimid = createItem(\"mint_timid\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintHasty = createItem(\"mint_hasty\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintJolly = createItem(\"mint_jolly\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintNaive = createItem(\"mint_naive\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintModest = createItem(\"mint_modest\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintMild = createItem(\"mint_mild\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintQuiet = createItem(\"mint_quiet\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintRash = createItem(\"mint_rash\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintCalm = createItem(\"mint_calm\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintSassy = createItem(\"mint_sassy\", ItemGroups.ITEMS);\n" +
                "    public static final Item mintCareful = createItem(\"mint_careful\", ItemGroups.ITEMS);\n" +
                "    public static final Item expCandyXS = createItem(\"exp_candy_x_s\", ItemGroups.ITEMS);\n" +
                "    public static final Item expCandyS = createItem(\"exp_candy_s\", ItemGroups.ITEMS);\n" +
                "    public static final Item expCandyM = createItem(\"exp_candy_m\", ItemGroups.ITEMS);\n" +
                "    public static final Item expCandyL = createItem(\"exp_candy_l\", ItemGroups.ITEMS);\n" +
                "    public static final Item expCandyXL = createItem(\"exp_candy_x_l\", ItemGroups.ITEMS);\n" +
                "    public static final Item zygardeCube = createItem(\"zygarde_cube\", ItemGroups.ITEMS);\n" +
                "    public static final Item meltanBox = createItem(\"meltan_box\", ItemGroups.ITEMS);\n" +
                "    public static final Item lureModule = createItem(\"lure_module\", ItemGroups.ITEMS);\n" +
                "    public static final Item timeGlass = createItem(\"time_glass\", ItemGroups.ITEMS);\n" +
                "    public static final Item rotomCatalog = createItem(\"rotom_catalog\", ItemGroups.ITEMS);\n" +
                "    public static final Item jewelOfLife = createItem(\"jewel_of_life\", ItemGroups.ITEMS);\n" +
                "    public static final Item basicSweetPokePuff = createItem(\"basic_sweet_poke_puff\", ItemGroups.ITEMS);\n" +
                "    public static final Item basicMintPokePuff = createItem(\"basic_mint_poke_puff\", ItemGroups.ITEMS);\n" +
                "    public static final Item basicCitrusPokePuff = createItem(\"basic_citrus_poke_puff\", ItemGroups.ITEMS);\n" +
                "    public static final Item basicMochaPokePuff = createItem(\"basic_mocha_poke_puff\", ItemGroups.ITEMS);\n" +
                "    public static final Item basicSpicePokePuff = createItem(\"basic_spice_poke_puff\", ItemGroups.ITEMS);\n" +
                "    public static final Item roomService = createItem(\"room_service\", ItemGroups.ITEMS);\n" +
                "    public static final Item pokedex = createItem(\"pokedex\", ItemGroups.ITEMS);\n" +
                "    public static final Item hoopaRing = createItem(\"hoopa_ring\", ItemGroups.ITEMS);\n" +
                "    public static final Item zoneWand = createItem(\"zone_wand\", ItemGroups.ITEMS);\n" +
                "    public static final Item lavaCrystal = createItem(\"lava_crystal\", ItemGroups.ITEMS);\n" +
                "    public static final Item magmaCrystal = createItem(\"magma_crystal\", ItemGroups.ITEMS);\n" +
                "    public static final Item relicSongRecord = createItem(\"relic_song_record\", ItemGroups.ITEMS);\n" +
                "    public static final Item shatteredRelicSong1 = createItem(\"shattered_relic_song1\", ItemGroups.ITEMS);\n" +
                "    public static final Item shatteredRelicSong2 = createItem(\"shattered_relic_song2\", ItemGroups.ITEMS);\n" +
                "    public static final Item shatteredRelicSong3 = createItem(\"shattered_relic_song3\", ItemGroups.ITEMS);\n" +
                "    public static final Item shatteredRelicSong4 = createItem(\"shattered_relic_song4\", ItemGroups.ITEMS);\n" +
                "    public static final Item rockPeakKey = createItem(\"rock_peak_key\", ItemGroups.ITEMS);\n" +
                "    public static final Item icebergKey = createItem(\"iceberg_key\", ItemGroups.ITEMS);\n" +
                "    public static final Item ironKey = createItem(\"iron_key\", ItemGroups.ITEMS);\n" +
                "    public static final Item dragoKey = createItem(\"drago_key\", ItemGroups.ITEMS);\n" +
                "    public static final Item elekiKey = createItem(\"eleki_key\", ItemGroups.ITEMS);\n" +
                "    public static final Item shatteredIceKey1 = createItem(\"shattered_ice_key1\", ItemGroups.ITEMS);\n" +
                "    public static final Item shatteredIceKey2 = createItem(\"shattered_ice_key2\", ItemGroups.ITEMS);\n" +
                "    public static final Item shatteredIceKey3 = createItem(\"shattered_ice_key3\", ItemGroups.ITEMS);\n" +
                "    public static final Item shatteredIceKey4 = createItem(\"shattered_ice_key4\", ItemGroups.ITEMS);\n" +
                "    public static final Item crumbledRockKey1 = createItem(\"crumbled_rock_key1\", ItemGroups.ITEMS);\n" +
                "    public static final Item crumbledRockKey2 = createItem(\"crumbled_rock_key2\", ItemGroups.ITEMS);\n" +
                "    public static final Item crumbledRockKey3 = createItem(\"crumbled_rock_key3\", ItemGroups.ITEMS);\n" +
                "    public static final Item crumbledRockKey4 = createItem(\"crumbled_rock_key4\", ItemGroups.ITEMS);\n" +
                "    public static final Item rustyIronKey1 = createItem(\"rusty_iron_key1\", ItemGroups.ITEMS);\n" +
                "    public static final Item rustyIronKey2 = createItem(\"rusty_iron_key2\", ItemGroups.ITEMS);\n" +
                "    public static final Item rustyIronKey3 = createItem(\"rusty_iron_key3\", ItemGroups.ITEMS);\n" +
                "    public static final Item rustyIronKey4 = createItem(\"rusty_iron_key4\", ItemGroups.ITEMS);\n" +
                "    public static final Item fragmentedDragoKey1 = createItem(\"fragmented_drago_key1\", ItemGroups.ITEMS);\n" +
                "    public static final Item fragmentedDragoKey2 = createItem(\"fragmented_drago_key2\", ItemGroups.ITEMS);\n" +
                "    public static final Item fragmentedDragoKey3 = createItem(\"fragmented_drago_key3\", ItemGroups.ITEMS);\n" +
                "    public static final Item fragmentedDragoKey4 = createItem(\"fragmented_drago_key4\", ItemGroups.ITEMS);\n" +
                "    public static final Item dischargedElekiKey1 = createItem(\"discharged_eleki_key1\", ItemGroups.ITEMS);\n" +
                "    public static final Item dischargedElekiKey2 = createItem(\"discharged_eleki_key2\", ItemGroups.ITEMS);\n" +
                "    public static final Item dischargedElekiKey3 = createItem(\"discharged_eleki_key3\", ItemGroups.ITEMS);\n" +
                "    public static final Item dischargedElekiKey4 = createItem(\"discharged_eleki_key4\", ItemGroups.ITEMS);\n" +
                "    public static final Item scrollPage = createItem(\"scroll_page\", ItemGroups.ITEMS);\n" +
                "    public static final Item secretArmorScroll = createItem(\"secret_armor_scroll\", ItemGroups.ITEMS);\n" +
                "    public static final Item lightStone = createItem(\"light_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item dragonStone = createItem(\"dragon_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item darkStone = createItem(\"dark_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item rainbowWing = createItem(\"rainbow_wing\", ItemGroups.ITEMS);\n" +
                "    public static final Item darkSoul = createItem(\"dark_soul\", ItemGroups.ITEMS);\n" +
                "    public static final Item dragonSoul = createItem(\"dragon_soul\", ItemGroups.ITEMS);\n" +
                "    public static final Item sparklingStone = createItem(\"sparkling_stone\", ItemGroups.ITEMS);\n" +
                "    public static final Item sparklingShard = createItem(\"sparkling_shard\", ItemGroups.ITEMS);\n" +
                "    public static final Item melodyFlute = createItem(\"melody_flute\", ItemGroups.ITEMS);\n" +
                "    public static final Item curry = createItem(\"curry\", ItemGroups.ITEMS);\n" +
                "    public static final Item lavaCookie = createItem(\"lava_cookie\", ItemGroups.ITEMS);\n" +
                "    public static final Item rageCandyBar = createItem(\"rage_candy_bar\", ItemGroups.ITEMS);\n" +
                "    public static final Item bigMalasada = createItem(\"big_malasada\", ItemGroups.ITEMS);\n" +
                "    public static final Item oldGateau = createItem(\"old_gateau\", ItemGroups.ITEMS);\n" +
                "    public static final Item casteliacone = createItem(\"casteliacone\", ItemGroups.ITEMS);\n" +
                "    public static final Item lumioseGalette = createItem(\"lumiose_galette\", ItemGroups.ITEMS);\n" +
                "    public static final Item shalourSable = createItem(\"shalour_sable\", ItemGroups.ITEMS);\n" +
                "    public static final Item bottleCap = createItem(\"bottle_cap\", ItemGroups.ITEMS);\n" +
                "    public static final Item goldBottleCap = createItem(\"gold_bottle_cap\", ItemGroups.ITEMS);\n" +
                "    public static final Item relicGold = createItem(\"relic_gold\", ItemGroups.ITEMS);\n" +
                "    public static final Item relicCopper = createItem(\"relic_copper\", ItemGroups.ITEMS);\n" +
                "    public static final Item relicBand = createItem(\"relic_band\", ItemGroups.ITEMS);\n" +
                "    public static final Item relicSilver = createItem(\"relic_silver\", ItemGroups.ITEMS);\n" +
                "    public static final Item relicStatue = createItem(\"relic_statue\", ItemGroups.ITEMS);\n" +
                "    public static final Item relicVase = createItem(\"relic_vase\", ItemGroups.ITEMS);\n" +
                "    public static final Item relicCrown = createItem(\"relic_crown\", ItemGroups.ITEMS);\n" +
                "    public static final Item heartScale = createItem(\"heart_scale\", ItemGroups.ITEMS);";

        String[] split = ugh.split("\n");
        String outputString = "";

        for (String s : split) {
            String[] s1 = s.split(" ");
            String bru = String.join("_", s1[8].split("(?=[A-Z])"));


            outputString += "public static final Item " + bru.toUpperCase(Locale.ROOT) + " = createItem(\"" + s1[10].replace("createItem(\"", "").replace("\",", "") + "\", ItemGroups.ITEMS);\n";
        }

        System.out.println(outputString);
    }

    @Override
    public void onInitialize() {
        PixelmonAssetProvider.ASSET_PROVIDERS.add(new PixelmonGenerationsCompatibility()); // TODO: add a way to add more of these.
        Commands.initialize();
        PixelmonDataTrackers.initialize();
        RRPCallback.AFTER_VANILLA.register(resources -> resources.add(RESOURCE_PACK));
    }

    /**
     * Checks if at least one asset provider is present and usable. Try not to call this method too much as it has to do a few I/O operations.
     */
    public static boolean isAssetProviderPresent() {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        try {
            Files.walk(PixelmonAssetProvider.JAR_PATH, 1).forEach(path -> {
                for (PixelmonAssetProvider assetProvider : PixelmonAssetProvider.ASSET_PROVIDERS) {
                    if (assetProvider.isCompatibleJar(path.getFileName().toString())) {
                        isPresent.set(true);
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("An IOException occurred while looking for Asset Providers.", e);
        }
        return isPresent.get();
    }

    public static Identifier id(String path) {
        return new Identifier("pixelmon", path);
    }
}
