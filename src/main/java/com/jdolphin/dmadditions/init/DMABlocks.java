package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.block.CarvedDalekPumpkinBlock;
import com.jdolphin.dmadditions.block.DalekPumpkinBlock;
import com.jdolphin.dmadditions.block.christmas.*;
import com.jdolphin.dmadditions.block.tardis.*;
import com.jdolphin.dmadditions.tileentity.DoorPanelTileEntity;
import com.jdolphin.dmadditions.tileentity.ReddashStatueTileEntity;
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

	protected static RegistryObject<Block> registerAdventBlock(int day, Supplier<Block> supplier, String name) {
		if (!AdventUnlock.unlockAt(day))
			return null;

		return registerBlock(supplier, name);
	}

	protected static RegistryObject<Block> registerAdventBlock(int day, Supplier<Block> supplier, String name, ItemGroup tab) {
		if (!AdventUnlock.unlockAt(day))
			return null;

		return registerBlock(supplier, name, tab);
	}


	//public static RegistryObject<Block> SANTA_BAUBLE_BLOCK = registerAdventBlock(7, BaubleBlock::new, "santa_bauble");
	public static RegistryObject<Block> BLUE_BAUBLE_BLOCK = registerAdventBlock(7, BaubleBlock::new, "blue_bauble");
	public static RegistryObject<Block> GOLD_BAUBLE_BLOCK = registerAdventBlock(7, BaubleBlock::new, "gold_bauble");
	public static RegistryObject<Block> GREEN_BAUBLE_BLOCK = registerAdventBlock(7, BaubleBlock::new, "green_bauble");
	public static RegistryObject<Block> RED_BAUBLE_BLOCK = registerAdventBlock(7, BaubleBlock::new, "red_bauble");

	public static RegistryObject<Block> CHRISTMAS_LIGHTS = registerAdventBlock(8, () ->
		new ChristmasLightsBlock(AbstractBlock.Properties.of(Material.DECORATION).strength(1F).sound(SoundType.STONE).noOcclusion()),
		"christmas_lights", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> CHRISTMAS_PUDDING = registerAdventBlock(5, () ->
		new ChristmasPuddingBlock(AbstractBlock.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)), "christmas_pudding", ItemGroup.TAB_FOOD);

	public static RegistryObject<Block> CHRISTMAS_PRESENT = registerAdventBlock(4,
												() -> new AndrozaniminorDimensionTpBlock(AbstractBlock.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion()
				.harvestTool(ToolType.PICKAXE)), "christmas_present", ItemGroup.TAB_DECORATIONS);

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

	public static RegistryObject<Block> TARDIS_SNOWGLOBE = registerBlock(
			() -> new SnowGlobeBlock(AbstractBlock.Properties.of(Material.GLASS).strength(0.8F, 0.8F).noOcclusion().dynamicShape().sound(SoundType.GLASS)),
			"tardis_snowglobe", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> RANDOMIZER = registerBlock(
			() -> new RandomizerBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
			"randomizer", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> CHEESE_ORE = registerBlock(() -> new OreBlock(AbstractBlock.Properties.copy(DMBlocks.ANORTHOSITE.isPresent() ? DMBlocks.ANORTHOSITE.get() : Blocks.STONE)), "cheese_ore", ItemGroup.TAB_BUILDING_BLOCKS);

		public static RegistryObject<Block> DALEK_PUMPKIN = registerBlock( () -> new DalekPumpkinBlock(AbstractBlock.Properties.copy(Blocks.CARVED_PUMPKIN)), "dalek_pumpkin", ItemGroup.TAB_BUILDING_BLOCKS);
		public static RegistryObject<Block> CARVED_DALEK_PUMPKIN = registerBlock( () -> new CarvedDalekPumpkinBlock(AbstractBlock.Properties.copy(Blocks.CARVED_PUMPKIN)), "carved_dalek_pumpkin", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> CHRISTMAS_TREE = registerBlock( () -> new ChristmasTreeBlock(AbstractBlock.Properties.of(Material.WOOD)
			.harvestTool(ToolType.AXE).noOcclusion().dynamicShape()), "christmas_tree", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> CHRISTMAS_CRACKER = registerAdventBlock (12, () -> new ChristmasCrackerBlock(AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion().dynamicShape().noCollission().sound(SoundType.WOOL)), "christmas_cracker");


	public static RegistryObject<Block> BLACK_QUARTZ_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "black_quartz_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> YELLOW_QUARTZ_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "yellow_quartz_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> WHITE_QUARTZ_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "white_quartz_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> SCORCHED_BLACK_QUARTZ_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "scorched_black_quartz_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> SCORCHED_YELLOW_QUARTZ_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "scorched_yellow_quartz_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> SCORCHED_WHITE_QUARTZ_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "scorched_white_quartz_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> DALEKANIUM_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "dalekanium_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> REFINED_DALEKANIUM_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "refined_dalekanium_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> PURE_DALEKANIUM_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "pure_dalekanium_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> METALERT_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "metalert_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> STEEL_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "steel_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> BLUE_STEEL_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "blue_steel_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> RED_STEEL_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "red_steel_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> STEEL_BEAMS_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "steel_beams_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> FILLED_STEEL_BEAMS_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "filled_steel_beams_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "rusted_steel_beams_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> FILLED_RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "filled_rusted_steel_beams_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "stainless_steel_beams_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> FILLED_STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "filled_stainless_steel_beams_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "steel_reinforced_walling_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> RUSTED_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "rusted_steel_reinforced_walling_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> STAINLESS_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER = registerContainer(Material.METAL, "stainless_steel_reinforced_walling_roundel_container", SoundType.METAL);
	public static RegistryObject<Block> TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> WHITE_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "white_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> ORANGE_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "orange_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> MAGENTA_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "magenta_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> LIGHT_BLUE_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "light_blue_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> YELLOW_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "yellow_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> LIME_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "lime_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> PINK_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "pink_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> GRAY_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "gray_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> LIGHT_GRAY_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "light_gray_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> CYAN_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "cyan_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> PURPLE_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "purple_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> BLUE_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "blue_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> BROWN_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "brown_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> GREEN_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "green_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> RED_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "red_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> BLACK_TERRACOTTA_ROUNDEL_CONTAINER = registerContainer(Material.STONE, "black_terracotta_roundel_container", SoundType.STONE);
	public static RegistryObject<Block> WHITE_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "white_plastic_roundel_container");
	public static RegistryObject<Block> CLAY_ROUNDEL_CONTAINER = registerContainer(Material.CLAY, "clay_roundel_container", SoundType.GRAVEL);
	public static RegistryObject<Block> ORANGE_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "orange_plastic_roundel_container");
	public static RegistryObject<Block> MAGENTA_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "magenta_plastic_roundel_container");
	public static RegistryObject<Block> LIGHT_BLUE_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "light_blue_plastic_roundel_container");
	public static RegistryObject<Block> YELLOW_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "yellow_plastic_roundel_container");
	public static RegistryObject<Block> LIME_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "lime_plastic_roundel_container");
	public static RegistryObject<Block> PINK_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "pink_plastic_roundel_container");
	public static RegistryObject<Block> GRAY_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "gray_plastic_roundel_container");
	public static RegistryObject<Block> LIGHT_GRAY_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "light_gray_plastic_roundel_container");
	public static RegistryObject<Block> CYAN_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "cyan_plastic_roundel_container");
	public static RegistryObject<Block> PURPLE_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "purple_plastic_roundel_container");
	public static RegistryObject<Block> BLUE_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "blue_plastic_roundel_container");
	public static RegistryObject<Block> BROWN_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "brown_plastic_roundel_container");
	public static RegistryObject<Block> GREEN_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "green_plastic_roundel_container");
	public static RegistryObject<Block> RED_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "red_plastic_roundel_container");
	public static RegistryObject<Block> BLACK_PLASTIC_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "black_plastic_roundel_container");
	public static RegistryObject<Block> WHITE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "white_plastic_bowl_roundel_container");
	public static RegistryObject<Block> ORANGE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "orange_plastic_bowl_roundel_container");
	public static RegistryObject<Block> MAGENTA_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "magenta_plastic_bowl_roundel_container");
	public static RegistryObject<Block> LIGHT_BLUE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "light_blue_plastic_bowl_roundel_container");
	public static RegistryObject<Block> YELLOW_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "yellow_plastic_bowl_roundel_container");
	public static RegistryObject<Block> LIME_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "lime_plastic_bowl_roundel_container");
	public static RegistryObject<Block> PINK_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "pink_plastic_bowl_roundel_container");
	public static RegistryObject<Block> GRAY_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "gray_plastic_bowl_roundel_container");
	public static RegistryObject<Block> LIGHT_GRAY_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "light_gray_plastic_bowl_roundel_container");
	public static RegistryObject<Block> CYAN_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "cyan_plastic_bowl_roundel_container");
	public static RegistryObject<Block> PURPLE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "purple_plastic_bowl_roundel_container");
	public static RegistryObject<Block> BLUE_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "blue_plastic_bowl_roundel_container");
	public static RegistryObject<Block> BROWN_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "brown_plastic_bowl_roundel_container");
	public static RegistryObject<Block> GREEN_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "green_plastic_bowl_roundel_container");
	public static RegistryObject<Block> RED_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "red_plastic_bowl_roundel_container");
	public static RegistryObject<Block> BLACK_PLASTIC_BOWL_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "black_plastic_bowl_roundel_container");
	public static RegistryObject<Block> WHITE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "white_plastic_shape_roundel_container");
	public static RegistryObject<Block> ORANGE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "orange_plastic_shape_roundel_container");
	public static RegistryObject<Block> MAGENTA_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "magenta_plastic_shape_roundel_container");
	public static RegistryObject<Block> LIGHT_BLUE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "light_blue_plastic_shape_roundel_container");
	public static RegistryObject<Block> YELLOW_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "yellow_plastic_shape_roundel_container");
	public static RegistryObject<Block> LIME_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "lime_plastic_shape_roundel_container");
	public static RegistryObject<Block> PINK_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "pink_plastic_shape_roundel_container");
	public static RegistryObject<Block> GRAY_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "gray_plastic_shape_roundel_container");
	public static RegistryObject<Block> LIGHT_GRAY_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "light_gray_plastic_shape_roundel_container");
	public static RegistryObject<Block> CYAN_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "cyan_plastic_shape_roundel_container");
	public static RegistryObject<Block> PURPLE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "purple_plastic_shape_roundel_container");
	public static RegistryObject<Block> BLUE_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "blue_plastic_shape_roundel_container");
	public static RegistryObject<Block> BROWN_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "brown_plastic_shape_roundel_container");
	public static RegistryObject<Block> GREEN_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "green_plastic_shape_roundel_container");
	public static RegistryObject<Block> RED_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "red_plastic_shape_roundel_container");
	public static RegistryObject<Block> BLACK_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD,"black_plastic_shape_roundel_container");

	public static <B extends Block> RegistryObject<Block> registerBlock(Supplier<B> block, String name, ItemGroup itemgroup) {
		return registerBlock(block, name, (new Item.Properties()).tab(itemgroup), true);
	}

	public static <B extends Block> RegistryObject<Block> registerContainer(Material material, String name, SoundType soundType) {
		return registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(material).strength(2.0F, 2.5F).sound(soundType).noOcclusion()),
			name, (new Item.Properties()).tab(DMATabs.DMA_ROUNDEL_CONTAINERS), true);
	}

	@SafeVarargs
	public static void registerRenderTypes(RenderType renderType, RegistryObject<Block> ...blocks){
		for(RegistryObject<Block> block : blocks ){
			if(block != null && block.isPresent()){
				registerRenderType(block.get(), renderType);
			}
		}
	}

	public static void registerRenderTypes() {
		registerRenderTypes(RenderType.cutoutMipped(),
			TARDIS_SNOWGLOBE,
			CHRISTMAS_TREE
		);

		registerRenderTypes(RenderType.cutoutMipped(),
				CHRISTMAS_CRACKER
		);

		registerRenderTypes(RenderType.cutoutMipped(),
			BLUE_BAUBLE_BLOCK,
			GOLD_BAUBLE_BLOCK,
			GREEN_BAUBLE_BLOCK,
			RED_BAUBLE_BLOCK
			//SANTA_BAUBLE_BLOCK
		);

		registerRenderTypes(RenderType.cutout(),
			STEEL_BEAMS_ROUNDEL_CONTAINER,
			RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER,
			STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER
		);

	}
	public static <B extends Block> RegistryObject<Block> registerContainer(Material material, String name) {
		return registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(material).strength(2.0F, 2.5F).sound(SoundType.WOOD)),
			name, (new Item.Properties()).tab(DMATabs.DMA_ROUNDEL_CONTAINERS), true);
	}

	public static <B extends Block> RegistryObject<Block> registerBlock(Supplier<B> block, String name) {
		return registerBlock(block, name, new Item.Properties(), false);
	}

	public static <B extends Block> RegistryObject<Block> registerBlock(Supplier<B> block, String name, Item.Properties properties, boolean needsItem) {
		RegistryObject<Block> blockObj = BLOCKS.register(name, block);
		if (needsItem) {
			DMAItems.ITEMS.register(name, () -> new BaseBlockItem(blockObj.get(), properties));
		}

		return blockObj;
	}

	public static <B extends Block> RegistryObject<Block> registerBlockAndItem(String name, Supplier<B> block, Item.Properties properties) {
		RegistryObject<Block> blockObject = BLOCKS.register(name, block);
		DMAItems.ITEMS.register(name, () -> new BlockItem(blockObject.get(), properties));
		return blockObject;
	}
}

