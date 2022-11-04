package com.jdolphin.dmadditions.entity;

import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class WoodenCybermanEntity extends CybermanEntity {
	public WoodenCybermanEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		double d0 = 0.0;
		double d1 = 0.0;
		double d2 = 0.0;
		if (target.isAlive()) {
			d0 = target.getX() - this.getX();
			d1 = target.getY(0.3) - this.getY() - 0.75;
			d2 = target.getZ() - this.getZ();
			if (this.level.random.nextInt(5) == 2) {
				this.playSound((SoundEvent) DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), 1.0F, 1.0F);
			}

			LaserEntity laser = new LaserEntity(this.level, this, 0.2F, 2.0F);
			laser.setLaserType(DMProjectiles.FIRE);
			laser.shoot(d0, d1, d2, 2.5F, 0.0F);
			this.playSound((SoundEvent)DMSoundEvents.ENTITY_DALEK_FLAME_THROWER_SHOOT.get(), 1.0F, 1.0F);
			this.level.addFreshEntity(laser);
		}
	}
}
