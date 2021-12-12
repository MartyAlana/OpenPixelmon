package me.marty.openpixelmon.item;

import me.marty.openpixelmon.OpenPixelmon;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ItemGroups {

    public static final ItemGroup POKEBALLS = create("pokeballs", () -> new ItemStack(OpenPixelmonItems.POKE_BALL));
    public static final ItemGroup RESTORATION = create("restoration", () -> new ItemStack(OpenPixelmonItems.POTION));
    public static final ItemGroup ORGANIC = create("organic", () -> new ItemStack(OpenPixelmonItems.RED_APRICORN));
    public static final ItemGroup TMS = create("tms", () -> new ItemStack(OpenPixelmonItems.ULTRA_BALL));
    public static final ItemGroup BLOCKS = create("blocks", () -> new ItemStack(OpenPixelmonItems.MASTER_BALL));
    public static final ItemGroup ITEMS = create("items", () -> new ItemStack(OpenPixelmonItems.EXP_ALL));
    public static final ItemGroup BADGES = create("badges", () -> new ItemStack(OpenPixelmonItems.rainBadge));
    public static final ItemGroup DECORATION = create("decoration", () -> new ItemStack(OpenPixelmonItems.CHERISH_BALL));

    private static ItemGroup create(String name, Supplier<ItemStack> stackSupplier) {
        return FabricItemGroupBuilder.build(
                OpenPixelmon.id(name),
                stackSupplier
        );
    }
}
