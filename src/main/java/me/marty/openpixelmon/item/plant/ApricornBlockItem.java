package me.marty.openpixelmon.item.plant;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ApricornBlockItem extends BlockItem {

    public ApricornBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public String getTranslationKey() {
        return super.getOrCreateTranslationKey();
    }
}
