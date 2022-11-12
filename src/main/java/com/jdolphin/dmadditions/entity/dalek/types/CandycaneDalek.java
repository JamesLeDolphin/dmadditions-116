package com.jdolphin.dmadditions.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;

public class CandycaneDalek extends DalekBase {
	public CandycaneDalek(String dalekName) {
		super(dalekName);
	}
		public float getMaxHealth() {
			return 17.0F;
		}

		public DMProjectiles.Laser getLaser(DalekEntity dalek) {
			return DMProjectiles.NAUSEA_LASER;
		}

		public SoundEvent getAttackSound(Entity e) {
			return (SoundEvent) DMSoundEvents.ENTITY_DALEK_CLASSIC_ATTACK.get();
		}

		protected void aiStep() {
		}
}
