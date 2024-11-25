package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class KantrofarriEntity extends MonsterEntity {

	public KantrofarriEntity(EntityType<? extends MonsterEntity> entityType, World world) {
		super(entityType, world);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MonsterEntity.createMonsterAttributes()
			.add(Attributes.FOLLOW_RANGE, 4.0d)
			.add(Attributes.MOVEMENT_SPEED, (double) 0.3d)
			.add(Attributes.ATTACK_DAMAGE, 3d)
			.add(Attributes.ARMOR, 2.0D)
			.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
	}


	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0f));
		this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractVillagerEntity.class, 6.0f));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.5f));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglinEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
	}

}
