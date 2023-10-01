//package com.jdolphin.dmadditions.entity;
//
//import net.minecraft.entity.*;
//import net.minecraft.entity.ai.attributes.AttributeModifierMap;
//import net.minecraft.entity.ai.attributes.Attributes;
//import net.minecraft.entity.ai.controller.MovementController;
//import net.minecraft.entity.ai.goal.*;
//import net.minecraft.entity.passive.AnimalEntity;
//import net.minecraft.entity.passive.TameableEntity;
//import net.minecraft.entity.passive.TurtleEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.projectile.AbstractArrowEntity;
//import net.minecraft.item.*;
//import net.minecraft.item.crafting.Ingredient;
//import net.minecraft.network.datasync.DataParameter;
//import net.minecraft.network.datasync.DataSerializers;
//import net.minecraft.network.datasync.EntityDataManager;
//import net.minecraft.util.ActionResultType;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.Hand;
//import net.minecraft.util.math.vector.Vector3d;
//import net.minecraft.world.World;
//import com.jdolphin.dmadditions.entity.ai.goal.*;
//import net.minecraft.world.server.ServerWorld;
//import org.jetbrains.annotations.Nullable;
//
//import java.io.Serializable;
//import java.util.EnumSet;
//import java.util.Random;
//import java.util.UUID;
//
//import static net.minecraft.entity.passive.WolfEntity.PREY_SELECTOR;
//
//public class FlyingSharkEntity extends TameableEntity implements IAngerable, IRideable {
//	// DataParameter for flying state
//	public static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);
//
//	// Add a boolean to represent tamed state
//	public static final DataParameter<Boolean> TAMED = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);
//
//	protected void defineSynchedData() {
//		super.defineSynchedData();
//		this.entityData.define(FLYING, true);
//		this.entityData.define(TAMED, false);
//	}
//
//	protected void registerGoals() {
//		this.goalSelector.addGoal(0, new BreedGoal(this, 1.0D));
//		this.goalSelector.addGoal(1, new TemptGoal(this, 1.25D, Ingredient.of(Items.COD), false));
//		this.goalSelector.addGoal(2, new SwimGoal(this));
//		this.goalSelector.addGoal(3, new FlyingSharkEntity.RandomFlyGoal(this));
//		this.goalSelector.addGoal(4, new FlyRandomlySharkGoal(this));
//		this.targetSelector.addGoal(5, new OwnerHurtByTargetGoal(this));
//		this.targetSelector.addGoal(6, new OwnerHurtTargetGoal(this));
//		this.targetSelector.addGoal(7, (new HurtByTargetGoal(this)).setAlertOthers());
//		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
//		this.goalSelector.addGoal(9, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
//		this.targetSelector.addGoal(10, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_SELECTOR));
//		this.targetSelector.addGoal(11, new NonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_ON_LAND_SELECTOR));
//	}
//
//	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
//		return MobEntity.createMobAttributes()
//			.add(Attributes.MOVEMENT_SPEED, 0.3)
//			.add(Attributes.MAX_HEALTH, 20.0)
//			.add(Attributes.ATTACK_DAMAGE, 2.0)
//			.add(Attributes.FOLLOW_RANGE, 20.0);
//	}
//
//	public FlyingSharkEntity(EntityType<? extends AnimalEntity> entityType, World world) {
//		super((EntityType<? extends TameableEntity>) entityType, world);
//
//		// Register AI goals
//		this.goalSelector.addGoal(6, new FlyRandomlySharkGoal(this));
//	}
//
//	// Getter and Setter for flying state
//	public boolean isFlying() {
//		return this.entityData.get(FLYING);
//	}
//
//	public void setFlying(boolean flying) {
//		this.entityData.set(FLYING, flying);
//	}
//
//	// Getter and Setter for tamed state
//	public boolean isTamed() {
//		return this.entityData.get(TAMED);
//	}
//
//	public void setTamed(boolean tamed) {
//		this.entityData.set(TAMED, tamed);
//	}
//
//	// Attempt to tame the entity when fed with fish
//	public boolean attemptTame(Hand hand, ItemStack itemStack) {
//		if (!this.isTamed() && itemStack.getItem() == Items.COD) {
//			if (!this.level.isClientSide) {
//				// Calculate a chance for taming (you can adjust the chance as needed)
//				double tamingChance = 0.25; // 25% chance for taming, adjust as needed
//
//				if (this.random.nextDouble() <= tamingChance) {
//					// Successfully tamed
//					this.setTamed(true);
//
//					// Remove one fish from the player's hand (you may want to adjust this part)
//					if (hand == Hand.MAIN_HAND) {
//						itemStack.shrink(1);
//					} else if (hand == Hand.OFF_HAND) {
//						PlayerEntity player = this.level.getNearestPlayer(this, 4.0);
//						if (player != null) {
//							player.getItemInHand(hand).shrink(1);
//						}
//					}
//
//					return true;
//				}
//			}
//
//			return false;
//		}
//
//		return false;
//	}
//
//	@Override
//	public boolean isFood(ItemStack stack) {
//		return stack.getItem() == Items.COD;
//	}
//
//	public Serializable processInteract(PlayerEntity player, Hand hand) {
//		ItemStack itemStack = player.getItemInHand(hand);
//
//		// Attempt to tame the entity when right-clicked with fish
//		if (this.attemptTame(hand, itemStack)) {
//			return true;
//		}
//
//		// Add other interactions here if needed
//
//		return super.interact(player, hand);
//	}
//
//	@Nullable
//	@Override
//	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
//		return null;
//	}
//
//	@Override
//	public int getRemainingPersistentAngerTime() {
//		return 0;
//	}
//
//	@Override
//	public void setRemainingPersistentAngerTime(int p_230260_1_) {
//
//	}
//
//	@Nullable
//	@Override
//	public UUID getPersistentAngerTarget() {
//		return null;
//	}
//
//	@Override
//	public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
//
//	}
//
//	@Override
//	public void startPersistentAngerTimer() {
//
//	}
//
//	@Override
//	public boolean boost() {
//		return false;
//	}
//
//	@Override
//	public void travelWithInput(Vector3d p_230267_1_) {
//
//	}
//
//	@Override
//	public float getSteeringSpeed() {
//		return 0;
//	}
//
//	static class RandomFlyGoal extends Goal {
//		private final FlyingSharkEntity shark;
//
//		public RandomFlyGoal(FlyingSharkEntity p_i45836_1_) {
//			this.shark = p_i45836_1_;
//			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
//		}
//
//		public boolean canUse() {
//			MovementController movementcontroller = this.shark.getMoveControl();
//			if (!movementcontroller.hasWanted()) {
//				return true;
//			} else {
//				double d0 = movementcontroller.getWantedX() - this.shark.getX();
//				double d1 = movementcontroller.getWantedY() - this.shark.getY();
//				double d2 = movementcontroller.getWantedZ() - this.shark.getZ();
//				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
//				return d3 < 1.0D || d3 > 3600.0D;
//			}
//		}
//
//		public boolean canContinueToUse() {
//			return false;
//		}
//
//		public void start() {
//			Random random = this.shark.getRandom();
//			double d0 = this.shark.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
//			double d1 = this.shark.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
//			double d2 = this.shark.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
//			this.shark.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
//		}
//	}
//
//	public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
//		if (this.isInvulnerableTo(p_70097_1_)) {
//			return false;
//		} else {
//			Entity entity = p_70097_1_.getEntity();
//			this.setOrderedToSit(false);
//			if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
//				p_70097_2_ = (p_70097_2_ + 1.0F) / 2.0F;
//			}
//
//			return super.hurt(p_70097_1_, p_70097_2_);
//		}
//	}
//
//	public boolean doHurtTarget(Entity p_70652_1_) {
//		boolean flag = p_70652_1_.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
//		if (flag) {
//			this.doEnchantDamageEffects(this, p_70652_1_);
//		}
//
//		return flag;
//	}
//
//	public void setTame(boolean p_70903_1_) {
//		super.setTame(p_70903_1_);
//		if (p_70903_1_) {
//			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
//			this.setHealth(20.0F);
//		} else {
//			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16.0D);
//		}
//
//		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
//	}
//
//	public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
//		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
//		Item item = itemstack.getItem();
//		if (this.level.isClientSide) {
//			boolean flag = this.isOwnedBy(p_230254_1_) || this.isTame() || item == Items.BONE && !this.isTame() && !this.isAngry();
//			return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
//		} else {
//			if (this.isTame()) {
//				if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
//					if (!p_230254_1_.abilities.instabuild) {
//						itemstack.shrink(1);
//					}
//
//					this.heal((float) item.getFoodProperties().getNutrition());
//					return ActionResultType.SUCCESS;
//				}
//
//				if (!(item instanceof DyeItem)) {
//					ActionResultType actionresulttype = super.mobInteract(p_230254_1_, p_230254_2_);
//					if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(p_230254_1_)) {
//						this.setOrderedToSit(!this.isOrderedToSit());
//						this.jumping = false;
//						this.navigation.stop();
//						this.setTarget((LivingEntity) null);
//						return ActionResultType.SUCCESS;
//					}
//
//					return actionresulttype;
//				}
//
//				if (item == Items.COD && !this.isAngry()) {
//					if (!p_230254_1_.abilities.instabuild) {
//						itemstack.shrink(1);
//					}
//
//					if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
//						this.tame(p_230254_1_);
//						this.navigation.stop();
//						this.setTarget((LivingEntity) null);
//						this.setOrderedToSit(true);
//						this.level.broadcastEntityEvent(this, (byte) 7);
//					} else {
//						this.level.broadcastEntityEvent(this, (byte) 6);
//					}
//
//					return ActionResultType.SUCCESS;
//				}
//
//				return super.mobInteract(p_230254_1_, p_230254_2_);
//			}
//		}
//		return ActionResultType.sidedSuccess(false);
//	}
//}

