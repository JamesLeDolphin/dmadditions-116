package com.jdolphin.dmadditions.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;

public class DalekSantaBase extends DalekBase {
	public DalekSantaBase(String dalekName) {
		super(dalekName);
	}
	public float getMaxHealth() {
		return 12.0F;
	}

	public DMProjectiles.Laser getLaser(DalekEntity dalek) {
		return DMProjectiles.GREEN_LASER;
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
