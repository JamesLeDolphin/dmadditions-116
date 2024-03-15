package com.jdolphin.dmadditions.init;


import com.jdolphin.dmadditions.block.tardis.*;
import com.swdteam.common.block.tardis.FlightPanelBlock;
import com.swdteam.common.tileentity.tardis.CoordPanelTileEntity;
import com.swdteam.common.tileentity.tardis.DimensionSelectorTileEntity;
import com.swdteam.common.tileentity.tardis.TardisDoorHitboxTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.function.Supplier;

public enum MixinBlock {


	FAST_RETURN_LEVER(() -> new BetterFastReturnLeverBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE))),
	FLIGHT_LEVER(() -> new BetterFlightLeverBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE))),

	TARDIS_DOOR_HITBOX(() -> new BetterTardisDoorHitbox(TardisDoorHitboxTileEntity::new, AbstractBlock.Properties.of(Material.STONE).strength(2.8F).sound(SoundType.WOOD))),

	COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	OAK_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	BIRCH_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	ACACIA_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	DARK_OAK_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	SPRUCE_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	CRIMSON_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	WARPED_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	JUNGLE_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	THALMA_PLANKS_COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),

	DIMENSION_SELECTOR_PANEL(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	ACACIA_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	BIRCH_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	CRIMSON_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	DARK_OAK_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	JUNGLE_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	OAK_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	SPRUCE_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	THALMA_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	WARPED_PLANKS_DIMENSION_SELECTOR(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new, AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),

	WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	OAK_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	ACACIA_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	BIRCH_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	CRIMSON_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	DARK_OAK_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	JUNGLE_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	SPRUCE_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	THALMA_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	WARPED_PLANKS_WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),

	CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	OAK_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	BIRCH_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	ACACIA_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	DARK_OAK_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	SPRUCE_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	CRIMSON_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	WARPED_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	JUNGLE_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),
	THALMA_PLANKS_CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),

	SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	ACACIA_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	BIRCH_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	CRIMSON_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	DARK_OAK_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	JUNGLE_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	OAK_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	SPRUCE_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	THALMA_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	WARPED_PLANKS_SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),

	FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),
	THALMA_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	OAK_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	BIRCH_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	CRIMSON_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	DARK_OAK_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	JUNGLE_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	SPRUCE_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	WARPED_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	ACACIA_PLANKS_FLIGHT_PANEL(() -> new BetterFlightPanel(AbstractBlock.Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL))),
	;

	public final Supplier<Block> supplier;

	MixinBlock(Supplier<Block> supplier) {
		this.supplier = supplier;
	}

	public Block get() {
		return this.supplier.get();
	}

	public static boolean has(String name) {
		try {
			get(name);
			return true;
		} catch (Exception ignored) {
			return false;
		}
	}

	public static MixinBlock get(String name) {
		return valueOf(MixinBlock.class, name.toUpperCase());
	}
}
