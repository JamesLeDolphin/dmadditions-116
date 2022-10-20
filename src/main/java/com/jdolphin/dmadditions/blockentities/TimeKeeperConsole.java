package com.jdolphin.dmadditions.blockentities;

import com.swdteam.common.block.RotatableTileEntityBase;
import net.minecraft.tileentity.TileEntity;
import com.swdteam.common.block.tardis.CoordPanelBlock;
import com.swdteam.client.render.tileentity.RenderCoordPanel;


import java.util.function.Supplier;

public class TimeKeeperConsole extends RotatableTileEntityBase.WaterLoggable {
	public TimeKeeperConsole(Supplier<TileEntity> tileEntitySupplier, int light) {
		super(tileEntitySupplier, light);
	}


	//We have permission to use this in the update
}
