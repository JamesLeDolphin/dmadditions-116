package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.World;

public class WhispermanEntity extends MonsterEntity{

	public WhispermanEntity(EntityType<? extends MonsterEntity> type, World world) {
		super(type, world);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MonsterEntity.createMonsterAttributes()
			.add(Attributes.FOLLOW_RANGE, 35.0D)
			.add(Attributes.MOVEMENT_SPEED, (double)0.13F)
			.add(Attributes.ATTACK_DAMAGE, 15.0D)
			.add(Attributes.ARMOR, 2.0D)
			.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
	}


	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.addBehaviourGoals();
	}

	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(2, new WhispermanAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglinEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_ON_LAND_SELECTOR));
	}

	static class WhispermanAttackGoal extends MeleeAttackGoal{
		public WhispermanAttackGoal(CreatureEntity entity, double speedModifier, boolean followEvenIfNotSeen) {
			super(entity, speedModifier, followEvenIfNotSeen);
		}

		@Override
		public void start() {
			super.start();
		}

		public void stop() {
			LivingEntity livingentity = this.mob.getTarget();
			if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
				this.mob.setTarget((LivingEntity)null);
				this.mob.setAggressive(false);
			}

			// this.mob.setAggressive(false);
			this.mob.getNavigation().stop();
		}

		@Override
		public void tick() {
			super.tick();
			if(!this.canContinueToUse()){
				this.mob.setAggressive(false);
			}
		}
		
		protected void checkAndPerformAttack(LivingEntity livingEntity, double v) {
			double d0 = this.getAttackReachSqr(livingEntity);
			if(v <= d0){
				this.resetAttackCooldown();
				// this.mob.swing(Hand.MAIN_HAND);
				this.mob.doHurtTarget(livingEntity);
			}else if(!this.canContinueToUse()){
				this.mob.setAggressive(false);
			}
		}

		public boolean canContinueToUse(LivingEntity entity){
			LivingEntity livingentity = this.mob.getTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else if (this.mob.getNavigation().isDone()) {
				return false;
			} else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
				return false;
			} else {
				return !(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative();
			}
		}

		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse();
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		return entity.hurt(DamageSource.MAGIC, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
	}

}
