package me.martyalana.openpixelmon.block;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.block.misc.AcornBush;
import me.martyalana.openpixelmon.item.OpenPixelmonItems;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class OpenPixelmonBlocks {

	public static final Block RED_ACORN_BUSH = new AcornBush(OpenPixelmonItems.RED_ACORN);

	public static void initialize(){
		Registry.register(Registry.BLOCK, OpenPixelmon.id("red_acorn_bush"), RED_ACORN_BUSH);
	}
}
