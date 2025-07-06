package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.common.block.*;
import com.jdolphin.dmadditions.common.block.christmas.*;
import com.jdolphin.dmadditions.common.block.tardis.*;
import com.jdolphin.dmadditions.common.block.tardis.console.ClassicConsoleBlock;
import com.jdolphin.dmadditions.common.tileentity.BrokenTardisTileEntity;
import com.jdolphin.dmadditions.common.tileentity.DoorPanelTileEntity;
import com.jdolphin.dmadditions.common.tileentity.JavaJsonModelLoaderTileEntity;
import com.jdolphin.dmadditions.common.world.tree.GallifreyOakTree;
import com.swdteam.common.block.LogBlock;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMTabs;
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

	public static final RegistryObject<Block> CORAL_FLIGHT_LEVER = registerBlockWithItem("coral_flight_lever",
		() -> new CoralHandbrake(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE)
			.instabreak().noOcclusion().sound(SoundType.STONE)), DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> COPPER_FLIGHT_LEVER = registerBlockWithItem("copper_flight_lever",
		() -> new CopperHandbrake(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE)
			.instabreak().noOcclusion().sound(SoundType.STONE)), DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> STONE_SONIC_CRYSTAL_ORE = registerBlockWithItem("stone_sonic_crystal_ore",
		() -> new BetterOreBlock(AbstractBlock.Properties.copy(Blocks.COAL_ORE)
			.harvestTool(ToolType.PICKAXE).harvestLevel(2), 1, 5),
		new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS));

	public static RegistryObject<Block> BLUE_BAUBLE_BLOCK = registerBlock(BaubleBlock::new, "blue_bauble");
	public static RegistryObject<Block> GOLD_BAUBLE_BLOCK = registerBlock(BaubleBlock::new, "gold_bauble");
	public static RegistryObject<Block> GREEN_BAUBLE_BLOCK = registerBlock(BaubleBlock::new, "green_bauble");
	public static RegistryObject<Block> RED_BAUBLE_BLOCK = registerBlock(BaubleBlock::new, "red_bauble");

	public static RegistryObject<Block> FOOD_MACHINE = registerBlockWithItem("food_machine",
		() -> new RotateableBlock(AbstractBlock.Properties.of(Material.METAL)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> FLOODLIGHT = registerBlockWithItem("dalek_floodlight",
		() -> new RotateableBlock(AbstractBlock.Properties.of(Material.METAL).noCollission().noOcclusion().lightLevel(state -> 15)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> SCHEMATIC_PANEL = registerBlockWithItem("schematic_panel",
		() -> new RotateableBlock(AbstractBlock.Properties.of(Material.METAL).noCollission().noOcclusion()), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> DALEK_LECTURN = registerBlockWithItem("dalek_lecturn",
		() -> new RotateableBlock(AbstractBlock.Properties.of(Material.METAL)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_LOG = registerBlockWithItem("gallifrey_oak_log",
		() -> new LogBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD), DMABlocks.STRIPPED_GALLIFREY_OAK_LOG), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_WOOD = registerBlockWithItem("gallifrey_oak_wood",
		() -> new LogBlock(AbstractBlock.Properties.copy(GALLIFREY_OAK_LOG.get()), DMABlocks.STRIPPED_GALLIFREY_OAK_LOG), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_SAPLING = registerBlockWithItem("gallifrey_oak_sapling",
		() -> new SaplingBlock(new GallifreyOakTree(), AbstractBlock.Properties.copy(Blocks.OAK_SAPLING)), ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> STRIPPED_GALLIFREY_OAK_WOOD = registerBlockWithItem("stripped_gallifrey_oak_wood",
		() -> new RotatedPillarBlock(AbstractBlock.Properties.copy(GALLIFREY_OAK_LOG.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SAND = registerBlockWithItem("gallifrey_sand",
		() -> new SandBlock(Color.PINK.getRGB(), AbstractBlock.Properties.of(Material.SAND).harvestTool(ToolType.SHOVEL).strength(0.5f).sound(SoundType.SAND)),ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE =  registerBlockWithItem("gallifrey_sandstone",
		() -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SMOOTH_SANDSTONE =  registerBlockWithItem("gallifrey_smooth_sandstone",
		() -> new Block(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_CUT_SANDSTONE =  registerBlockWithItem("gallifrey_cut_sandstone",
		() -> new Block(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_CUT_SANDSTONE_SLAB =  registerBlockWithItem("gallifrey_cut_sandstone_slab",
		() -> new SlabBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE_SLAB =  registerBlockWithItem("gallifrey_sandstone_slab",
		() -> new SlabBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE_WALL =  registerBlockWithItem("gallifrey_sandstone_wall",
		() -> new WallBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE_STAIRS =  registerBlockWithItem("gallifrey_sandstone_stairs",
		() -> new StairsBlock(() -> GALLIFREY_SANDSTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SMOOTH_SANDSTONE_SLAB =  registerBlockWithItem("gallifrey_smooth_sandstone_slab",
		() -> new SlabBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SMOOTH_SANDSTONE_STAIRS =  registerBlockWithItem("gallifrey_smooth_sandstone_stairs",
		() -> new StairsBlock(() -> GALLIFREY_SMOOTH_SANDSTONE.get().defaultBlockState(), AbstractBlock.Properties.copy(GALLIFREY_SMOOTH_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_CHISELED_SANDSTONE =  registerBlockWithItem("gallifrey_chiseled_sandstone",
		() -> new Block(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_SANDSTONE_PILLAR =  registerBlockWithItem("gallifrey_sandstone_pillar",
		() -> new RotatedPillarBlock(AbstractBlock.Properties.copy(GALLIFREY_SANDSTONE.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> STRIPPED_GALLIFREY_OAK_LOG =  registerBlockWithItem("stripped_gallifrey_oak_log",
		() -> log(MaterialColor.COLOR_RED, MaterialColor.COLOR_PINK), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_PLANKS =  registerBlockWithItem("gallifrey_oak_planks",
		() -> new Block(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_SLAB =  registerBlockWithItem("gallifrey_oak_slab",
		() -> new SlabBlock(AbstractBlock.Properties.copy(GALLIFREY_OAK_PLANKS.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_STAIRS =  registerBlockWithItem("gallifrey_oak_stairs",
		() -> new StairsBlock(() -> GALLIFREY_OAK_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(GALLIFREY_OAK_PLANKS.get())), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_LEAVES =  registerBlockWithItem("gallifrey_oak_leaves",
		() -> new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES).harvestTool(ToolType.HOE).sound(SoundType.GRASS).noOcclusion()), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_DOOR = registerBlockWithItem("gallifrey_oak_door",
		() -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD).noOcclusion()), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_OAK_TRAPDOOR =  registerBlockWithItem("gallifrey_oak_trapdoor",
		() -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD).noOcclusion()), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_STONE = registerBlockWithItem( "gallifrey_stone",
		() -> new Block(AbstractBlock.Properties.copy(Blocks.STONE)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_ZEITON_ORE = registerBlockWithItem("gallifrey_zeiton_ore",
		() -> new BetterOreBlock(AbstractBlock.Properties.copy(Blocks.COAL_ORE), 2, 5),  ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> GALLIFREY_COBBLESTONE = registerBlockWithItem("gallifrey_cobblestone",
		() -> new Block(AbstractBlock.Properties.of(Material.STONE)),  ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> CLASSIC_CONSOLE = registerBlockWithItem("classic_console",
		() -> new ClassicConsoleBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL)), new Item.Properties().tab(DMTabs.DM_TARDIS));

	public static RegistryObject<Block> BROKEN_TARDIS = registerBlockWithItem("broken_tardis",
		() -> new BrokenTardisBlock(BrokenTardisTileEntity::new, AbstractBlock.Properties.of(Material.HEAVY_METAL)), new Item.Properties().tab(DMTabs.DM_TARDIS));

	public static RegistryObject<Block> CHRISTMAS_LIGHTS = registerBlockWithItem("christmas_lights", () ->
			new ChristmasLightsBlock(AbstractBlock.Properties.of(Material.DECORATION).strength(1F).sound(SoundType.STONE).noOcclusion()), ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> CLOAKING_PANEL = registerBlockWithItem("cloak_panel", () ->
			new CloakPanel(AbstractBlock.Properties.of(Material.STONE).strength(6.25F, 5.75F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> PLAYER_LOCATOR = registerBlockWithItem("player_locator", () ->
			new PlayerLocatorBlock(AbstractBlock.Properties.of(Material.STONE).strength(6.25F, 5.75F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> FORCEFIELD_PANEL = registerBlockWithItem("forcefield_panel", () ->
			new ForceFieldPanel(AbstractBlock.Properties.of(Material.STONE).strength(6.25F, 5.75F).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> CHRISTMAS_PRESENT = registerBlockWithItem("christmas_present", () ->
		new ChristmasPresentBlock(AbstractBlock.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion()
			.harvestTool(ToolType.PICKAXE)), ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> JAVAJSON_MODEL_LOADER = registerBlockWithItem("javajson_model_loader",
		() -> new JavaJsonModelLoaderBlock(JavaJsonModelLoaderTileEntity::new, AbstractBlock.Properties.copy(Blocks.STONE)),
		new Item.Properties().tab(DMTabs.DM_TARDIS));

	public static RegistryObject<Block> DOOR_PANEL = registerBlockWithItem("door_panel",
		() -> new DoorPanelBlock(DoorPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD)),
		new Item.Properties().tab(DMTabs.DM_TARDIS));

	public static RegistryObject<Block> RANDOMIZER = registerBlockWithItem("randomizer",
		() -> new RandomizerBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
		 DMTabs.DM_TARDIS);

	public static RegistryObject<Block> CHEESE_ORE = registerBlockWithItem("cheese_ore",
		() -> new OreBlock(AbstractBlock.Properties.copy(DMBlocks.ANORTHOSITE.isPresent() ? DMBlocks.ANORTHOSITE.get() : Blocks.STONE)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> DALEK_PUMPKIN = registerBlockWithItem("dalek_pumpkin", () ->
		new DalekPumpkinBlock(AbstractBlock.Properties.copy(Blocks.CARVED_PUMPKIN)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> CARVED_DALEK_PUMPKIN = registerBlockWithItem("carved_dalek_pumpkin", () ->
		new CarvedDalekPumpkinBlock(AbstractBlock.Properties.copy(Blocks.CARVED_PUMPKIN)), ItemGroup.TAB_BUILDING_BLOCKS);

	public static RegistryObject<Block> MAGPIE_TELEVISION = registerBlockWithItem("magpie_television", () ->
		new MagpieTelevisionBlock(AbstractBlock.Properties.of(Material.WOOD).noOcclusion()), ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> TITANIC_SNOWGLOBE = registerBlockWithItem("titanic_snowglobe", () ->
			new SnowGlobeBlock(AbstractBlock.Properties.of(Material.GLASS).strength(0.8F, 0.8F).noOcclusion().dynamicShape().sound(SoundType.GLASS)), ItemGroup.TAB_DECORATIONS);

	public static RegistryObject<Block> SPECIMEN_JAR = registerBlock(() -> new SpecimenJarBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).noOcclusion()), "specimen_jar");

	public static RegistryObject<Block> ENGINE = registerBlockWithItem("engine", () -> new EngineBlock(AbstractBlock.Properties.of(Material.PISTON).noOcclusion()), ItemGroup.TAB_MATERIALS);

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
	public static RegistryObject<Block> BLACK_PLASTIC_SHAPE_ROUNDEL_CONTAINER = registerContainer(Material.WOOD, "black_plastic_shape_roundel_container");

	@SafeVarargs
	public static void registerRenderTypes(RenderType renderType, RegistryObject<Block>... blocks) {
		for (RegistryObject<Block> block : blocks) {
			if (block != null && block.isPresent()) {
				registerRenderType(block.get(), renderType);
			}
		}
	}

	public static void registerRenderTypes() {
		registerRenderTypes(RenderType.cutoutMipped(),
			BLUE_BAUBLE_BLOCK,
			GOLD_BAUBLE_BLOCK,
			GREEN_BAUBLE_BLOCK,
			RED_BAUBLE_BLOCK,
			TITANIC_SNOWGLOBE,
			MAGPIE_TELEVISION,
			GALLIFREY_OAK_LEAVES,
			FOOD_MACHINE,
			DALEK_LECTURN,
			CLASSIC_CONSOLE
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

	public static <B extends Block> RegistryObject<Block> registerContainer(Material material, String name, SoundType soundType) {
		return registerBlockWithItem(name, () -> new RoundelContainerBlock(AbstractBlock.Properties.of(material).strength(2.0F, 2.5F).sound(soundType).noOcclusion()),
			DMACreativeTabs.DMA_ROUNDEL_CONTAINERS);
	}

	public static RotatedPillarBlock log(MaterialColor color, MaterialColor color1) {
		return new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD,
			(state) ->
				state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? color : color1)
			.strength(2.0F).sound(SoundType.WOOD));
	}

	public static <B extends Block> RegistryObject<Block> registerContainer(Material material, String name) {
		return registerContainer(material, name, SoundType.WOOD);
	}

	public static <B extends Block> RegistryObject<Block> registerBlock(Supplier<B> block, String name) {
		return BLOCKS.register(name, block);
	}

	public static <B extends Block> RegistryObject<Block> registerBlockWithItem(String name, Supplier<B> block, ItemGroup group) {
		return registerBlockWithItem(name, block, new Item.Properties().tab(group));
	}

	public static <B extends Block> RegistryObject<Block> registerBlockWithItem(String name, Supplier<B> block, Item.Properties properties) {
		RegistryObject<Block> blockObject = registerBlock(block, name);
		DMAItems.ITEMS.register(name, () -> new BlockItem(blockObject.get(), properties));
		return blockObject;
	}
}

