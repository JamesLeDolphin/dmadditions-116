package com.jdolphin.dmadditions.common.tileentity;

import com.jdolphin.dmadditions.common.init.DMABlockEntities;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class JavaJsonModelLoaderTileEntity extends TileEntity {

	public JavaJsonModelLoaderTileEntity(TileEntityType<?> p_i48289_1_) {
		super(p_i48289_1_);
	}

	public JavaJsonModelLoaderTileEntity() {
		this(DMABlockEntities.JAVAJSON_MODEL_LOADER.get());
	}
}
