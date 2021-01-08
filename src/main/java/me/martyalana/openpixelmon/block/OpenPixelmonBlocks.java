package me.martyalana.openpixelmon.block;

import me.martyalana.openpixelmon.OpenPixelmon;
import me.martyalana.openpixelmon.block.misc.AcornBush;
import me.martyalana.openpixelmon.item.OpenPixelmonItems;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OpenPixelmonBlocks {

	public static final Block RED_ACORN_BUSH = generateAcornBush(new AcornBush(OpenPixelmonItems.RED_ACORN), "red_acorn_bush");
	public static final Block WHITE_ACORN_BUSH = generateAcornBush(new AcornBush(OpenPixelmonItems.RED_ACORN), "white_acorn_bush");
	public static final Block BLUE_ACORN_BUSH = generateAcornBush(new AcornBush(OpenPixelmonItems.RED_ACORN), "blue_acorn_bush");
	public static final Block YELLOW_ACORN_BUSH = generateAcornBush(new AcornBush(OpenPixelmonItems.RED_ACORN), "yellow_acorn_bush");
	public static final Block GREEN_ACORN_BUSH = generateAcornBush(new AcornBush(OpenPixelmonItems.RED_ACORN), "green_acorn_bush");
	public static final Block PINK_ACORN_BUSH = generateAcornBush(new AcornBush(OpenPixelmonItems.RED_ACORN), "pink_acorn_bush");
	public static final Block BLACK_ACORN_BUSH = generateAcornBush(new AcornBush(OpenPixelmonItems.RED_ACORN), "black_acorn_bush");

	public static void initialize(){
	}

	private static Block generateAcornBush(AcornBush block, String id) {
		Registry.register(Registry.BLOCK, OpenPixelmon.id(id), block);
		Identifier identifier = OpenPixelmon.id(id);
		Identifier grownIdentifier = OpenPixelmon.id("block/grown_" + id);
		Identifier growingIdentifier = OpenPixelmon.id("block/growing_" + id);
		OpenPixelmon.RESOURCE_PACK.addBlockState(new JState().add(new JVariant()
				.put("grown=1", new JBlockModel(growingIdentifier))
				.put("grown=2", new JBlockModel(growingIdentifier))
				.put("grown=3", new JBlockModel(grownIdentifier))), identifier);

		OpenPixelmon.RESOURCE_PACK.addModel(new JModel()
				.parent("open_pixelmon:block/grown_acorn_bush")
				.textures(new JTextures().var("0", grownIdentifier.toString())), grownIdentifier);
		OpenPixelmon.RESOURCE_PACK.addModel(new JModel()
				.parent("open_pixelmon:block/growing_acorn_bush")
				.textures(new JTextures().var("0", growingIdentifier.toString())), growingIdentifier);
		return block;
	}
}