package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.entity.ai.goal.FlyRandomlySharkGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;

public class FlyingSharkEntity extends WolfEntity implements IRideable, IEquipable {
	private static final DataParameter<Boolean> DATA_SADDLE_ID = EntityDataManager.defineId(PigEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> DATA_BOOST_TIME = EntityDataManager.defineId(PigEntity.class, DataSerializers.INT);
	private final BoostHelper steering = new BoostHelper(this.entityData, DATA_BOOST_TIME, DATA_SADDLE_ID);
	private static final DataParameter<Byte> DATA_ID_FLAGS = EntityDataManager.defineId(AbstractHorseEntity.class, DataSerializers.BYTE);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.COD, Items.SALMON);
	// DataParameter for flying state
	public static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);
	private int mouthCounter;
	private int standCounter;
	public int tailCounter;
	public int sprintCounter;
	private float eatAnim;
	private float standAnim;
	private float mouthAnim;
	private float eatAnimO;
	private float standAnimO;
	private boolean allowStandSliding;
	private float mouthAnimO;
	protected int gallopSoundCounter;
	protected boolean isJumping;
	protected float playerJumpPendingScale;

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
		this.targetSelector.addGoal(10, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_SELECTOR));
		this.targetSelector.addGoal(11, new NonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_ON_LAND_SELECTOR));
		this.goalSelector.addGoal(12, new TemptGoal(this, 1.2D, Ingredient.of(Items.CARROT_ON_A_STICK), false));
		this.goalSelector.addGoal(13, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.MAX_HEALTH, 20.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	@javax.annotation.Nullable
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	public boolean canBeControlledByRider() {
		Entity entity = this.getControllingPassenger();
		if (!(entity instanceof PlayerEntity)) {
			return false;
		} else {
			PlayerEntity playerentity = (PlayerEntity) entity;
			return playerentity.getMainHandItem().getItem() == Items.CARROT_ON_A_STICK || playerentity.getOffhandItem().getItem() == Items.CARROT_ON_A_STICK;
		}
	}

	public FlyingSharkEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super((EntityType<? extends WolfEntity>) entityType, world);

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

	@Override
	public WolfEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
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

	public boolean boost() {
		return false;
	}

	public void travelWithInput(Vector3d p_230267_1_) {

	}

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
			double d0 = this.shark.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.shark.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d2 = this.shark.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
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
		boolean flag = this.isFood(p_230254_1_.getItemInHand(p_230254_2_));
		if (!flag && this.isSaddled() && !this.isVehicle() && !p_230254_1_.isSecondaryUseActive()) {
			if (!this.level.isClientSide) {
				p_230254_1_.startRiding(this);
			}

			return ActionResultType.sidedSuccess(this.level.isClientSide);
		} else {
			ActionResultType actionresulttype = super.mobInteract(p_230254_1_, p_230254_2_);
			if (!actionresulttype.consumesAction()) {
				ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
				return itemstack.getItem() == Items.SADDLE ? itemstack.interactLivingEntity(p_230254_1_, this, p_230254_2_) : ActionResultType.PASS;
			} else {
				return actionresulttype;
			}
		}
	}

	public boolean isSaddleable() {
		return this.isAlive() && !this.isBaby();
	}

	public void equipSaddle(@javax.annotation.Nullable SoundCategory p_230266_1_) {
		this.steering.setSaddle(true);
		if (p_230266_1_ != null) {
			this.level.playSound((PlayerEntity) null, this, SoundEvents.HORSE_SADDLE, p_230266_1_, 0.5F, 1.0F);
		}

	}

	public boolean isSaddled() {
		return this.steering.hasSaddle();
	}

	protected void setFlag(int p_110208_1_, boolean p_110208_2_) {
		byte b0 = this.entityData.get(DATA_ID_FLAGS);
		if (p_110208_2_) {
			this.entityData.set(DATA_ID_FLAGS, (byte) (b0 | p_110208_1_));
		} else {
			this.entityData.set(DATA_ID_FLAGS, (byte) (b0 & ~p_110208_1_));
		}

	}

	public double getCustomJump() {
		return this.getAttributeValue(Attributes.JUMP_STRENGTH);
	}

	public void setStanding(boolean p_110219_1_) {
		if (p_110219_1_) {
			this.setEating(false);
		}

		this.setFlag(32, p_110219_1_);
	}

	public void setEating(boolean p_110227_1_) {
		this.setFlag(16, p_110227_1_);
	}

	public boolean isEating() {
		return this.getFlag(16);
	}

	protected boolean getFlag(int p_110233_1_) {
		return (this.entityData.get(DATA_ID_FLAGS) & p_110233_1_) != 0;
	}

	public boolean isStanding() {
		return this.getFlag(32);
	}

	public boolean isJumping() {
		return this.isJumping;
	}

	public void setIsJumping(boolean p_110255_1_) {
		this.isJumping = p_110255_1_;
	}

	public void travel(Vector3d p_213352_1_) {
		if (this.isAlive()) {
			if (this.isVehicle() && this.canBeControlledByRider() && this.isSaddled()) {
				LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
				this.yRot = livingentity.yRot;
				this.yRotO = this.yRot;
				this.xRot = livingentity.xRot * 0.5F;
				this.setRot(this.yRot, this.xRot);
				this.yBodyRot = this.yRot;
				this.yHeadRot = this.yBodyRot;
				float f = livingentity.xxa * 0.5F;
				float f1 = livingentity.zza;
				if (f1 <= 0.0F) {
					f1 *= 0.25F;
					this.gallopSoundCounter = 0;
				}

				if (this.onGround && this.playerJumpPendingScale == 0.0F && this.isStanding() && !this.allowStandSliding) {
					f = 0.0F;
					f1 = 0.0F;
				}

				if (this.playerJumpPendingScale > 0.0F && !this.isJumping() && this.onGround) {
					double d0 = this.getCustomJump() * (double) this.playerJumpPendingScale * (double) this.getBlockJumpFactor();
					double d1;
					if (this.hasEffect(Effects.JUMP)) {
						d1 = d0 + (double) ((float) (this.getEffect(Effects.JUMP).getAmplifier() + 1) * 0.1F);
					} else {
						d1 = d0;
					}

					Vector3d vector3d = this.getDeltaMovement();
					this.setDeltaMovement(vector3d.x, d1, vector3d.z);
					this.setIsJumping(true);
					this.hasImpulse = true;
					net.minecraftforge.common.ForgeHooks.onLivingJump(this);
					if (f1 > 0.0F) {
						float f2 = MathHelper.sin(this.yRot * ((float) Math.PI / 180F));
						float f3 = MathHelper.cos(this.yRot * ((float) Math.PI / 180F));
						this.setDeltaMovement(this.getDeltaMovement().add((double) (-0.4F * f2 * this.playerJumpPendingScale), 0.0D, (double) (0.4F * f3 * this.playerJumpPendingScale)));
					}

					this.playerJumpPendingScale = 0.0F;
				}

				this.flyingSpeed = this.getSpeed() * 0.1F;
				if (this.isControlledByLocalInstance()) {
					this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
					super.travel(new Vector3d((double) f, p_213352_1_.y, (double) f1));
				} else if (livingentity instanceof PlayerEntity) {
					this.setDeltaMovement(Vector3d.ZERO);
				}

				if (this.onGround) {
					this.playerJumpPendingScale = 0.0F;
					this.setIsJumping(false);
				}

				this.calculateEntityAnimation(this, false);
			} else {
				this.flyingSpeed = 0.02F;
				super.travel(p_213352_1_);
			}
		}
	}
}