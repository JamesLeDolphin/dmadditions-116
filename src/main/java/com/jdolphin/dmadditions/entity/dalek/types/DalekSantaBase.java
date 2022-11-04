package com.jdolphin.dmadditions.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMParticleTypes;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.particles.IParticleData;
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
	public boolean canFly() {
		return true;
	}
	public SoundEvent getAttackSound(Entity e) {
	return DMSoundEvents.ENTITY_DALEK_MOLTEN_ATTACK.get();
	}
	public SoundEvent getShootSound(Entity e) {
		return DMSoundEvents.ENTITY_DALEK_GUNSTICK_SHOOT.get();
	}


	protected void aiStep() {
	}
	public void onUpdate(Entity e) {
		if (e.level.isClientSide) {
			DalekEntity dalek = (DalekEntity)e;
			if (dalek.isFlying() && dalek.level.isClientSide) {
				if (dalek.level.random.nextInt(24) == 0 && !dalek.isSilent()) {
				}

				for(int i = 0; i < 2; ++i) {
					dalek.level.addParticle((IParticleData) DMParticleTypes.RED_DALEK_HOVER.get(), dalek.getRandomX(0.5), dalek.getY(-0.01), dalek.getRandomZ(0.5), 0.0, 0.0, 0.0);
				}
			}
		}

		super.onUpdate(e);
	}
	public boolean causeFallDamage(float f1, float f2) {
		return false;
	}

}
