package com.jdolphin.dmadditions.entity.ai.goal;

import com.swdteam.common.item.GunItem;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;

import java.util.EnumSet;

public class RangedLasergunAttackGoal<T extends MonsterEntity & IRangedAttackMob> extends RangedBowAttackGoal<T> {
	protected final T mob;
	protected final double speedModifier;
	protected int attackIntervalMin;
	protected final float attackRadiusSqr;
	protected int attackTime = -1;
	protected int seeTime;
	protected boolean strafingClockwise;
	protected boolean strafingBackwards;
	protected int strafingTime = -1;

	public RangedLasergunAttackGoal(T p_i47515_1_, double p_i47515_2_, int p_i47515_4_, float p_i47515_5_) {
		super(p_i47515_1_, p_i47515_2_, p_i47515_4_, p_i47515_5_);

		this.mob = p_i47515_1_;
		this.speedModifier = p_i47515_2_;
		this.attackIntervalMin = p_i47515_4_;
		this.attackRadiusSqr = p_i47515_5_ * p_i47515_5_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public void setMinAttackInterval(int p_189428_1_) {
		this.attackIntervalMin = p_189428_1_;
	}

	@Override
	protected boolean isHoldingBow() {
		return this.mob.isHolding(item -> item instanceof GunItem);
	}

	@Override
	public void tick() {
		LivingEntity livingentity = this.mob.getTarget();
		if (livingentity != null) {
			double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			boolean flag = this.mob.getSensing().canSee(livingentity);
			boolean flag1 = this.seeTime > 0;
			if (flag != flag1) {
				this.seeTime = 0;
			}

			if (flag) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}

			if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 20) {
				this.mob.getNavigation().stop();
				++this.strafingTime;
			} else {
				this.mob.getNavigation().moveTo(livingentity, this.speedModifier);
				this.strafingTime = -1;
			}

			if (this.strafingTime >= 20) {
				if ((double)this.mob.getRandom().nextFloat() < 0.3D) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if ((double)this.mob.getRandom().nextFloat() < 0.3D) {
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (d0 > (double)(this.attackRadiusSqr * 0.75F)) {
					this.strafingBackwards = false;
				} else if (d0 < (double)(this.attackRadiusSqr * 0.25F)) {
					this.strafingBackwards = true;
				}

				this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				this.mob.lookAt(livingentity, 30.0F, 30.0F);
			} else {
				this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			}

			if (this.mob.isUsingItem()) {
				if (!flag && this.seeTime < -60) {
					this.mob.stopUsingItem();
				} else if (flag) {
					int i = this.mob.getTicksUsingItem();
					Item item = this.mob.getMainHandItem().getItem();
					if (i >= ((GunItem)item).requiredChargeTime) {
						this.mob.stopUsingItem();
						this.mob.performRangedAttack(livingentity, BowItem.getPowerForTime(i));
						this.attackTime = this.attackIntervalMin;
					}
				}
			} else if (--this.attackTime <= 0 && this.seeTime >= -60) {
				this.mob.startUsingItem(ProjectileHelper.getWeaponHoldingHand(this.mob, item -> item instanceof GunItem));
			}

		}
	}
}
