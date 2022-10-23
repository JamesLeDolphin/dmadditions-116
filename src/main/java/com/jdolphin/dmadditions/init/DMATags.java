package com.jdolphin.dmadditions.init;

import net.minecraft.block.Block;
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

}
