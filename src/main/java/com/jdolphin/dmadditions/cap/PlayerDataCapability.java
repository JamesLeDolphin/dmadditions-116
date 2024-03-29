package com.jdolphin.dmadditions.cap;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class PlayerDataCapability implements IPlayerDataCap {
	private final String TAG_REGEN_AMOUNT = "regens";
	private PlayerEntity player;
	private final int maxRegens = 12;
	private final int minRegens = 0;
	private int currentRegens;

	public PlayerDataCapability(PlayerEntity player) {
	this.player = player;
	}

	@Override
	public boolean regen() {
		if (hasRegens()) {
			player.addEffect(new EffectInstance(Effects.ABSORPTION, 20 * 3, 0));
			this.addRegens(-1);
			return true;
		}
		else return false;
	}

	@Override
	public int getMaxRegens() {
		return this.maxRegens;
	}

	@Override
	public boolean hasRegens() {
        return currentRegens > minRegens;
    }

	@Override
	public int getRegens() {
		return currentRegens;
	}

	@Override
	public void setRegens(int regens) {
		this.currentRegens = regens;
	}

	@Override
	public void addRegens(int add) {
		this.currentRegens = Math.min(this.getRegens() + add, maxRegens);
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt(TAG_REGEN_AMOUNT, this.currentRegens);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (nbt.contains(TAG_REGEN_AMOUNT)) {
			nbt.getInt(TAG_REGEN_AMOUNT);
		}
	}
}
