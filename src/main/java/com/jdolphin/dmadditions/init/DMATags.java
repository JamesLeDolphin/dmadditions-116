package com.jdolphin.dmadditions.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class DMATags {
	public static class Blocks {

		public static final Tags.IOptionalNamedTag<Block> ROUNDEL_CONTAINERS = createTag("roundel_containers");


		private static Tags.IOptionalNamedTag<Block> createTag(String name, Boolean itemTag) {
			if (itemTag) {
				ItemTags.createOptional(new ResourceLocation("dalekmod", name));
			}

			return BlockTags.createOptional(new ResourceLocation("dalekmod", name));
		}

		private static Tags.IOptionalNamedTag<Block> createTag(String name) {
			return createTag(name, true);
		}
	}

	public static class Items {
		public static final Tags.IOptionalNamedTag<Item> DATA_MODULES = createTag("data_modules");

		private static Tags.IOptionalNamedTag<Item> createTag(String name) {
			return ItemTags.createOptional(new ResourceLocation("dalekmod", name));
		}
	}

}
