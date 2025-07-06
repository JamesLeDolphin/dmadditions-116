package com.jdolphin.dmadditions.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;

public class SteampunkDalekBase extends DalekBase {
	public SteampunkDalekBase(String dalekName) {
		super(dalekName);
	}

	public float getMaxHealth() {
		return 15.0F;
	}

	public DMProjectiles.Laser getLaser(DalekEntity dalek) {
		return DMProjectiles.SMOKE;
	}

	public SoundEvent getAttackSound(Entity e) {
		return DMSoundEvents.ENTITY_DALEK_MOLTEN_ATTACK.get();
	}

	public SoundEvent getShootSound(Entity e) {
		return DMSoundEvents.ENTITY_DALEK_GUNSTICK_SHOOT.get();
	}


	protected void aiStep() {
	}
}
