package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class TimeLordEntity extends RegeneratingEntity {
	public static final DataParameter<String> TIMELORD_TYPE = EntityDataManager.defineId(TimeLordEntity.class, DataSerializers.STRING);
	public TimeLordEntity(EntityType<? extends RegeneratingEntity> type, World world) {
		super(type, world);
	}

	protected void defineSynchedData() {
		TimeLordType[] types = TimeLordType.values();
		this.getEntityData().define(TIMELORD_TYPE, types[this.random.nextInt(types.length)].getName());
		super.defineSynchedData();
	}

	public TimeLordType getTimelordType() {
		return TimeLordType.get(this.entityData.get(TIMELORD_TYPE));
	}

	public void setTimelordType(String type) {
		setTimelordType(TimeLordType.get(type));
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes()
			.add(Attributes.ATTACK_DAMAGE, 1.0D)
			.add(Attributes.MOVEMENT_SPEED, (double) 0.2F)
			.add(Attributes.ATTACK_SPEED, 1.0f)
			.add(Attributes.LUCK)
			.add(Attributes.ATTACK_KNOCKBACK)
			.add(Attributes.FOLLOW_RANGE, 30.0D);
	}

	public void setTimelordType(TimeLordType type) {
		if (this.entityData != null) {
			this.entityData.set(TIMELORD_TYPE, type.getName());
		}
	}
}
