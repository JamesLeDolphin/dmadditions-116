package com.jdolphin.dmadditions.entity;

import static net.minecraft.entity.passive.WolfEntity.PREY_SELECTOR;

import java.util.EnumSet;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.swdteam.common.init.DMTags;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FlyingSharkEntity extends TameableEntity implements IAngerable, IJumpingMount {
	private static final ITag<Item> FOOD_ITEMS = ItemTags.FISHES;

	// DataParameter for flying state
	public static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);

	public static final DataParameter<Boolean> SADDLED = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.BOOLEAN);

	private static final DataParameter<Integer> DATA_REMAINING_ANGER_TIME = EntityDataManager.defineId(FlyingSharkEntity.class, DataSerializers.INT);
	private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
	private UUID persistentAngerTarget;

	public Inventory inventory = new Inventory(8);

	protected StealSonicGoal stealSonicGoal;

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(FLYING, true);
		this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
		this.entityData.define(SADDLED, false);
	}

	protected void registerGoals() {
		this.stealSonicGoal = new StealSonicGoal(this, 2);

		this.goalSelector.addGoal(1, new TemptGoal(this, 1.25D, Ingredient.of(FOOD_ITEMS), false));
		this.goalSelector.addGoal(2, stealSonicGoal);
		this.goalSelector.addGoal(3, new SwimGoal(this));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, true));
		this.goalSelector.addGoal(6, new FlyingSharkEntity.WanderGoal());
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new FlyingSharkEntity.LookAroundGoal(this));
		this.goalSelector.addGoal(9, new BreedGoal(this, 1.0D));

		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_SELECTOR));
	}

	protected PathNavigator createNavigation(World world) {
		FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world) {
			public boolean isStableDestination(BlockPos p_188555_1_) {
				return !this.level.getBlockState(p_188555_1_.below()).isAir();
			}

			public void tick() {
				if (((TameableEntity) this.mob).isOrderedToSit()) {
					this.stop();
				} else {
					super.tick();
				}
			}
		};

		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanFloat(false);
		flyingpathnavigator.setCanPassDoors(true);


		return flyingpathnavigator;
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

		this.moveControl = new FlyingMovementController(this, 1, false);
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
		return this.entityData.get(DATA_OWNERUUID_ID).isPresent();
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.contains(stack.getItem());
	}

	@Nullable
	@Override
	public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		return null;
	}

	public int getRemainingPersistentAngerTime() {
		return this.entityData.get(DATA_REMAINING_ANGER_TIME);
	}

	public void setRemainingPersistentAngerTime(int p_230260_1_) {
		this.entityData.set(DATA_REMAINING_ANGER_TIME, p_230260_1_);
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
	}

	@Nullable
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
		this.persistentAngerTarget = p_230259_1_;
	}

	@Override
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

				if (f1 <= 0.0F) {
					f1 *= 0.25F;
				}

				if (f1 > 0.5F) {
					f1 = 0.5F;
				}


				if (f1 > 0.0F) {
					float f2 = MathHelper.sin(this.yRot * 0.017453292F);
					float f3 = MathHelper.cos(this.yRot * 0.017453292F);

					this.setDeltaMovement(this.getDeltaMovement().add(-0.2F * f2, Math.toRadians(this.xRot) * -0.5, 0.2F * f3));

					this.setNoGravity(false);
				} else if (this.getEntity().getDeltaMovement().y <= 0) {
					this.setNoGravity(true);

					if (this.level.getBlockState(this.blockPosition().below()).isFaceSturdy(level, this.blockPosition(), Direction.DOWN)) {
						this.setDeltaMovement(this.getDeltaMovement().add(0, 0.1, 0));
					}
				}

			}

			super.travel(p_213352_1_);
		}
	}


	public boolean isSaddled() {
		return this.entityData.get(SADDLED);
	}

	public void setSaddled(boolean saddled) {
		this.entityData.set(SADDLED, saddled);
	}

	public void removeSaddle() {
		if (this.isSaddled()) {
			this.spawnAtLocation(new ItemStack(Items.SADDLE));
		}

		setSaddled(false);
	}

	@Override
	protected void dropAllDeathLoot(DamageSource p_213345_1_) {
		super.dropAllDeathLoot(p_213345_1_);
		if (this.getEntity().level.isClientSide) return;

		this.removeSaddle();

		for (int i = 0; i < this.inventory.getContainerSize(); i++) {
			ItemStack itemStack = this.inventory.getItem(i);
			if (itemStack.isEmpty()) continue;

			this.spawnAtLocation(itemStack);
		}
	}

	@Override
	public boolean rideableUnderWater() {
		return true;
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
			if (!this.shark.isInWater() && shark.level.getBlockState(shark.blockPosition().below()).getBlock().is(Blocks.AIR)) {
				this.shark.setFlying(true);
			}

			if (this.shark.getTarget() == null) {
/*
				Vector3d vector3d = this.shark.getDeltaMovement();
				this.shark.yRot = -((float) MathHelper.atan2(vector3d.x, vector3d.z)) * (180F / (float)Math.PI);
				this.shark.yBodyRot = this.shark.yRot;
*/
			} else {
				LivingEntity livingentity = this.shark.getTarget();
				double d0 = 64.0D;
				if (livingentity.distanceToSqr(this.shark) < 4096.0D) {
					double d1 = livingentity.getX() - this.shark.getX();
					double d2 = livingentity.getZ() - this.shark.getZ();
					this.shark.yRot = -((float) MathHelper.atan2(d1, d2)) * (180F / (float) Math.PI);
					this.shark.yBodyRot = this.shark.yRot;
				}
			}

		}
	}

	private boolean inventoryFull() {

		for (int i = 0; i < this.inventory.getContainerSize(); i++) {
			ItemStack itemstack = this.inventory.getItem(i);
			if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
				return false;
			}
		}

		return true;
	}

	static class StealSonicGoal extends TemptGoal {
		private static final EntityPredicate TEMP_TARGETING = (new EntityPredicate()).range(64.0D).allowInvulnerable().allowSameTeam().allowNonAttackable().allowUnseeable();
		private int calmDown;

		public StealSonicGoal(FlyingSharkEntity mob, double speedModifier) {
			super(mob, speedModifier, Ingredient.of(DMTags.Items.SONICS), false);
		}

		@Override
		public void tick() {
			super.tick();

			if (this.mob.distanceToSqr(this.player) < 6.25D) {
				ItemStack itemStack = this.player.getItemInHand(player.getUsedItemHand());
				if (itemStack.getItem().is(DMTags.Items.SONICS)) {

					Inventory inventory = ((FlyingSharkEntity) this.mob).inventory;
					if (((FlyingSharkEntity) this.mob).inventoryFull()) {
						this.mob.spawnAtLocation(itemStack);

						this.mob.playSound(SoundEvents.PLAYER_BURP, 1, 1);
					} else {
						inventory.addItem(itemStack);
					}

					player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
					this.mob.playSound(SoundEvents.GENERIC_EAT, 1, 1);
				}
			}
		}

		@Override
		public boolean canUse() {
			if (this.calmDown > 0) {
				--this.calmDown;
				return false;
			} else {
				this.player = this.mob.level.getNearestPlayer(TEMP_TARGETING, this.mob);
				if (this.player == null) {
					return false;
				} else {
					return this.shouldFollowItem(this.player.getMainHandItem()) || this.shouldFollowItem(this.player.getOffhandItem());
				}
			}
		}

		@Override
		public void stop() {
			super.stop();
			this.calmDown = 100;
		}
	}

	class WanderGoal extends Goal {
		WanderGoal() {
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			return FlyingSharkEntity.this.navigation.isDone() && FlyingSharkEntity.this.random.nextInt(10) == 0 && !FlyingSharkEntity.this.isOrderedToSit() && !FlyingSharkEntity.this.isVehicle();
		}

		public boolean canContinueToUse() {
			return FlyingSharkEntity.this.navigation.isInProgress();
		}

		public void start() {
			Vector3d vector3d = this.findPos();
			if (vector3d != null) {
				FlyingSharkEntity.this.navigation.moveTo(FlyingSharkEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
			}

		}

		@javax.annotation.Nullable
		private Vector3d findPos() {
			Vector3d vector3d;
			vector3d = FlyingSharkEntity.this.getViewVector(0.0F);

			int i = 8;
			Vector3d vector3d2 = RandomPositionGenerator.getAboveLandPos(FlyingSharkEntity.this, 8, 7, vector3d, ((float) Math.PI / 2F), 2, 1);
			return vector3d2 != null ? vector3d2 : RandomPositionGenerator.getAirPos(FlyingSharkEntity.this, 8, 4, -2, vector3d, (double) ((float) Math.PI / 2F));
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
			this.setHealth(this.getMaxHealth());
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16.0D);
		}

		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
	}

	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		Item item = itemstack.getItem();
		if (this.level.isClientSide) {
			boolean flag = this.isOwnedBy(player) || this.isTame() || item == Items.BONE && !this.isTame() && !this.isAngry();
			return flag ? ActionResultType.CONSUME : ActionResultType.PASS;
		} else {
			if (this.isTame()) {
				if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
					if (!player.abilities.instabuild) {
						itemstack.shrink(1);
					}

					this.heal((float) item.getFoodProperties().getNutrition());
					return ActionResultType.SUCCESS;
				}

				if (this.isSaddled()) {
					if (itemstack.isEmpty() && !isOrderedToSit() && !player.isCrouching()) {
						player.startRiding(this);
						return ActionResultType.PASS;
					}
				} else if (item == Items.SADDLE) {
					this.setSaddled(true);
					this.playSaddleEquipSound();
					itemstack.shrink(1);

					return ActionResultType.CONSUME;
				}

				if (!(item instanceof DyeItem)) {
					ActionResultType actionresulttype = super.mobInteract(player, hand);
					if ((!actionresulttype.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
						this.setOrderedToSit(!this.isOrderedToSit());
						this.jumping = false;
						this.navigation.stop();
						this.setTarget((LivingEntity) null);
						return ActionResultType.SUCCESS;
					}

					return actionresulttype;
				}

			} else if (isFood(itemstack) && !this.isAngry()) {
				if (!player.abilities.instabuild) {
					itemstack.shrink(1);
				}

				if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
					this.tame(player);
					this.navigation.stop();
					this.setTarget((LivingEntity) null);
					this.setOrderedToSit(true);
					this.level.broadcastEntityEvent(this, (byte) 7);
				} else {
					this.level.broadcastEntityEvent(this, (byte) 6);
				}

				return ActionResultType.SUCCESS;
			}

			return super.mobInteract(player, hand);
		}
	}

	public boolean canBeControlledByRider() {
		return this.getControllingPassenger() instanceof LivingEntity;
	}

	public boolean canBeSteered() {
		return this.getControllingPassenger() instanceof LivingEntity;
	}

	@javax.annotation.Nullable
	public Entity getControllingPassenger() {
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
		super.addAdditionalSaveData(p_213281_1_);

		p_213281_1_.put("Inventory", this.inventory.createTag());
		p_213281_1_.putBoolean("Saddled", this.isSaddled());
	}

	public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);

		this.inventory.fromTag(p_70037_1_.getList("Inventory", 10));
		this.setSaddled(p_70037_1_.getBoolean("Saddled"));
	}

	public void aiStep() {
		super.aiStep();

		if (!this.level.isClientSide) {
			this.updatePersistentAnger((ServerWorld) this.level, true);
		}
	}

	protected void playSaddleEquipSound() {
		this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	@Override
	protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
		this.playSound(SoundEvents.TROPICAL_FISH_FLOP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.TROPICAL_FISH_HURT;
	}


	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.DOLPHIN_SWIM;
	}

}
