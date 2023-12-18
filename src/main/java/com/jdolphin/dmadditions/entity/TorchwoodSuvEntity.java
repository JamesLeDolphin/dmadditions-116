package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public class TorchwoodSuvEntity extends BessieEntity { //FIXME this prob shouldn't extend bessie
	public TorchwoodSuvEntity(EntityType<? extends AnimalEntity> p_i48568_1_, World p_i48568_2_) {
		super(p_i48568_1_, p_i48568_2_);
	}

	@Override
	public void handleStartJump(int p_184775_1_) {
	}

	@Override
	public double getPassengersRidingOffset() {
		return 0.5;
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}
