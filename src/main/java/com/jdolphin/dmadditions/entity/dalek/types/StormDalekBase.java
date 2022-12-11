package com.jdolphin.dmadditions.entity.dalek.types;

import com.jdolphin.dmadditions.init.DMASoundEvents;
import com.swdteam.common.entity.dalek.DalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;

public class StormDalekBase extends DalekBase {
		public StormDalekBase(String dalekName) {
			super(dalekName);
		}

		public float getMaxHealth() {
			return 50.0F;
		}

		public boolean canFly() {
		return true;
	}

		public SoundEvent getLivingSound(Entity e) {
			return null;
		}

		public DMProjectiles.Laser getLaser(DalekEntity dalek) {
			return DMProjectiles.EXPLOSIVE_LASER;
		}

		public SoundEvent getDeathSound(Entity e) {
		return DMASoundEvents.DALEK_STORM_DEATH.get();
	}

		public SoundEvent getAttackSound(Entity e) {
			return (SoundEvent) DMASoundEvents.DALEK_STORM_EXTERMINATE.get();
		}

		public SoundEvent getShootSound(Entity e) {
			return (SoundEvent)DMSoundEvents.ENTITY_DALEK_SWD_SHOOT.get();
		}

		protected void aiStep() {
		}
	}
