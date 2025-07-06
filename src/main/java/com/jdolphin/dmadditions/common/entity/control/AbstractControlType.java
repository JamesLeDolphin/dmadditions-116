package com.jdolphin.dmadditions.common.entity.control;

import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractControlType {
	private final ResourceLocation name;
	protected Vector3d offset = Vector3d.ZERO;

	public AbstractControlType(ResourceLocation name) {
		this.name = name;
	}

	public ResourceLocation getName() {
		return name;
	}

	public Vector3d getOffset() {
		return offset;
	}

	public void setOffset(Vector3d offset) {
		this.offset = offset;
	}

	protected void setDoors(TardisTileEntity tardis, boolean open) {
		tardis.setDoor(TardisDoor.BOTH, open, TardisTileEntity.DoorSource.INTERIOR);
		tardis.setDoor(TardisDoor.BOTH, open, TardisTileEntity.DoorSource.TARDIS);
	}

	public abstract void onPress(ServerWorld level, PlayerEntity player, BlockPos pos, TardisData data, TardisControl control);
}
