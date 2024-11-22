package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.block.*;
import com.jdolphin.dmadditions.block.CarvedDalekPumpkinBlock;
import com.jdolphin.dmadditions.block.DalekPumpkinBlock;
import com.jdolphin.dmadditions.block.EngineBlock;
import com.jdolphin.dmadditions.block.SpecimenJarBlock;
import com.jdolphin.dmadditions.block.christmas.*;
import com.jdolphin.dmadditions.block.tardis.*;
import com.jdolphin.dmadditions.tileentity.ConsoleTileEntity;
import com.jdolphin.dmadditions.tileentity.DoorPanelTileEntity;
import com.jdolphin.dmadditions.tileentity.ReddashStatueTileEntity;
import com.jdolphin.dmadditions.world.tree.GallifreyOakTree;
import com.swdteam.common.block.LogBlock;
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
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;
import java.util.function.Supplier;

import static com.swdteam.common.init.DMBlocks.registerRenderType;

@SuppressWarnings("unused")
public class DMABlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DMAdditions.MODID);

	public static final RegistryObject<Block> CORAL_FLIGHT_LEVER = registerBlock(
		() -> new CoralHandbrake(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE)
			.instabreak().noOcclusion().sound(SoundType.STONE)),
		"coral_flight_lever", DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> COPPER_FLIGHT_LEVER = registerBlock(
		() -> new CopperHandbrake(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE)
			.instabreak().noOcclusion().sound(SoundType.STONE)),
		"copper_flight_lever", DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> STONE_SONIC_CRYSTAL_ORE = registerBlockAndItem("stone_sonic_crystal_ore",
		() -> new BetterOreBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.STONE)
			.harvestTool(ToolType.PICKAXE).harvestLevel(2)
			.sound(SoundType.STONE)
			.requiresCorrectToolForDrops()
			.strength(6.0F, 6.0F), 1, 5),
		new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS));

	protected static RegistryObject<Block> registerAdventBlock(int day, Supplier<Block> supplier, String name) {
		if (!TimedUnlock.advent(day))
			return null;

		return registerBlock(supplier, name);
	}

	protected static RegistryObject<Block> registerAdventBlock(int day, Supplier<Block> supplier, String name, ItemGroup tab) {
		if (!TimedUnlock.advent(day))
			return null;

		return registerBlock(supplier, name, tab);
	}

	public static RegistryObject<Block> BLUE_BAUBLE_BLOCK = registerBlock(BaubleBlock::new, "blue_bauble");
	public static RegistryObject<Block> GOLD_BAUBLE_BLOCK = registerBlock(BaubleBlock::new, "gold_bauble");
	public static RegistryObject<Block> GREEN_BAUBLE_BLOCK = registerBlock(BaubleBlock::new, "green_bauble");
	public static RegistryObject<Block> RED_BAUBLE_BLOCK = registerBlock(BaubleBlock::new, "red_bauble");

	public static RegistryObject<Block> GALLIFREY_OAK_LOG = registerAdventBlock(6, () ->
			new LogBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD), DMABlocks.STRIPPED_GALLIFREY_OAK_LOG),
		"gallifrey_oak_log", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_WOOD = registerAdventBlock(6, () ->
			new LogBlock(AbstractBlock.Properties.copy(GALLIFREY_OAK_LOG.get()), DMABlocks.STRIPPED_GALLIFREY_OAK_LOG),
		"gallifrey_oak_wood", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_SAPLING = registerAdventBlock(6, () ->
			new SaplingBlock(new GallifreyOakTree(), AbstractBlock.Properties.copy(Blocks.OAK_SAPLING)),
		"gallifrey_oak_sapling", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> STRIPPED_GALLIFREY_OAK_WOOD = registerAdventBlock(6, () ->
			new RotatedPillarBlock(AbstractBlock.Properties.copy(GALLIFREY_OAK_LOG.get())),
		"stripped_gallifrey_oak_wood", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SAND = registerAdventBlock(6, () ->
			new SandBlock(Color.PINK.getRGB(), AbstractBlock.Properties.of(Material.SAND).harvestTool(ToolType.SHOVEL).strength(0.5f).sound(SoundType.SAND)),
		"gallifrey_sand", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE =  registerAdventBlock(6, () ->
			new Block(AbstractBlock.Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)),
		"gallifrey_sandstone", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SMOOTH_SANDSTONE =  registerAdventBlock(6, () ->
			new Block(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_smooth_sandstone", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_CUT_SANDSTONE =  registerAdventBlock(6, () ->
			new Block(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_cut_sandstone", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_CUT_SANDSTONE_SLAB =  registerAdventBlock(6, () ->
			new SlabBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_cut_sandstone_slab", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE_SLAB =  registerAdventBlock(6, () ->
			new SlabBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_sandstone_slab", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE_WALL =  registerAdventBlock(6, () ->
			new WallBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_sandstone_wall", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE_STAIRS =  registerAdventBlock(6, () ->
			new StairsBlock(() -> GALLIFREY_SANDSTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_sandstone_stairs", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SMOOTH_SANDSTONE_SLAB =  registerAdventBlock(6, () ->
			new SlabBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_smooth_sandstone_slab", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SMOOTH_SANDSTONE_STAIRS =  registerAdventBlock(6, () ->
			new StairsBlock(() -> GALLIFREY_SMOOTH_SANDSTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(GALLIFREY_SMOOTH_SANDSTONE.get())),
		"gallifrey_smooth_sandstone_stairs", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_CHISELED_SANDSTONE =  registerAdventBlock(6, () ->
			new Block(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_chiseled_sandstone", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE_PILLAR =  registerAdventBlock(6, () ->
			new RotatedPillarBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())),
		"gallifrey_sandstone_pillar", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> STRIPPED_GALLIFREY_OAK_LOG =  registerAdventBlock(6, () ->
			log(MaterialColor.COLOR_RED, MaterialColor.COLOR_PINK),
		"stripped_gallifrey_oak_log", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_PLANKS =  registerAdventBlock(6, () ->
			new Block(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)),
		"gallifrey_oak_planks", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_SLAB =  registerAdventBlock(6, () ->
			new SlabBlock(AbstractBlock.Properties.copy(GALLIFREY_OAK_PLANKS.get())),
		"gallifrey_oak_slab", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_STAIRS =  registerAdventBlock(6, () ->
			new StairsBlock(() -> GALLIFREY_OAK_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(GALLIFREY_OAK_PLANKS.get())),
		"gallifrey_oak_stairs", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_LEAVES =  registerAdventBlock(6, () ->
			new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES).harvestTool(ToolType.HOE).sound(SoundType.GRASS).noOcclusion()),
		"gallifrey_oak_leaves", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_DOOR = registerAdventBlock(6, () ->
			new DoorBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD).noOcclusion()),
		"gallifrey_oak_door", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_TRAPDOOR =  registerAdventBlock(6, () ->
			new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD).noOcclusion()),
		"gallifrey_oak_trapdoor", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_STONE = registerAdventBlock(6,
		() -> new Block(AbstractBlock.Properties.copy(Blocks.STONE)), "gallifrey_stone", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_COBBLESTONE = registerAdventBlock(6,
		() -> new Block(AbstractBlock.Properties.of(Material.STONE)), "gallifrey_cobblestone", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> CONSOLE = registerAdventBlock(24,
		() -> new ConsoleBlock(ConsoleTileEntity::new, AbstractBlock.Properties.of(Material.HEAVY_METAL)), "console", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> CHRISTMAS_LIGHTS = registerBlock(() ->
		new ChristmasLightsBlock(AbstractBlock.Properties.of(Material.DECORATION).strength(1F).sound(SoundType.STONE).noOcclusion()),
		"christmas_lights", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> CLOAKING_PANEL = registerBlock(() ->
			new CloakPanel(AbstractBlock.Properties.of(Material.STONE).strength(6.25F, 5.75F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)),
		"cloak_panel", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> PLAYER_LOCATOR = registerBlock(() ->
			new PlayerLocatorBlock(AbstractBlock.Properties.of(Material.STONE).strength(6.25F, 5.75F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)),
		"player_locator", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> FORCEFIELD_PANEL = registerBlock(() ->
			new ForceFieldPanel(AbstractBlock.Properties.of(Material.STONE).strength(6.25F, 5.75F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)),
		"forcefield_panel", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> CHRISTMAS_PUDDING = registerBlock(() ->
		new ChristmasPuddingBlock(AbstractBlock.Properties.of(Material.CAKE).strength(0.5F).sound(SoundType.WOOL)), "christmas_pudding", ItemGroup.TAB_FOOD);

	public static RegistryObject<Block> CHRISTMAS_PRESENT = registerBlock(() ->
		new ChristmasPresentBlock(AbstractBlock.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion()
				.harvestTool(ToolType.PICKAXE)), "christmas_present", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> DOOR_PANEL = registerBlockAndItem("door_panel",
			() -> new DoorPanelBlock(DoorPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD)),
			new Item.Properties().tab(DMTabs.DM_TARDIS));

	public static RegistryObject<Block> BLUE_CANDY_CANE_BLOCK = registerBlock(() ->
		new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
	.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), "blue_candy_cane_block", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> GREEN_CANDY_CANE_BLOCK = registerBlock(() ->
		new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
		.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), "green_candy_cane_block", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> ORANGE_CANDY_CANE_BLOCK = registerBlock(() ->
		new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
		.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), "orange_candy_cane_block", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> PINK_CANDY_CANE_BLOCK = registerBlock(() ->
		new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
		.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), "pink_candy_cane_block", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> PURPLE_CANDY_CANE_BLOCK = registerBlock(() ->
		new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
		.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), "purple_candy_cane_block", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> RED_CANDY_CANE_BLOCK = registerBlock(() ->
		new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
		.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), "red_candy_cane_block", DMTabs.DM_TARDIS);

	public static RegistryObject<Block> YELLOW_CANDY_CANE_BLOCK = registerBlock(() ->
		new CandyCaneBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_GRAY).strength(6.25F, 5.75F).requiresCorrectToolForDrops()
		.harvestTool(ToolType.PICKAXE).harvestLevel(1).sound(SoundType.STONE)), "yellow_candy_cane_block", DMTabs.DM_TARDIS);

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

		public static RegistryObject<Block> DALEK_PUMPKIN = registerBlock(() -> new DalekPumpkinBlock(AbstractBlock.Properties.copy(Blocks.CARVED_PUMPKIN)), "dalek_pumpkin", ItemGroup.TAB_BUILDING_BLOCKS);
		public static RegistryObject<Block> CARVED_DALEK_PUMPKIN = registerBlock(() -> new CarvedDalekPumpkinBlock(AbstractBlock.Properties.copy(Blocks.CARVED_PUMPKIN)), "carved_dalek_pumpkin", ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> CHRISTMAS_TREE = registerBlock( () -> new ChristmasTreeBlock(AbstractBlock.Properties.of(Material.WOOD)
			.harvestTool(ToolType.AXE).noOcclusion().dynamicShape()), "christmas_tree", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> CHRISTMAS_CRACKER = registerBlock(() -> new ChristmasCrackerBlock(AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion().dynamicShape().noCollission().sound(SoundType.WOOL)), "christmas_cracker");

	public static RegistryObject<Block> MAGPIE_TELEVISION = registerBlock(() -> new MagpieTelevisionBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion()), "magpie_television", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> TITANIC_SNOWGLOBE = registerBlock(() ->
			new SnowGlobeBlock(AbstractBlock.Properties.of(Material.GLASS).strength(0.8F, 0.8F).noOcclusion().dynamicShape().sound(SoundType.GLASS)),
		"titanic_snowglobe", ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> SPECIMEN_JAR = registerBlock(() -> new SpecimenJarBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).noOcclusion()), "specimen_jar");

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

	public static RegistryObject<Block> ENGINE = registerBlock(() -> new EngineBlock(AbstractBlock.Properties.of(Material.PISTON).noOcclusion()), "engine", ItemGroup.TAB_MATERIALS);

	public static <B extends Block> RegistryObject<Block> registerBlock(Supplier<B> block, String name, ItemGroup itemgroup) {
		return registerBlock(block, name, (new Item.Properties()).tab(itemgroup), true);
	}

	public static <B extends Block> RegistryObject<Block> registerContainer(Material material, String name, SoundType soundType) {
		return registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(material).strength(2.0F, 2.5F).sound(soundType).noOcclusion()),
			name, (new Item.Properties()).tab(DMACreativeTabs.DMA_ROUNDEL_CONTAINERS), true);
	}

	@SafeVarargs
	public static void registerRenderTypes(RenderType renderType, RegistryObject<Block> ...blocks){
		for(RegistryObject<Block> block : blocks ){
			if(block != null && block.isPresent()){
				registerRenderType(block.get(), renderType);
			}
		}
	}

	public static RotatedPillarBlock log(MaterialColor color, MaterialColor color1) {
		return new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
			(state) ->
				state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? color : color1)
			.strength(2.0F).sound(SoundType.WOOD));
	}

	public static void registerRenderTypes() {

		registerRenderTypes(RenderType.cutoutMipped(),
			BLUE_BAUBLE_BLOCK,
			GOLD_BAUBLE_BLOCK,
			GREEN_BAUBLE_BLOCK,
			RED_BAUBLE_BLOCK,
			TITANIC_SNOWGLOBE,
			MAGPIE_TELEVISION,
			CHRISTMAS_CRACKER,
			TARDIS_SNOWGLOBE,
			CHRISTMAS_TREE,
			GALLIFREY_OAK_LEAVES
		);

		registerRenderTypes(RenderType.translucent(),
			SPECIMEN_JAR
		);

		registerRenderTypes(RenderType.cutout(),
			STEEL_BEAMS_ROUNDEL_CONTAINER,
			RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER,
			STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER,
			GALLIFREY_OAK_TRAPDOOR,
			GALLIFREY_OAK_DOOR,
			GALLIFREY_OAK_SAPLING
		);

	}
	public static <B extends Block> RegistryObject<Block> registerContainer(Material material, String name) {
		return registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(material).strength(2.0F, 2.5F).sound(SoundType.WOOD)),
			name, (new Item.Properties()).tab(DMACreativeTabs.DMA_ROUNDEL_CONTAINERS), true);
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

