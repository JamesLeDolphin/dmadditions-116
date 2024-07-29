package com.jdolphin.dmadditions.tileentity;

import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeTileEntity;

import java.util.ArrayList;
import java.util.List;


public class ConsoleTileEntity extends DMTileEntityBase implements IForgeTileEntity, ITickableTileEntity {
	public final List<TardisControl> controls = new ArrayList<>();
	private int controlSpawnCooldown;
	private static final AxisAlignedBB HITBOX = new AxisAlignedBB(-1, 0, -1, 2, 2, 2);

	public ConsoleTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public ConsoleTileEntity() {
		this(DMABlockEntities.TILE_CONSOLE.get());
	}

	@Override
	public void onLoad() {
		super.onLoad();
		this.controlSpawnCooldown = 10;
	}

	public void makeControls() {
		if (this.level instanceof ServerWorld) {
			ServerWorld world = (ServerWorld) level;
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

	public void removeControls() {
		this.controls.forEach(Entity::remove);
		this.controls.clear();
	}

	public void setRemoved() {
		removeControls();
		super.setRemoved();
	}

	@Override
	public void tick() {
		if (controlSpawnCooldown > 0) controlSpawnCooldown--;
		if (controlSpawnCooldown == 0) this.makeControls();
	}
}
