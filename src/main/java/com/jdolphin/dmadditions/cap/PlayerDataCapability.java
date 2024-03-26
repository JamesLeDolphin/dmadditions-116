package com.jdolphin.dmadditions.cap;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class PlayerDataCapability implements IPlayerDataCap {
	private PlayerEntity player;

	public PlayerDataCapability(PlayerEntity player) {
	this.player = player;
	}

	@Override
	public void tick() {
		Helper.print("CAP CAP CAP CAP");
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {

	}
}
