package com.jdolphin.dmadditions.tileentity;

import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.swdteam.common.tileentity.ExtraRotationTileEntityBase;
import net.minecraft.tileentity.ITickableTileEntity;

public class BrokenTardisTileEntity extends ExtraRotationTileEntityBase implements ITickableTileEntity {

	public BrokenTardisTileEntity() {
		super(DMABlockEntities.BROKEN_TARDIS_TILE.get());
	}

	@Override
	public void tick() {

	}
}
