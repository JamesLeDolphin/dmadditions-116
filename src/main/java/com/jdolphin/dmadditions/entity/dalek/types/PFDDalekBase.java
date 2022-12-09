package com.jdolphin.dmadditions.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;

public class PFDDalekBase extends DalekBase {
	public PFDDalekBase(String dalekName) {
		super(dalekName);
	}

	public float getMaxHealth() {
		return 17.0F;
	}

	public DMProjectiles.Laser getLaser(DalekEntity dalek) {
		return null;
	}

	public SoundEvent getAttackSound(Entity e) {
		return null;
	}

	@Override
	protected void aiStep() {

	}
}
