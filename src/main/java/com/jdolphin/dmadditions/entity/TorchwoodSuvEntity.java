package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public class TorchwoodSuvEntity extends BessieEntity { //FIXME this prob shouldn't extend bessie
	public TorchwoodSuvEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void handleStartJump(int l) {
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
