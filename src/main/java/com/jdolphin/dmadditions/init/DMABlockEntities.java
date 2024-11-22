package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DMABlockEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DMAdditions.MODID);

	public static RegistryObject<TileEntityType<BetterScannerTileEntity>> TILE_SCANNER;

	protected static <T extends TileEntity> RegistryObject<TileEntityType<T>> registerAdventTileEntity(int day, String name, Supplier<TileEntityType<T>> supplier){
		if (!TimedUnlock.advent(day)) return null;
		return TILE_ENTITY_TYPES.register(name, supplier);
	}
	public static final RegistryObject<TileEntityType<DoorPanelTileEntity>> TILE_DOOR_PANEL = TILE_ENTITY_TYPES.register("door_panel",
			() -> TileEntityType.Builder.of(DoorPanelTileEntity::new, DMABlocks.DOOR_PANEL.get()).build(null));

	public static final RegistryObject<TileEntityType<ConsoleTileEntity>> TILE_CONSOLE = TILE_ENTITY_TYPES.register("console",
		() -> TileEntityType.Builder.of(ConsoleTileEntity::new, DMABlocks.CONSOLE.get()).build(null));

	public static final RegistryObject<TileEntityType<ReddashStatueTileEntity>> TILE_REDDASH_STATUE = TILE_ENTITY_TYPES.register("reddash_statue", () ->
			TileEntityType.Builder.of(ReddashStatueTileEntity::new, DMABlocks.REDDASH_STATUE.get()).build(null));

	public static final RegistryObject<TileEntityType<SpecimenJarTileEntity>> TILE_SPECIMEN_JAR = TILE_ENTITY_TYPES.register("specimen_jar", () ->
			TileEntityType.Builder.of(SpecimenJarTileEntity::new, DMABlocks.SPECIMEN_JAR.get()).build(null));


	public static final RegistryObject<TileEntityType<RoundelContainerTileEntity>> TILE_ROUNDEL_CONTAINER = TILE_ENTITY_TYPES.register("roundel_container", () ->
			TileEntityType.Builder.of(RoundelContainerTileEntity::new, new Block[] {
				DMABlocks.BLACK_QUARTZ_ROUNDEL_CONTAINER.get(),
				DMABlocks.YELLOW_QUARTZ_ROUNDEL_CONTAINER.get(),
				DMABlocks.WHITE_QUARTZ_ROUNDEL_CONTAINER.get(),
				DMABlocks.SCORCHED_BLACK_QUARTZ_ROUNDEL_CONTAINER.get(),
				DMABlocks.SCORCHED_YELLOW_QUARTZ_ROUNDEL_CONTAINER.get(),
				DMABlocks.SCORCHED_WHITE_QUARTZ_ROUNDEL_CONTAINER.get(),
				DMABlocks.DALEKANIUM_ROUNDEL_CONTAINER.get(),
				DMABlocks.REFINED_DALEKANIUM_ROUNDEL_CONTAINER.get(),
				DMABlocks.PURE_DALEKANIUM_ROUNDEL_CONTAINER.get(),
				DMABlocks.METALERT_ROUNDEL_CONTAINER.get(),
				DMABlocks.STEEL_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLUE_STEEL_ROUNDEL_CONTAINER.get(),
				DMABlocks.RED_STEEL_ROUNDEL_CONTAINER.get(),
				DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(),
				DMABlocks.FILLED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(),
				DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(),
				DMABlocks.FILLED_RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(),
				DMABlocks.STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER.get(),
				DMABlocks.FILLED_STAINLESS_STEEL_BEAMS_ROUNDEL_CONTAINER.get(),
				DMABlocks.STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get(),
				DMABlocks.RUSTED_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get(),
				DMABlocks.STAINLESS_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get(),
				DMABlocks.TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.WHITE_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.ORANGE_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.MAGENTA_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIGHT_BLUE_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.YELLOW_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIME_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.PINK_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.GRAY_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIGHT_GRAY_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.CYAN_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.PURPLE_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLUE_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.BROWN_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.GREEN_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.RED_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLACK_TERRACOTTA_ROUNDEL_CONTAINER.get(),
				DMABlocks.WHITE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.CLAY_ROUNDEL_CONTAINER.get(),
				DMABlocks.ORANGE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.MAGENTA_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIGHT_BLUE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.YELLOW_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIME_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.PINK_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.GRAY_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIGHT_GRAY_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.CYAN_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.PURPLE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLUE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.BROWN_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.GREEN_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.RED_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLACK_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMABlocks.WHITE_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.ORANGE_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.MAGENTA_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIGHT_BLUE_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.YELLOW_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIME_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.PINK_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.GRAY_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIGHT_GRAY_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.CYAN_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.PURPLE_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLUE_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.BROWN_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.GREEN_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.RED_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLACK_PLASTIC_BOWL_ROUNDEL_CONTAINER.get(),
				DMABlocks.WHITE_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.ORANGE_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.MAGENTA_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIGHT_BLUE_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.YELLOW_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIME_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.PINK_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.GRAY_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.LIGHT_GRAY_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.CYAN_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.PURPLE_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLUE_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.BROWN_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.GREEN_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.RED_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get(),
				DMABlocks.BLACK_PLASTIC_SHAPE_ROUNDEL_CONTAINER.get()
			}).build(null));
}
