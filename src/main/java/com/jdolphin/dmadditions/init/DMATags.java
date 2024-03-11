package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class DMATags {
	public static class Blocks {
		public static final Tags.IOptionalNamedTag<Block> ROUNDEL_CONTAINERS = createTag("roundel_containers");

		private static Tags.IOptionalNamedTag<Block> createTag(String name, Boolean itemTag) {
			if (itemTag) {
				ItemTags.createOptional(Helper.createDMRL(name));
			}
			return BlockTags.createOptional(Helper.createDMRL(name));
		}
		private static Tags.IOptionalNamedTag<Block> createTag(String name) {
			return createTag(name, true);
		}
	}

	public static class Items {
		public static final Tags.IOptionalNamedTag<Item> DATA_MODULES = createTag("data_modules");
		public static final Tags.IOptionalNamedTag<Item> TARDIS_KEYS = createTag("tardis_keys");
		private static Tags.IOptionalNamedTag<Item> createTag(String name) {
			return ItemTags.createOptional(Helper.createDMRL(name));
		}
	}
	public static class Fluids {
		public static final Tags.IOptionalNamedTag<Fluid> METALERT = createTag("metalert");
		public static final Tags.IOptionalNamedTag<Fluid> DALEKANIUM = createTag("dalekanium");
		public static final Tags.IOptionalNamedTag<Fluid> SILICON = createTag("silicon");
		public static final Tags.IOptionalNamedTag<Fluid> STAINLESS_STEEL = createTag("stainless_steel");

		private static Tags.IOptionalNamedTag<Fluid> createTag(String name) {
			return FluidTags.createOptional(Helper.createAdditionsRL(name));
		}
	}

}
