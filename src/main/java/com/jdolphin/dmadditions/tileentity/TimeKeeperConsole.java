package com.jdolphin.dmadditions.tileentity;

import com.swdteam.common.block.RotatableTileEntityBase;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Supplier;

public class TimeKeeperConsole extends RotatableTileEntityBase.WaterLoggable {
	public TimeKeeperConsole(Supplier<TileEntity> tileEntitySupplier, int light) {
		super(tileEntitySupplier, light);
	}


	//We have permission to use this in the update
}
