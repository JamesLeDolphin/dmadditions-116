package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class K9Entity extends WolfEntity {
	public K9Entity(EntityType<? extends WolfEntity> p_i50240_1_, World p_i50240_2_) {
		super(p_i50240_1_, p_i50240_2_);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.IRON_GOLEM_HURT;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3F)
			.add(Attributes.MAX_HEALTH, 8.0D)
			.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}
}
