package com.jdolphin.dmadditions.tileentity;

import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeTileEntity;


public class ConsoleTileEntity extends DMTileEntityBase implements IForgeTileEntity {
	public ConsoleTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public ConsoleTileEntity() {
		this(DMABlockEntities.TILE_CONSOLE.get());
	}

	public void onLoad() {
		World level = this.level;
		BlockPos pos = this.worldPosition;
		if (level != null && !level.isClientSide()) {
			System.out.print("dwadsawfffffffffffff");
			TardisControl control = new TardisControl(DMAEntities.CONTROL.get(), level);
			control.setPos(pos.getX(), pos.getY(), pos.getZ());
			level.addFreshEntity(control);
		}
	}
}
