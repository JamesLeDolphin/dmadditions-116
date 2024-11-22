package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

@SuppressWarnings("unused")
public class DMATags {
	public static class Blocks {
		public static final Tags.IOptionalNamedTag<Block> ROUNDEL_CONTAINERS = createTag("roundel_containers");
		public static final Tags.IOptionalNamedTag<Block> GALLIFREY_REPLACEABLE = createTag("gallifrey_replaceable");


		private static Tags.IOptionalNamedTag<Block> createTag(String name) {
			return BlockTags.createOptional(Helper.createAdditionsRL(name));
		}
	}

	public static class Items {
		public static final Tags.IOptionalNamedTag<Item> DATA_MODULES = createDMTag("data_modules");
		public static final Tags.IOptionalNamedTag<Item> SPECIMEN_JAR_ACCEPTS = createTag("specimen_jar_accepts");
		public static final Tags.IOptionalNamedTag<Item> TARDIS_KEYS = createDMTag("tardis_keys");

		private static Tags.IOptionalNamedTag<Item> createTag(String name) {
			return ItemTags.createOptional(Helper.createAdditionsRL(name));
		}

		private static Tags.IOptionalNamedTag<Item> createDMTag(String name) {
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
