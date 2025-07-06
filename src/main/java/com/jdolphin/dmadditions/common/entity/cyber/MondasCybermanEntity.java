package com.jdolphin.dmadditions.common.entity.cyber;

import com.jdolphin.dmadditions.common.init.DMADamageSources;
import com.jdolphin.dmadditions.common.init.DMAEntities;
import com.jdolphin.dmadditions.common.init.DMAItems;
import com.jdolphin.dmadditions.common.init.DMASoundEvents;
import com.jdolphin.dmadditions.common.util.Helper;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Predicate;

public class MondasCybermanEntity extends MonsterEntity {
	private static final DataParameter<Integer> DATA_ID_ATTACK_TARGET = EntityDataManager.defineId(MondasCybermanEntity.class, DataSerializers.INT);
	private LivingEntity clientSideCachedAttackTarget;
	private int clientSideAttackTime;

	public MondasCybermanEntity(EntityType<? extends MondasCybermanEntity> entityType, World world) {
		super(entityType, world);
	}

	public MondasCybermanEntity(World world) {
		super(DMAEntities.MONDAS_CYBERMAN.get(), world);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.ATTACK_DAMAGE, 6.0D)
			.add(Attributes.ARMOR, 3.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.2)
			.add(Attributes.FOLLOW_RANGE, 32)
			.add(Attributes.MAX_HEALTH, 30.0D);
	}

	protected SoundEvent getAmbientSound() {
		return DMASoundEvents.MONDAS_CYBER_AMBIENT.get();
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
	}

	@Override
	public void onAddedToWorld() {
		super.onAddedToWorld();
		if (this.level.getRandom().nextInt(10) == 0) { //We do a funny
			this.setCustomName(new StringTextComponent("Bill Potts"));
		}
	}

	protected void registerGoals() {
		super.registerGoals();
		RandomWalkingGoal randomStrollGoal = new RandomWalkingGoal(this, 1.0D, 80);
		this.goalSelector.addGoal(4, new MondasCybermanEntity.AttackGoal(this));
		this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D, 80));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtGoal(this, CybermanEntity.class, 12.0F, 0.01F));
		this.goalSelector.addGoal(9, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(3, new MoveTowardsTargetGoal(this, 0.9, 32F));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		randomStrollGoal.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, DalekEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
	}


	public void aiStep() {
		if (this.isAlive()) {
			if (this.level.isClientSide) {

				if (this.hasActiveAttackTarget()) {
					if (this.clientSideAttackTime < this.getAttackDuration()) {
						++this.clientSideAttackTime;
						double d = this.distanceToSqr(Objects.requireNonNull(this.getActiveAttackTarget()));
						if (d > 5) {
							this.getNavigation().moveTo(this.getActiveAttackTarget(), this.getMoveControl().getSpeedModifier());
						}
					}

					LivingEntity livingentity = this.getActiveAttackTarget();
					if (livingentity != null) {
						this.getLookControl().setLookAt(livingentity, 90.0F, 90.0F);
						this.getLookControl().tick();
						double d0 = livingentity.getX() - this.getX();
						double d1 = livingentity.getY(0.5) - this.getEyeY();
						double d2 = livingentity.getZ() - this.getZ();
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 = d0 / d3;
						d1 = d1 / d3;
						d2 = d2 / d3;
						double d4 = this.random.nextDouble();

						while (d4 < d3) {
							d4 += this.random.nextDouble() * 1.7D;
							this.level.addParticle(ParticleTypes.FLAME, this.getX() + d0 * d4, this.getEyeY() + 0.5 + d1 * d4, this.getZ() + d2 * d4, 0.0D, 0.0D, 0.0D);
						}
					}
				}
			}

			if (this.hasActiveAttackTarget()) {
				this.yRot = this.yHeadRot;
			}
		}
		super.aiStep();
	}

	static class TargetPredicate implements Predicate<LivingEntity> {
		private final MondasCybermanEntity cyberman;

		public TargetPredicate(MondasCybermanEntity p_i45832_1_) {
			this.cyberman = p_i45832_1_;
		}

		public boolean test(@Nullable LivingEntity entity) {
			return (entity instanceof PlayerEntity || entity instanceof DalekEntity || entity instanceof VillagerEntity) && entity.distanceTo(this.cyberman) <= 5;
		}
	}

	static class AttackGoal extends Goal {
		private final MondasCybermanEntity cyber;
		private int attackTime;

		public AttackGoal(MondasCybermanEntity p_i45833_1_) {
			this.cyber = p_i45833_1_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		public boolean canUse() {
			LivingEntity livingentity = this.cyber.getTarget();
			return livingentity != null && livingentity.isAlive();
		}

		public boolean canContinueToUse() {
			LivingEntity entity = this.cyber.getTarget();
			if (entity != null) {
				double d = this.cyber.distanceTo(entity);
				return super.canContinueToUse() && (d <= 5.0D);
			}
			return false;
		}

		public void start() {
			this.attackTime = -10;
			this.cyber.getLookControl().setLookAt(this.cyber.getTarget(), 90.0F, 90.0F);
		}

		public void stop() {
			this.cyber.setAggressive(false);
			this.cyber.setActiveAttackTarget(0);
			this.cyber.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity target = this.cyber.getTarget();
			this.cyber.getLookControl().setLookAt(target, 90.0F, 90.0F);
			double distance = this.cyber.distanceTo(target);
			if (!this.cyber.canSee(target)) {
				this.cyber.setTarget((LivingEntity) null);
			} else if (distance > 1 && distance <= this.cyber.getAttributeValue(Attributes.FOLLOW_RANGE)) {
				this.cyber.getNavigation().createPath(target, 0);
				++this.attackTime;

				if(distance >= getAttackReachSqr(target)){
					tickRanged();
				} else {
					tickMelee();
				}

				super.tick();
			}
		}

		public void tickMelee() {
			LivingEntity target = this.cyber.getTarget();
			double distance = this.cyber.distanceTo(target);
			double reach = this.getAttackReachSqr(target);

			if (distance <= reach && this.attackTime >= 0) {
				this.attackTime = -10;
				this.cyber.swing(Hand.MAIN_HAND);
				this.cyber.doHurtTarget(target);
			}

		}

		public void tickRanged() {
			LivingEntity target = this.cyber.getTarget();
			if (this.attackTime == 0) {
				Helper.playSound(cyber.level, cyber.blockPosition(), DMASoundEvents.MONDAS_CYBER_LASER_ATTACK.get(),
						SoundCategory.HOSTILE);
				this.cyber.setActiveAttackTarget(this.cyber.getTarget().getId());
			} else if (this.attackTime >= this.cyber.getAttackDuration()) {
				float f = 1.0F;
				if (this.cyber.level.getDifficulty() == Difficulty.HARD) {
					f += 2.0F;
				}

				target.hurt(DMADamageSources.CYBER_BEAM,
						(float) this.cyber.getAttributeValue(Attributes.ATTACK_DAMAGE) + f);
				this.cyber.setTarget((LivingEntity) null);
			}
		}

		protected double getAttackReachSqr(LivingEntity entity) {
			return (double) (this.cyber.getBbWidth() * 2.0F * this.cyber.getBbWidth() * 2.0F + entity.getBbWidth());
		}
	}

	public ItemStack getPickedResult(RayTraceResult target) {
		return new ItemStack(DMAItems.MONDAS_CYBERMAN_SPAWNER.get());
	}

	public int getAttackDuration() {
		return 40;
	}

	private void setActiveAttackTarget(int p_175463_1_) {
		this.entityData.set(DATA_ID_ATTACK_TARGET, p_175463_1_);
	}

	public boolean hasActiveAttackTarget() {
		return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
	}

	@Nullable
	public LivingEntity getActiveAttackTarget() {
		if (!this.hasActiveAttackTarget()) {
			return null;
		} else if (this.level.isClientSide) {
			if (this.clientSideCachedAttackTarget != null) {
				return this.clientSideCachedAttackTarget;
			} else {
				Entity entity = this.level.getEntity(this.entityData.get(DATA_ID_ATTACK_TARGET));
				if (entity instanceof LivingEntity) {
					this.clientSideCachedAttackTarget = (LivingEntity) entity;
					return this.clientSideCachedAttackTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
		super.onSyncedDataUpdated(p_184206_1_);
		if (DATA_ID_ATTACK_TARGET.equals(p_184206_1_)) {
			this.clientSideAttackTime = 0;
			this.clientSideCachedAttackTarget = null;
		}

	}
}
