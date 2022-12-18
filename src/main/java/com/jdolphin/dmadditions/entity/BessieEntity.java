package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;


public class BessieEntity extends AnimalEntity {

	public BessieEntity(EntityType<? extends AnimalEntity> p_i48568_1_, World p_i48568_2_) {
		super(p_i48568_1_, p_i48568_2_);
		this.maxUpStep = 1.0F;
	}

	protected void registerGoals() {
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 1.0).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 2.0);
	}

	public boolean isSaddled() {
		return true;
	}

	public boolean isPushable() {
		return !this.isVehicle();
	}

	public boolean canEatGrass() {
		return false;
	}

	public boolean isTamed() {
		return true;
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}

	public boolean canBeControlledByRider() {
		return this.getControllingPassenger() instanceof LivingEntity;
	}

	@Override
	public boolean shouldRiderSit() {
		return super.shouldRiderSit();
	}

	public boolean canMate(AnimalEntity p_70878_1_) {
		return false;
	}

	protected boolean canParent() {
		return false;
	}

	@Nullable
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	public void positionRider(Entity p_184232_1_) {
		super.positionRider(p_184232_1_);
		if (p_184232_1_ instanceof MobEntity) {
			MobEntity mobentity = (MobEntity) p_184232_1_;
			this.yBodyRot = mobentity.yBodyRot;
		}

		float f3 = MathHelper.sin(this.yBodyRot * ((float) Math.PI / 180F));
		float f = MathHelper.cos(this.yBodyRot * ((float) Math.PI / 180F));
		p_184232_1_.setPos(this.getX() + (double) f3, this.getY() + this.getPassengersRidingOffset() + p_184232_1_.getMyRidingOffset(), this.getZ() - (double) (f));
		if (p_184232_1_ instanceof LivingEntity) {
			((LivingEntity) p_184232_1_).yBodyRot = this.yBodyRot;
		}

	}

	public void travel(Vector3d p_213352_1_) {
		if (this.isAlive()) {
			LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();

			if (livingentity == null) return;

			this.yRot = livingentity.yRot;
			this.yRotO = this.yRot;
			this.xRot = livingentity.xRot * 0.5F;
			this.setRot(this.yRot, this.xRot);
			this.yBodyRot = this.yRot;
			this.yHeadRot = this.yBodyRot;
			float f = livingentity.xxa * 0.5F;
			float f1 = livingentity.zza;
			f = 0.0F;
			f1 = 0.0F;
			this.flyingSpeed = 0.02F;
			super.travel(p_213352_1_);
		}
	}

	private Vector3d getDismountLocationInDirection(Vector3d vector, LivingEntity entityLiving) {
		double d0 = this.getX() + vector.x;
		double d1 = this.getBoundingBox().minY;
		double d2 = this.getZ() + vector.z;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for (Pose pose : entityLiving.getDismountPoses()) {
			blockpos$mutable.set(d0, d1, d2);
			double d3 = this.getBoundingBox().maxY + 0.75D;

			while (true) {
				double d4 = this.level.getBlockFloorHeight(blockpos$mutable);
				if ((double) blockpos$mutable.getY() + d4 > d3) {
					break;
				}

				if (TransportationHelper.isBlockFloorValid(d4)) {
					AxisAlignedBB axisalignedbb = entityLiving.getLocalBoundsForPose(pose);
					Vector3d vector3d = new Vector3d(d0, (double) blockpos$mutable.getY() + d4, d2);
					if (TransportationHelper.canDismountTo(this.level, entityLiving, axisalignedbb.move(vector3d))) {
						entityLiving.setPose(pose);
						return vector3d;
					}
				}

				blockpos$mutable.move(Direction.UP);
				if (!((double) blockpos$mutable.getY() < d3)) {
					break;
				}
			}
		}

		return null;
	}

	protected void doPlayerRide(PlayerEntity p_110237_1_) {
		if (!this.level.isClientSide) {
			p_110237_1_.yRot = this.yRot;
			p_110237_1_.xRot = this.xRot;
			p_110237_1_.startRiding(this);
		}
	}

	@Override
	public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		doPlayerRide(p_230254_1_);

		return super.mobInteract(p_230254_1_, p_230254_2_);
	}

	public Vector3d getDismountLocationForPassenger(LivingEntity p_230268_1_) {
		Vector3d vector3d = getCollisionHorizontalEscapeVector(this.getBbWidth(), p_230268_1_.getBbWidth(), this.yRot + (p_230268_1_.getMainArm() == HandSide.RIGHT ? 90.0F : -90.0F));
		Vector3d vector3d1 = this.getDismountLocationInDirection(vector3d, p_230268_1_);
		if (vector3d1 != null) {
			return vector3d1;
		} else {
			Vector3d vector3d2 = getCollisionHorizontalEscapeVector(this.getBbWidth(), p_230268_1_.getBbWidth(), this.yRot + (p_230268_1_.getMainArm() == HandSide.LEFT ? 90.0F : -90.0F));
			Vector3d vector3d3 = this.getDismountLocationInDirection(vector3d2, p_230268_1_);
			return vector3d3 != null ? vector3d3 : this.position();
		}
	}
}
