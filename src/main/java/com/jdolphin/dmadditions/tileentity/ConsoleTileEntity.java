package com.jdolphin.dmadditions.tileentity;

import com.jdolphin.dmadditions.entity.control.FlightControl;
import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeTileEntity;

import java.util.List;


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
			TardisControl control = new FlightControl(DMAEntities.FLIGHT_CONTROL.get(), level);
			control.setPos(pos.getX(), pos.getY(), pos.getZ());
			level.addFreshEntity(control);
		}
	}

	public boolean isRemoved() {
		if (this.level != null && !level.isClientSide()) {
			List<Entity> entities = level.getEntitiesOfClass(TardisControl.class, this.getRenderBoundingBox().move(this.worldPosition).inflate(1));
			for (Entity control : entities) {
				control.remove(false);
			}
		}
		return this.remove;
	}
}
