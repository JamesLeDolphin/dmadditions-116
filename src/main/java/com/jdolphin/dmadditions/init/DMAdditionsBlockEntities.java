package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.tileentity.RoundelContainerTileEntity;
import com.swdteam.common.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class DMAdditionsBlockEntities {
	public static final RegistryObject<TileEntityType<RoundelContainerTileEntity>> TILE_ROUNDEL_CONTAINER;


	static {
		TILE_ROUNDEL_CONTAINER = RegistryHandler.TILE_ENTITY_TYPES.register("roundel_container", () ->
			TileEntityType.Builder.of(RoundelContainerTileEntity::new, new Block[]{
				DMAdditionsBlocks.WHITE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.CLAY_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.ORANGE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.MAGENTA_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.LIGHT_BLUE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.YELLOW_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.LIME_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.PINK_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.GRAY_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.LIGHT_GRAY_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.CYAN_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.PURPLE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.BLUE_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.BROWN_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.GREEN_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.RED_PLASTIC_ROUNDEL_CONTAINER.get(),
				DMAdditionsBlocks.BLACK_PLASTIC_ROUNDEL_CONTAINER.get()
			}).build(null));
	}
}
