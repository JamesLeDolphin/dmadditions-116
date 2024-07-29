package com.jdolphin.dmadditions.tileentity;

import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeTileEntity;

import java.util.ArrayList;
import java.util.List;


public class ConsoleTileEntity extends DMTileEntityBase implements IForgeTileEntity, ITickableTileEntity {
	public final List<TardisControl> controls = new ArrayList<>();
	private static final AxisAlignedBB HITBOX = new AxisAlignedBB(-1, 0, -1, 2, 2, 2);

	public ConsoleTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public ConsoleTileEntity() {
		this(DMABlockEntities.TILE_CONSOLE.get());
	}

	public void onLoad() {
		makeControls();
	}

	public void makeControls() {
		if (this.level instanceof ServerWorld) {
			ServerWorld world = (ServerWorld) level;
			this.getOldControls();
			if (controls.size() < TardisControl.ControlType.values().length) {
				this.removeControls();

				TardisControl.ControlType[] types = TardisControl.ControlType.values();

				for (TardisControl.ControlType type : types) {
					TardisControl control = new TardisControl(world, type, this);

					Vector3d offset = control.position();
					BlockPos pos = this.getBlockPos();

					control.setPos(pos.getX() + 0.5 + offset.x(),
						pos.getY() + 0.5 + offset.y(),
						pos.getZ() + 0.5 + offset.z());

					world.addFreshEntity(control);
					control.setMaster(this);
					this.controls.add(control);
				}
			}
		}
	}

	private void getOldControls() {
		this.controls.clear();
		if (level != null) {
		for (TardisControl control : level.getEntitiesOfClass(TardisControl.class, HITBOX.move(this.worldPosition).inflate(2))) {
			if (control != null) {
				control.setMaster(this);
				this.controls.add(control);
			}
		}
		}
	}

	public void removeControls() {
		if (level != null) {
			for (TardisControl control : level.getEntitiesOfClass(TardisControl.class, HITBOX.move(this.worldPosition).inflate(5))) {
				control.remove();
			}
			this.controls.clear();
		}
	}

	public void setRemoved() {
		removeControls();
		super.setRemoved();
	}

	@Override
	public void tick() {
		if (this.controls.isEmpty()) {
			this.makeControls();
		}
	}
}
