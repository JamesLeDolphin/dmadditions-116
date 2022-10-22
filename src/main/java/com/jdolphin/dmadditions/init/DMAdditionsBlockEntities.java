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
				DMAdditionsBlocks.CYAN_PLASTIC_ROUNDEL_CONTAINER.get(),

			}).build(null));
	}
}
