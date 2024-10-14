package com.jdolphin.dmadditions.tileentity;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.swdteam.common.tileentity.DMTileEntityBase;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;


public class ConsoleTileEntity extends DMTileEntityBase implements ITickableTileEntity {
	public final List<TardisControl> controls = new ArrayList<>();
	private int controlSpawnCooldown;
	private boolean hasControls = false;
	public static String TAG_HAS_CONTROLS = "hasControls";
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
		this.hasControls = false;
		this.controlSpawnCooldown = 10;
	}


	public void load(@NotNull BlockState state, @NotNull CompoundNBT tag) {
		super.load(state, tag);
		this.hasControls = tag.getBoolean(TAG_HAS_CONTROLS);
	}

	public @NotNull CompoundNBT save(@NotNull CompoundNBT tag) {
		tag.putBoolean(TAG_HAS_CONTROLS, this.hasControls);
		return super.save(tag);
	}

	public void makeControls() {
		if (level != null && this.level instanceof ServerWorld) {
			ServerWorld world = (ServerWorld) level;
			TardisControl.ControlType[] types = TardisControl.ControlType.values();
			this.hasControls = true;
			for (TardisControl.ControlType type : types) {
				TardisControl control = new TardisControl(world, type, this.worldPosition);

				Vector3d offset = control.position();
				BlockPos pos = this.getBlockPos();

				control.setPos(pos.getX() + 0.5 + offset.x(),
					pos.getY() + 0.5 + offset.y(),
					pos.getZ() + 0.5 + offset.z());

				control.setMaster(this.worldPosition);
				world.addFreshEntity(control);
				this.controls.add(control);
			}
		}
	}

	public void removeControls() {
		this.controls.forEach(Entity::remove);
		this.controls.clear();
		this.hasControls = false;
	}

	public void setRemoved() {
		removeControls();
		super.setRemoved();
	}

	@Override
	public void tick() {
		if (controlSpawnCooldown > 0) {
			controlSpawnCooldown--;
			if (controlSpawnCooldown == 0 && !this.hasControls) this.makeControls();
		}
	}
}
