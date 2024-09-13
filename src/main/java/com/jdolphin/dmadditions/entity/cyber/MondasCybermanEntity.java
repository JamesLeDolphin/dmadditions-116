package com.jdolphin.dmadditions.entity.cyber;

import com.jdolphin.dmadditions.init.DMAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class MondasCybermanEntity extends WoodenCybermanEntity {

	public MondasCybermanEntity(EntityType<? extends MondasCybermanEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		if (target.isAlive()) {
			if (this.hasGun()) super.performRangedAttack(target, distanceFactor);
			else if (distanceFactor <= 5) {
				this.lookAt(target, 10.0f, 10.0f);
				float f = 1.0F;
				if (this.level.getDifficulty() == Difficulty.NORMAL) {
					f = 2.0F;
				}
				if (this.level.getDifficulty() == Difficulty.NORMAL) {
					f = 3.0F;
				}
				target.hurt(DamageSource.indirectMagic(this, this), f);
				target.hurt(DamageSource.mobAttack(this), (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE));
				this.setTarget((LivingEntity)null);
			}
		}
	}

	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(DMAItems.WOODEN_CYBERMAN_SPAWNER.get());
	}
}
