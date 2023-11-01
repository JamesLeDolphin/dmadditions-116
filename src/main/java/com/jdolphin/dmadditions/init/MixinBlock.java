package com.jdolphin.dmadditions.init;


import com.jdolphin.dmadditions.block.tardis.*;
import com.swdteam.common.tileentity.tardis.CoordPanelTileEntity;
import com.swdteam.common.tileentity.tardis.DimensionSelectorTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.function.Supplier;

public enum MixinBlock {
	FAST_RETURN_LEVER(() -> new BetterFastReturnLeverBlock(
		AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE))),

	FLIGHT_LEVER(() -> new BetterFlightLeverBlock(
		AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE))),

	CHAMELEON_PANEL(() -> new BetterChameleonPanelBlock(
		AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),

	DIMENSION_SELECTOR_PANEL(() -> new BetterDimensionSelector(DimensionSelectorTileEntity::new,
		AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),

	COORD_PANEL(() -> new BetterCoordPanelBlock(CoordPanelTileEntity::new,
		AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.WOOD))),

	SONIC_INTERFACE(() -> new BetterSonicInterfaceBlock(
		AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),

	WAYPOINT_PANEL(() -> new BetterWaypointPanelBlock(
		AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),

	FLIGHT_PANEL(() -> new BetterFlightPanel(
		AbstractBlock.Properties.of(Material.WOOD).instabreak().noOcclusion().sound(SoundType.WOOD))),

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
