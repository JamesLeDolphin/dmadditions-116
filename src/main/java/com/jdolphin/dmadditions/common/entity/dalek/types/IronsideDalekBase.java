package com.jdolphin.dmadditions.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;

public class IronsideDalekBase extends DalekBase {
	public IronsideDalekBase(String dalekName) {
		super(dalekName);
	}

	public float getMaxHealth() {
		return 15.0F;
	}

	public DMProjectiles.Laser getLaser(DalekEntity dalek) {
		return DMProjectiles.BLUE_LASER;
	}

	public SoundEvent getAttackSound(Entity e) {
		return (SoundEvent) DMSoundEvents.ENTITY_DALEK_TIME_WAR_ATTACK.get();
	}

	protected void aiStep() {
	}
}
