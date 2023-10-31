package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static net.minecraft.entity.passive.WolfEntity.PREY_SELECTOR;

public class FlyingSharkEntity extends TameableEntity implements IAngerable, IRideable {
	// DataParameter for flying state
	public static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);

	// Add a boolean to represent tamed state
	public static final DataParameter<Boolean> TAMED = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);

	public static final DataParameter<Boolean> SITTING = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);

	public static final DataParameter<Optional<UUID>> OWNER = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.OPTIONAL_UUID);

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(FLYING, true);
		this.entityData.define(TAMED, false);
		this.entityData.define(SITTING, false);
		this.entityData.define(OWNER, Optional.empty());
	}

	protected void registerGoals() {
/*
		this.goalSelector.addGoal(0, new BreedGoal(this, 1.0D));
*/
		this.goalSelector.addGoal(1, new TemptGoal(this, 1.25D, Ingredient.of(Items.COD), false));
		this.goalSelector.addGoal(2, new SwimGoal(this));
		this.goalSelector.addGoal(3, new FlyingSharkEntity.RandomFlyGoal(this));
		this.goalSelector.addGoal(4, new FlyingSharkEntity.LookAroundGoal(this));
		this.targetSelector.addGoal(5, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(6, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(7, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.goalSelector.addGoal(9, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		this.targetSelector.addGoal(10, new NonTamedTargetGoal<>(this,  AnimalEntity.class, false, PREY_SELECTOR));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.MAX_HEALTH, 20.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0)
			.add(Attributes.FOLLOW_RANGE, 20.0)
			.add(Attributes.FLYING_SPEED, 1);
	}

	public FlyingSharkEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super((EntityType<? extends TameableEntity>) entityType, world);

		this.moveControl = new FlyingMovementController(this, 1, true);
	}

	@Override
	protected int calculateFallDamage(float p_225508_1_, float p_225508_2_) {
		return 0;
	}

	// Getter and Setter for flying state
	public boolean isFlying() {
		return this.entityData.get(FLYING);
	}

	public void setFlying(boolean flying) {
		this.entityData.set(FLYING, flying);
	}

	// Getter and Setter for tamed state
	public boolean isTamed() {
		return this.entityData.get(TAMED);
	}

	public boolean isSitting() {
		return this.entityData.get(SITTING);
	}

	public void setSitting(boolean b) {
		this.entityData.set(SITTING, b);
	}

	public void setTamed(boolean tamed) {
		this.entityData.set(TAMED, tamed);
	}

	// Attempt to tame the entity when fed with fish
	public boolean attemptTame(Hand hand, ItemStack itemStack) {
		if (!this.isTamed() && itemStack.getItem() == Items.COD) {
			if (!this.level.isClientSide) {
				// Calculate a chance for taming (you can adjust the chance as needed)
				double tamingChance = 0.25; // 25% chance for taming, adjust as needed

				if (this.random.nextDouble() <= tamingChance) {
					// Successfully tamed
					this.setTamed(true);

					// Remove one fish from the player's hand (you may want to adjust this part)
					if (hand == Hand.MAIN_HAND) {
						itemStack.shrink(1);
					} else if (hand == Hand.OFF_HAND) {
						PlayerEntity player = this.level.getNearestPlayer(this, 4.0);
						if (player != null) {
							player.getItemInHand(hand).shrink(1);
						}
					}

					return true;
				}
			}

			return false;
		}

		return false;
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.getItem() == Items.COD;
	}

	public Serializable processInteract(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getItemInHand(hand);

		// Attempt to tame the entity when right-clicked with fish
		if (this.attemptTame(hand, itemStack)) {
			return true;
		}

		// Add other interactions here if needed

		return super.interact(player, hand);
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return 0;
	}

	@Override
	public void setRemainingPersistentAngerTime(int p_230260_1_) {

	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return null;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {

	}

	@Override
	public void startPersistentAngerTimer() {

	}

	@Override
	public boolean boost() {
		return false;
	}

	@Override
	public void travelWithInput(Vector3d p_230267_1_) {

	}

	@Override
	public float getSteeringSpeed() {
		return 0;
	}

	static class LookAroundGoal extends Goal {
		private final FlyingSharkEntity shark;

		public LookAroundGoal(FlyingSharkEntity p_i45839_1_) {
			this.shark = p_i45839_1_;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public void tick() {
			if (this.shark.getTarget() == null) {
				Vector3d vector3d = this.shark.getDeltaMovement();
				this.shark.yRot = -((float) MathHelper.atan2(vector3d.x, vector3d.z)) * (180F / (float)Math.PI);
				this.shark.yBodyRot = this.shark.yRot;
			} else {
				LivingEntity livingentity = this.shark.getTarget();
				double d0 = 64.0D;
				if (livingentity.distanceToSqr(this.shark) < 4096.0D) {
					double d1 = livingentity.getX() - this.shark.getX();
					double d2 = livingentity.getZ() - this.shark.getZ();
					this.shark.yRot = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
					this.shark.yBodyRot = this.shark.yRot;
				}
			}

		}
	}

	static class RandomFlyGoal extends Goal {
		private final FlyingSharkEntity shark;

		public RandomFlyGoal(FlyingSharkEntity p_i45836_1_) {
			this.shark = p_i45836_1_;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			MovementController movementcontroller = this.shark.getMoveControl();
			if (!movementcontroller.hasWanted()) {
				return true;
			} else {
				double d0 = movementcontroller.getWantedX() - this.shark.getX();
				double d1 = movementcontroller.getWantedY() - this.shark.getY();
				double d2 = movementcontroller.getWantedZ() - this.shark.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		public boolean canContinueToUse() {
			return false;
		}

		public void start() {
			Random random = this.shark.getRandom();
			double d0 = this.shark.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.shark.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d2 = this.shark.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.shark.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
		}
	}

	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
		if (this.isInvulnerableTo(p_70097_1_)) {
			return false;
		} else {
			Entity entity = p_70097_1_.getEntity();
			this.setOrderedToSit(false);
			if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
				p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
			}

			return super.hurt(p_70097_1_, p_70097_2_);
		}
	}

	public boolean doHurtTarget(Entity p_70652_1_) {
		boolean flag = p_70652_1_.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
		if (flag) {
			this.doEnchantDamageEffects(this, p_70652_1_);
		}

		return flag;
	}

	public void setTame(boolean p_70903_1_) {
		super.setTame(p_70903_1_);
		if (p_70903_1_) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
			this.setHealth(20.0F);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16.0D);
		}

		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
	}

	public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		Item item = itemstack.getItem();
		if (this.level.isClientSide) {
			boolean flag = this.isOwnedBy(p_230254_1_) || this.isTame() || item == Items.BONE && !this.isTame() && !this.isAngry();
			return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
		} else {
			if (this.isTame()) {
				if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
					if (!p_230254_1_.abilities.instabuild) {
						itemstack.shrink(1);
					}

					this.heal((float) item.getFoodProperties().getNutrition());
					return ActionResultType.SUCCESS;
				}

				if (!(item instanceof DyeItem)) {
					ActionResultType actionresulttype = super.mobInteract(p_230254_1_, p_230254_2_);
					if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(p_230254_1_)) {
						this.setOrderedToSit(!this.isOrderedToSit());
						this.jumping = false;
						this.navigation.stop();
						this.setTarget((LivingEntity) null);
						return ActionResultType.SUCCESS;
					}

					return actionresulttype;
				}

				if (item == Items.COD && !this.isAngry()) {
					if (!p_230254_1_.abilities.instabuild) {
						itemstack.shrink(1);
					}

					if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
						this.tame(p_230254_1_);
						this.navigation.stop();
						this.setTarget((LivingEntity) null);
						this.setOrderedToSit(true);
						this.level.broadcastEntityEvent(this, (byte) 7);
					} else {
						this.level.broadcastEntityEvent(this, (byte) 6);
					}

					return ActionResultType.SUCCESS;
				}

				return super.mobInteract(p_230254_1_, p_230254_2_);
			}
		}
		return ActionResultType.sidedSuccess(false);
	}
}