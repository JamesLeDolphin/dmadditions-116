package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.tileentity.RoundelContainerTileEntity;
import com.swdteam.common.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class DMABlockEntities {
	public static final RegistryObject<TileEntityType<RoundelContainerTileEntity>> TILE_ROUNDEL_CONTAINER;


	static {
		TILE_ROUNDEL_CONTAINER = RegistryHandler.TILE_ENTITY_TYPES.register("roundel_container", () ->
			TileEntityType.Builder.of(RoundelContainerTileEntity::new, new Block[]{
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
				DMABlocks.BLACK_PLASTIC_ROUNDEL_CONTAINER.get()
			}).build(null));
	}
}
