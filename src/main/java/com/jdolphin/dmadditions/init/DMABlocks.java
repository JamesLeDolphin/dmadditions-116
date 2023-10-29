package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.RegistryHandler;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.block.*;
import com.jdolphin.dmadditions.tileentity.DoorPanelTileEntity;
import com.jdolphin.dmadditions.tileentity.ReddashStatueTileEntity;
import com.swdteam.common.block.RoundelBlock;
import com.swdteam.common.block.StatueBlock;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMTabs;
import com.swdteam.common.item.BaseBlockItem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.function.Supplier;

import static com.swdteam.common.init.DMBlocks.registerRenderType;

public class DMABlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DmAdditions.MODID);

	public static final RegistryObject<Block> CORAL_FLIGHT_LEVER = registerBlock(
		() -> new CoralHandbrake(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE)
			.instabreak().noOcclusion().sound(SoundType.STONE)),
		"coral_flight_lever", DMTabs.DM_TARDIS);
	public static final RegistryObject<Block> COPPER_FLIGHT_LEVER = registerBlock(
		() -> new CopperHandbrake(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE)
			.instabreak().noOcclusion().sound(SoundType.STONE)),
		"copper_flight_lever", DMTabs.DM_TARDIS);

	protected static RegistryObject<Block> registerAdventBlock(int day, Supplier<Block> supplier, String name, ItemGroup tab) {
		if (!AdventUnlock.unlockAt(day))
			return null;

		return registerBlock(supplier, name, tab);
	}

	public static RegistryObject<Block> DOOR_PANEL = registerBlockAndItem("door_panel",
			() -> new DoorPanelBlock(DoorPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD)),
			new Item.Properties().tab(DMTabs.DM_TARDIS));

	public static RegistryObject<Block> BLUE_CANDY_CANE_BLOCK = registerBlock(() -> {
			return new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
			.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE));
		}, "blue_candy_cane_block", DMTabs.DM_TARDIS);
	public static RegistryObject<Block> GREEN_CANDY_CANE_BLOCK = registerBlock(() -> {
			return new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
				.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE));
		}, "green_candy_cane_block", DMTabs.DM_TARDIS);
	public static RegistryObject<Block> ORANGE_CANDY_CANE_BLOCK = registerBlock(() -> {
			return new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
				.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE));
		}, "orange_candy_cane_block", DMTabs.DM_TARDIS);
	public static RegistryObject<Block> PINK_CANDY_CANE_BLOCK = registerBlock(() -> {
			return new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
				.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE));
		}, "pink_candy_cane_block", DMTabs.DM_TARDIS);
	public static RegistryObject<Block> PURPLE_CANDY_CANE_BLOCK = registerBlock(() -> {
			return new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
				.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE));
		}, "purple_candy_cane_block", DMTabs.DM_TARDIS);
	public static RegistryObject<Block> RED_CANDY_CANE_BLOCK = registerBlock(() -> {
			return new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
				.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE));
		}, "red_candy_cane_block", DMTabs.DM_TARDIS);
	public static RegistryObject<Block>YELLOW_CANDY_CANE_BLOCK = registerBlock(() -> {
			return new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
				.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE));
		}, "yellow_candy_cane_block", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> REDDASH_STATUE = registerBlock(() -> new StatueBlock(ReddashStatueTileEntity::new, AbstractBlock.Properties.of(Material.STONE)
			.requiresCorrectToolForDrops().noOcclusion().strength(1.5F, 6.0F).sound(SoundType.STONE)), "reddash_statue", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> WREATH = registerBlock(
			() -> new WreathBlock(AbstractBlock.Properties.of(Material.LEAVES).strength(0.8F, 0.8F).sound(SoundType.GRASS).noOcclusion().noCollission().instabreak()),
			"wreath", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block>TARDIS_SNOWGLOBE = registerBlock(
			() -> new SnowGlobeBlock(AbstractBlock.Properties.of(Material.GLASS).strength(0.8F, 0.8F).noOcclusion().dynamicShape().sound(SoundType.GLASS)),
			"tardis_snowglobe", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block>RANDOMIZER = registerBlock(
			() -> new RandomizerBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
			"randomizer", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> CHEESE_ORE = registerBlock(() -> new OreBlock(AbstractBlock.Properties.copy(DMBlocks.ANORTHOSITE.isPresent() ? DMBlocks.ANORTHOSITE.get() : Blocks.STONE)), "cheese_ore");

		public static RegistryObject<Block> DALEK_PUMPKIN = registerBlock( () -> new DalekPumpkinBlock(AbstractBlock.Properties.copy(Blocks.CARVED_PUMPKIN)), "dalek_pumpkin", ItemGroup.TAB_BUILDING_BLOCKS);
		public static RegistryObject<Block> CARVED_DALEK_PUMPKIN = registerBlock( () -> new CarvedDalekPumpkinBlock(AbstractBlock.Properties.copy(Blocks.CARVED_PUMPKIN)), "carved_dalek_pumpkin", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> CHRISTMAS_TREE = registerBlock( () -> new ChristmasTreeBlock(AbstractBlock.Properties.of(Material.WOOD)
			.harvestTool(ToolType.AXE).noOcclusion().dynamicShape()), "christmas_tree", ItemGroup.TAB_DECORATIONS);


		public static RegistryObject<Block> BLACK_QUARTZ_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(0.8F, 0.8F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "black_quartz_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> YELLOW_QUARTZ_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(0.8F, 0.8F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "yellow_quartz_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> WHITE_QUARTZ_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(0.8F, 0.8F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "white_quartz_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> SCORCHED_BLACK_QUARTZ_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.0F, 0.4F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "scorched_black_quartz_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> SCORCHED_YELLOW_QUARTZ_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.0F, 0.4F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "scorched_yellow_quartz_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> SCORCHED_WHITE_QUARTZ_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.0F, 0.4F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "scorched_white_quartz_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> DALEKANIUM_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(3.0F, 3.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "dalekanium_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> REFINED_DALEKANIUM_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 5.5F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "refined_dalekanium_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PURE_DALEKANIUM_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(12.0F, 10.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "pure_dalekanium_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> METALERT_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(40.0F, 50.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "metalert_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> STEEL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "steel_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLUE_STEEL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "blue_steel_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> RED_STEEL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "red_steel_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> STEEL_BEAMS_ROUNDEL_CONTAINER = registerBlock(() -> new RustableRoundelContainerBlock.WaterLoggable(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1).noOcclusion()), "steel_beams_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> FILLED_STEEL_BEAMS_ROUNDEL_CONTAINER = registerBlock(() -> new RustableRoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "filled_steel_beams_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock.WaterLoggable(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1).noOcclusion()), "rusted_steel_beams_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> FILLED_RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "filled_rusted_steel_beams_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1).noOcclusion()), "stainless_steel_beams_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> FILLED_STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL).strength(6.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "filled_stainless_steel_beams_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(8.0F, 7.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "steel_reinforced_walling_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> RUSTED_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_BROWN).strength(6.5F, 6.5F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "rusted_steel_reinforced_walling_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> STAINLESS_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY).strength(8.5F, 8.5F).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1)), "stainless_steel_reinforced_walling_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> WHITE_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "white_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> ORANGE_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "orange_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> MAGENTA_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "magenta_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "light_blue_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> YELLOW_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "yellow_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIME_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "lime_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PINK_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "pink_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> GRAY_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "gray_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "light_gray_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> CYAN_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "cyan_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PURPLE_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "purple_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLUE_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "blue_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BROWN_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "brown_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> GREEN_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "green_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> RED_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "red_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLACK_TERRACOTTA_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.STONE).strength(1.25F, 4.2F).sound(SoundType.STONE).requiresCorrectToolForDrops()), "black_terracotta_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> WHITE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD).requiresCorrectToolForDrops()), "white_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> CLAY_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.CLAY).strength(0.6F, 0.6F).sound(SoundType.GRAVEL)), "clay_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> ORANGE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "orange_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> MAGENTA_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "magenta_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIGHT_BLUE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "light_blue_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> YELLOW_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "yellow_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIME_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "lime_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PINK_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "pink_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> GRAY_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "gray_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIGHT_GRAY_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "light_gray_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> CYAN_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "cyan_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PURPLE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "purple_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLUE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "blue_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BROWN_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "brown_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> GREEN_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "green_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> RED_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "red_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLACK_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "black_plastic_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> WHITE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "white_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> ORANGE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "orange_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> MAGENTA_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "magenta_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIGHT_BLUE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "light_blue_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> YELLOW_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "yellow_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIME_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "lime_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PINK_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "pink_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> GRAY_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "gray_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIGHT_GRAY_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "light_gray_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> CYAN_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "cyan_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PURPLE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "purple_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLUE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "blue_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BROWN_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "brown_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> GREEN_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "green_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> RED_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "red_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLACK_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "black_plastic_bowl_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> WHITE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "white_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> ORANGE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "orange_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> MAGENTA_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "magenta_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIGHT_BLUE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "light_blue_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> YELLOW_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "yellow_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIME_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "lime_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PINK_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "pink_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> GRAY_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "gray_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> LIGHT_GRAY_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "light_gray_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> CYAN_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "cyan_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> PURPLE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "purple_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLUE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "blue_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BROWN_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "brown_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> GREEN_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "green_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> RED_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "red_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);
		public static RegistryObject<Block> BLACK_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "black_plastic_shape_roundel_container", DMATabs.DMA_ROUNDEL_CONTAINERS);

	public static <B extends Block> RegistryObject<Block> registerBlock(Supplier<B> block, String name, ItemGroup itemgroup) {
		return registerBlock(block, name, (new Item.Properties()).tab(itemgroup), true);
	}

	public static <B extends Block> RegistryObject<Block> registerBlock(Supplier<B> block, String name) {
		return registerBlock(block, name, new Item.Properties(), true);
	}

	public static <B extends Block> RegistryObject<Block> registerBlock(Supplier<B> block, String name, Item.Properties properties, boolean needsItem) {
		RegistryObject<Block> blockObj = BLOCKS.register(name, block);
		if (needsItem) {
			RegistryHandler.DMARegistries.ITEMS.register(name, () -> new BaseBlockItem(blockObj.get(), properties));
		}

		return blockObj;
	}

	public static <B extends Block> RegistryObject<Block> registerBlockAndItem(String name, Supplier<B> block, Item.Properties properties) {
		RegistryObject<Block> blockObject = BLOCKS.register(name, block);
		RegistryHandler.DMARegistries.ITEMS.register(name, () -> new BlockItem(blockObject.get(), properties));

		return blockObject;
	}
}