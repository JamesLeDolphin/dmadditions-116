package com.jdolphin.dmadditions.tileentity;

import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.tileentity.TileEntityType;

import static com.jdolphin.dmadditions.init.DMABlockEntities.TILE_SPECIMEN_JAR;

public class SpecimenJarTileEntity extends DMTileEntityBase {
	public SpecimenJarTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public SpecimenJarTileEntity(){
		this(TILE_SPECIMEN_JAR.get());
	}
}
