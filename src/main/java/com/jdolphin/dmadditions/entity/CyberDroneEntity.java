package com.jdolphin.dmadditions.entity;

import com.swdteam.common.entity.LookAtGoalBetter;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.goal.RangedLaserAttack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;

public class CyberDroneEntity extends MonsterEntity implements IRangedAttackMob, IForgeEntity, IFlyingAnimal {
	public CyberDroneEntity(EntityType<? extends MonsterEntity> p_i48553_1_, World p_i48553_2_) {
		super(p_i48553_1_, p_i48553_2_);
	}

	@Override
	public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {

	}
	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.17).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 2.0).add(Attributes.FOLLOW_RANGE, 20.0);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(4, new RangedLaserAttack(this, 1.0, 20, 15.0F));

		this.goalSelector.addGoal(5, new LookAtGoalBetter(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, DalekEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, VillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, ZombieVillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, ZombieEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, HuskEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, SkeletonEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, StrayEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, PiglinEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, PiglinBruteEntity.class, true));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}
}
