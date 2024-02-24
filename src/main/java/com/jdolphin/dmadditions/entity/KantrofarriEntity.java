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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.World;

public class KantrofarriEntity extends MonsterEntity {

	public static final DamageSource KANTROFFARI_ATTACK = new DamageSource("kantrofarri").bypassArmor();
	public KantrofarriEntity(EntityType<? extends MonsterEntity> p_i48553_1_, World p_i48553_2_) {
		super(p_i48553_1_, p_i48553_2_);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MonsterEntity.createMonsterAttributes()
			.add(Attributes.FOLLOW_RANGE, 4.0d)
			.add(Attributes.MOVEMENT_SPEED, (double)0.3d)
			.add(Attributes.ATTACK_DAMAGE, 3d)
			.add(Attributes.ARMOR, 2.0D)
			.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
	}


	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0f));
		this.goalSelector.addGoal(8, new LookAtGoal(this, AbstractVillagerEntity.class, 6.0f));
		this.addBehaviourGoals();
	}

	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(3, new KantrofarriAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.5f));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglinEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
	}

	static class KantrofarriAttackGoal extends MeleeAttackGoal{
		public KantrofarriAttackGoal(CreatureEntity entity, double speedModifier, boolean followEvenIfNotSeen) {
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

			this.mob.getNavigation().stop();
		}

		@Override
		public void tick() {
			super.tick();
			if(!this.canContinueToUse()){
				this.mob.setAggressive(false);
			}
		}

		protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
			double d0 = this.getAttackReachSqr(p_190102_1_);
			if(p_190102_2_ <= d0){
				this.resetAttackCooldown();
				// this.mob.swing(Hand.MAIN_HAND);
				this.mob.doHurtTarget(p_190102_1_);
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
		return entity.hurt(KANTROFFARI_ATTACK, (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
	}

}
