package com.jdolphin.dmadditions.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import com.jdolphin.dmadditions.entity.ai.goal.*;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;

public class FlyingSharkEntity extends TameableEntity implements IAngerable, IRideable {
	// DataParameter for flying state
	public static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);

	// Add a boolean to represent tamed state
	public static final DataParameter<Boolean> TAMED = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(FLYING, true);
		this.entityData.define(TAMED, false);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new TemptGoal(this, 1.25D, Ingredient.of(Items.COD), false));
		this.goalSelector.addGoal(2, new SwimGoal(this));
		this.goalSelector.addGoal(3, new FlyingSharkEntity.RandomFlyGoal(this));
		this.goalSelector.addGoal(4, new FlyRandomlySharkGoal(this));
		this.targetSelector.addGoal(5, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(6, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(7, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.goalSelector.addGoal(9, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.MAX_HEALTH, 20.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	public FlyingSharkEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super((EntityType<? extends TameableEntity>) entityType, world);

		// Register AI goals
		this.goalSelector.addGoal(6, new FlyRandomlySharkGoal(this));
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
}