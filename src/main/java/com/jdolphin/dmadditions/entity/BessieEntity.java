package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMASoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;


public class BessieEntity extends AnimalEntity implements IJumpingMount {
	protected float jumpPower;
	private final boolean allowStandSliding = true;
	protected boolean horseJumping;
	private float deltaRotation;
	private int engineRevTime = 0;
	boolean deathExplosion = false;
	public boolean driving = false;

	public BessieEntity(EntityType<? extends AnimalEntity> p_i48568_1_, World p_i48568_2_) {
		super(p_i48568_1_, p_i48568_2_);
		this.maxUpStep = 1.0F;
	}

	protected void registerGoals() {
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.4).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 2.0);
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

	public boolean canBeSteered() {
		return this.getControllingPassenger() instanceof LivingEntity;
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
		if (this.hasPassenger(p_184232_1_)) {
			float f = 0.0F;
			float f1 = (float) ((!this.isAlive() ? (double) 0.01F : this.getPassengersRidingOffset()) + p_184232_1_.getMyRidingOffset());
			if (this.getPassengers().size() > 1) {
				int i = this.getPassengers().indexOf(p_184232_1_);
				if (i == 0) {
					f = 0.2F;
				} else {
					f = -0.6F;
				}

				if (p_184232_1_ instanceof AnimalEntity) {
					f = (float) ((double) f + 0.2D);
				}
			}

			Vector3d vector3d = (new Vector3d(f, 0.0D, 0.0D)).yRot(-this.yRot * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
			p_184232_1_.setPos(this.getX() + vector3d.x, this.getY() + (double) f1, this.getZ() + vector3d.z);
			p_184232_1_.yRot += this.deltaRotation;
			p_184232_1_.setYHeadRot(p_184232_1_.getYHeadRot() + this.deltaRotation);
//			this.clampRotation(p_184232_1_);
			if (p_184232_1_ instanceof AnimalEntity && this.getPassengers().size() > 1) {
				int j = p_184232_1_.getId() % 2 == 0 ? 90 : 270;
				p_184232_1_.setYBodyRot(((AnimalEntity) p_184232_1_).yBodyRot + (float) j);
				p_184232_1_.setYHeadRot(p_184232_1_.getYHeadRot() + (float) j);
			}

		}
	}

	@Override
	public double getPassengersRidingOffset() {
		return -0.02;
	}

	protected boolean canAddPassenger(Entity p_184219_1_) {
		return this.getPassengers().size() < 2 && !this.isEyeInFluid(FluidTags.WATER);
	}

	public void travel(Vector3d p_213352_1_) {
		if (this.isAlive()) {
			if (this.isVehicle() && this.canBeSteered() && this.isSaddled()) {
				LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
				this.yRot = livingentity.yRot;
				this.yRotO = this.yRot;
				this.xRot = livingentity.xRot * 0.5F;
				this.setRot(this.yRot, this.xRot);
				this.yBodyRot = this.yRot;
				this.yHeadRot = this.yBodyRot;
				float f = livingentity.xxa * 0.5F;
				float f1 = livingentity.zza;
//				if (livingentity instanceof PlayerEntity && !ItemUtils.playerHasItem((PlayerEntity)livingentity, (Item)WOTWItems.FUEL.get(), false) && !((PlayerEntity)livingentity).isCreative()) {
//					f = 0.0F;
//					f1 = 0.0F;
//				}

				if (f1 > 0.0F) {
					this.driving = true;
//					if (MathUtils.getRANDOM().nextInt(100) == 1 && livingentity instanceof PlayerEntity) {
//						PlayerEntity pl = (PlayerEntity)livingentity;
//						if (ItemUtils.playerHasItem(pl, (Item)WOTWItems.FUEL.get(), false) && !pl.isCreative()) {
//							this.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 1.0F, (float)MathUtils.randomDouble(0.6D, 1.0D));
//							if (!this.world.isRemote) {
//								ItemUtils.consumeItem(pl, (Item)WOTWItems.FUEL.get());
//							}
//						}
//					}

					++this.engineRevTime;
					if (this.engineRevTime > 10) {
						this.playSound(DMASoundEvents.BESSIE.get(), 1.0F, 0.4F + f1);
						this.engineRevTime = 0;
					}
				} else {
					this.driving = false;
				}

				if (f1 <= 0.0F) {
					f1 *= 0.25F;
				}

				if (f1 > 0.5F) {
					f1 = 0.5F;
				}

				if (this.onGround && this.jumpPower == 0.0F && this.isRearing() && !this.allowStandSliding) {
					f = 0.0F;
					f1 = 0.0F;
				}

				double d3;
				double d2;
				if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround) {
					d2 = 1.0D * (double) this.jumpPower * (double) this.getBlockJumpFactor();
					if (this.hasEffect(Effects.JUMP)) {
						d3 = d2 + (double) ((float) (this.getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F);
					} else {
						d3 = d2;
					}

					Vector3d vec3d = this.getDeltaMovement();
					this.setDeltaMovement(vec3d.x, d3, vec3d.z);
					this.setHorseJumping(true);
					this.hasImpulse = true;
					if (f1 > 0.0F) {
						float f2 = MathHelper.sin(this.yRot * 0.017453292F);
						float f3 = MathHelper.cos(this.yRot * 0.017453292F);
						this.setDeltaMovement(this.getDeltaMovement().add(-0.4F * f2 * this.jumpPower, 0.0D, 0.4F * f3 * this.jumpPower));
					}

					this.jumpPower = 0.0F;
				}

				this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
					super.travel(new Vector3d(f, p_213352_1_.y, f1));
				} else if (livingentity instanceof PlayerEntity) {
					this.setDeltaMovement(Vector3d.ZERO);
				}

				if (this.onGround) {
					this.jumpPower = 0.0F;
					this.setHorseJumping(false);
				}

				this.animationSpeedOld = this.animationSpeed;
				d2 = this.getX() - this.xo;
				d3 = this.getZ() - this.zo;
				float f4 = MathHelper.sqrt(d2 * d2 + d3 * d3) * 4.0F;
				if (f4 > 1.0F) {
					f4 = 1.0F;
				}

				this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
				this.animationPosition += this.animationSpeed;
			} else {
				this.flyingSpeed = 0.02F;
				super.travel(p_213352_1_);
			}
		}
	}

	public void setHorseJumping(boolean jumping) {
		this.horseJumping = jumping;
	}

	public boolean isHorseJumping() {
		return this.horseJumping;
	}

	public boolean isRearing() {
		return false;
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

	@Override
	public void onPlayerJump(int p_110206_1_) {
	}

	@Override
	public boolean canJump() {
		return true;
	}

	@Override
	public void handleStartJump(int p_184775_1_) {
	}

	@Override
	public void handleStopJump() {
	}
}
