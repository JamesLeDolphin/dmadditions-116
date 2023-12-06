package com.jdolphin.dmadditions.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import slimeknights.mantle.registration.ItemProperties;

public class ChristmasCrackerBlockItem extends BlockItem {

	public ChristmasCrackerBlockItem(Block type, Properties properties) {
		super(type, properties);
	}

	public ChristmasCrackerBlockItem(RegistryObject<Block> type){
		super(type.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS));
	}
}
