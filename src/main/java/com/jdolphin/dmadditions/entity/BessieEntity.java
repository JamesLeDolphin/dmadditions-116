package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMASoundEvents;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class BessieEntity extends Entity {
	private static final DataParameter<Integer> DATA_ID_HURT = EntityDataManager.defineId(BessieEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DATA_ID_HURTDIR = EntityDataManager.defineId(BessieEntity.class, DataSerializers.INT);
	private static final DataParameter<Float> DATA_ID_DAMAGE = EntityDataManager.defineId(BessieEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> DATA_ID_TYPE = EntityDataManager.defineId(BessieEntity.class, DataSerializers.INT);
	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYRot;
	private double lerpXRot;
	private boolean inputLeft;
	private boolean inputRight;
	private boolean inputUp;
	private boolean inputDown;
	private float deltaRotation;
	public BessieEntity(EntityType<?> entityType, World wolrdIn) {
		super(entityType, wolrdIn);
	}
	protected void playDriveSound(SoundType soundType) {
		super.playSound(DMASoundEvents.BESSIE.get(), soundType.getVolume() * 0.15F, soundType.getPitch());
	}
	public ActionResultType interact(PlayerEntity p_184230_1_, Hand p_184230_2_) {
		return ActionResultType.PASS;
	}
	private void controlBoat() {
		if (this.isVehicle()) {
			float f = 0.0F;
			if (this.inputLeft) {
				--this.deltaRotation;
			}

			if (this.inputRight) {
				++this.deltaRotation;
			}

			if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
				f += 0.005F;
			}

			this.yRot += this.deltaRotation;
			if (this.inputUp) {
				f += 0.04F;
			}

			if (this.inputDown) {
				f -= 0.005F;
			}

			this.setDeltaMovement(this.getDeltaMovement().add((double)(MathHelper.sin(-this.yRot * ((float)Math.PI / 180F)) * f), 0.0D, (double)(MathHelper.cos(this.yRot * ((float)Math.PI / 180F)) * f)));
			}
	}

	public void positionRider(Entity entity) {
		if (this.hasPassenger(entity)) {
			float f = 0.0F;
			float f1 = (float)((this.removed ? (double)0.01F : this.getPassengersRidingOffset()) + entity.getMyRidingOffset());
			if (this.getPassengers().size() > 1) {
				int i = this.getPassengers().indexOf(entity);
				if (i == 0) {
					f = 0.2F;
				} else {
					f = -0.6F;
				}

				if (entity instanceof AnimalEntity) {
					f = (float)((double)f + 0.2D);
				}
			}

			Vector3d vector3d = (new Vector3d((double)f, 0.0D, 0.0D)).yRot(-this.yRot * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
			entity.setPos(this.getX() + vector3d.x, this.getY() + (double)f1, this.getZ() + vector3d.z);
			entity.yRot += this.deltaRotation;
			entity.setYHeadRot(entity.getYHeadRot() + this.deltaRotation);
			this.clampRotation(entity);
			if (entity instanceof AnimalEntity && this.getPassengers().size() > 1) {
				int j = entity.getId() % 2 == 0 ? 90 : 270;
				entity.setYBodyRot(((AnimalEntity)entity).yBodyRot + (float)j);
				entity.setYHeadRot(entity.getYHeadRot() + (float)j);
			}

		}
	}

	public Vector3d getDismountLocationForPassenger(LivingEntity livingEntity) {
		Vector3d vector3d = getCollisionHorizontalEscapeVector((double)(this.getBbWidth() * MathHelper.SQRT_OF_TWO), (double)livingEntity.getBbWidth(), this.yRot);
		double d0 = this.getX() + vector3d.x;
		double d1 = this.getZ() + vector3d.z;
		BlockPos blockpos = new BlockPos(d0, this.getBoundingBox().maxY, d1);
		BlockPos blockpos1 = blockpos.below();
		if (!this.level.isWaterAt(blockpos1)) {
			double d2 = (double)blockpos.getY() + this.level.getBlockFloorHeight(blockpos);
			double d3 = (double)blockpos.getY() + this.level.getBlockFloorHeight(blockpos1);

			for(Pose pose : livingEntity.getDismountPoses()) {
				Vector3d vector3d1 = TransportationHelper.findDismountLocation(this.level, d0, d2, d1, livingEntity, pose);
				if (vector3d1 != null) {
					livingEntity.setPose(pose);
					return vector3d1;
				}

				Vector3d vector3d2 = TransportationHelper.findDismountLocation(this.level, d0, d3, d1, livingEntity, pose);
				if (vector3d2 != null) {
					livingEntity.setPose(pose);
					return vector3d2;
				}
			}
		}

		return super.getDismountLocationForPassenger(livingEntity);
	}

	protected void clampRotation(Entity entity) {
		entity.setYBodyRot(this.yRot);
		float f = MathHelper.wrapDegrees(entity.yRot - this.yRot);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entity.yRotO += f1 - f;
		entity.yRot += f1 - f;
		entity.setYHeadRot(entity.yRot);
	}

	@OnlyIn(Dist.CLIENT)
	public void onPassengerTurned(Entity entity) {
		this.clampRotation(entity);
	}
	public boolean canBeCollidedWith() {
		return true;
	}
	public boolean isPushable() {
		return true;
	}

	public double getPassengersRidingOffset() {
		return -0.1D;
	}

	public boolean hurt(DamageSource dmgSource, float float1) {
		return !this.isInvulnerableTo(dmgSource);
		}

	protected Vector3d getRelativePortalPosition(Direction.Axis axis, TeleportationRepositioner.Result result) {
		return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(axis, result));
	}

	public boolean canCollideWith(Entity entity) {
		return canVehicleCollide(this, entity);
	}

	public static boolean canVehicleCollide(Entity entity, Entity entity2) {
		return (entity2.canBeCollidedWith() || entity2.isPushable()) && !entity.isPassengerOfSameVehicle(entity2);
	}
	@Override
	protected void defineSynchedData() {
			this.entityData.define(DATA_ID_HURT, 0);
			this.entityData.define(DATA_ID_HURTDIR, 1);
			this.entityData.define(DATA_ID_DAMAGE, 0.0F);
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT nbt) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {

	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return new SSpawnObjectPacket(this);
	}
	public void setHurtDir(int num) {
		this.entityData.set(DATA_ID_HURTDIR, num);
	}

	public int getHurtDir() {
		return this.entityData.get(DATA_ID_HURTDIR);
	}
	protected boolean canAddPassenger(Entity entity) {
		return this.getPassengers().size() < 2;
	}
	@Override
	protected void addPassenger(Entity passenger) {
		super.addPassenger(passenger);
		if (this.isControlledByLocalInstance() && this.lerpSteps > 0) {
			this.lerpSteps = 0;
			this.absMoveTo(this.lerpX, this.lerpY, this.lerpZ, (float)this.lerpYRot, (float)this.lerpXRot);
		}
	}

	@Nullable
	public Entity getControllingPassenger() {
		List<Entity> list = this.getPassengers();
		return list.isEmpty() ? null : list.get(0);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateHurt() {
		this.setHurtDir(-this.getHurtDir());
		this.setHurtTime(10);
		this.setDamage(this.getDamage() * 11.0F);
	}
	public void setDamage(float float2) {
		this.entityData.set(DATA_ID_DAMAGE, float2);
	}

	public float getDamage() {
		return this.entityData.get(DATA_ID_DAMAGE);
	}

	public void setHurtTime(int integer) {
		this.entityData.set(DATA_ID_HURT, integer);
	}

	public int getHurtTime() {
		return this.entityData.get(DATA_ID_HURT);
	}
}
