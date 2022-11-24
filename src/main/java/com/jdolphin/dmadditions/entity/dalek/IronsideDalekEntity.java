package com.jdolphin.dmadditions.entity.dalek;

import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.IEntityVariant;
import com.swdteam.common.entity.ai.DestroyTargetBlock;
import com.swdteam.common.entity.ai.NearestAttackableTargetGoalForDalek;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.entity.dalek.types.Nether;
import com.swdteam.common.entity.goal.RangedLaserAttack;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Dimension;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class IronsideDalekEntity extends DalekEntity implements IRangedAttackMob, IEntityVariant, IForgeEntity, IFlyingAnimal {
	private boolean isSetup = false;
	private final RangedLaserAttack<IronsideDalekEntity> aiLaserAttack = new RangedLaserAttack(this, 1.0, 20, 15.0F);
	private DestroyTargetBlock destroyTargetBlock;
	public static final DataParameter<String> DALEK_TYPE;
	public static final DataParameter<String> DALEK_ARM_LEFT;
	public static final DataParameter<String> DALEK_ARM_RIGHT;
	public static final DataParameter<Integer> DALEK_FUSE;
	public static final DataParameter<Boolean> DALEK_BREAK_IN;
	private IDalek cachedData = null;
	public float lift;
	public float liftSpeed;
	public float oLiftSpeed;
	public float oLift;
	private float lifting = 1.0F;
	private Goal waterOne;
	private Goal laserRange;
	private Goal lookAt;
	private Goal randomGoal;
	private Goal hurtGoal;
	private Goal nearestPlayerGoal;

	public IronsideDalekEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(5, this.waterOne = new WaterAvoidingRandomWalkingGoal(this, 1.0));
		this.goalSelector.addGoal(1, this.laserRange = new RangedLaserAttack(this, 1.0, 20, 2.0F));
		this.goalSelector.addGoal(5, this.lookAt = new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, this.randomGoal = new LookRandomlyGoal(this));
		this.goalSelector.addGoal(7, this.destroyTargetBlock = new DestroyTargetBlock(DoorBlock.class, this, 6.0));
		this.goalSelector.addGoal(7, new DestroyTargetBlock(Blocks.TNT.defaultBlockState(), this, 6.0));
		this.targetSelector.addGoal(1, this.hurtGoal = new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, this.nearestPlayerGoal = new NearestAttackableTargetGoalForDalek(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoalForDalek(this, VillagerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoalForDalek(this, CybermanEntity.class, true));
	}

	public void setID(String id) {
		this.getEntityData().set(DALEK_TYPE, id);
		this.getEntityData().set(DALEK_ARM_LEFT, this.getDalekData().getRandomLeftArm(this));
		this.getEntityData().set(DALEK_ARM_RIGHT, this.getDalekData().getRandomRightArm(this));
	}

	public String getDalekArmLeft() {
		if (!this.getDalekData().getLeftArmAttatchments().contains(this.getEntityData().get(DALEK_ARM_LEFT))) {
			this.getEntityData().set(DALEK_ARM_LEFT, "GunArm");
		}

		return (String)this.getEntityData().get(DALEK_ARM_LEFT);
	}

	public String getDalekArmRight() {
		if (!this.getDalekData().getRightArmAttatchments().contains(this.getEntityData().get(DALEK_ARM_RIGHT))) {
			this.getEntityData().set(DALEK_ARM_RIGHT, "SuctionArm");
		}

		return (String)this.getEntityData().get(DALEK_ARM_RIGHT);
	}

	public void setupDalek() {
		this.getDalekData().setupDalek(this);
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.getDalekData().getMaxHealth());
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.getDalekData().getMoveSpeed());
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(15.0);
		if (this.getDalekData() instanceof Nether) {
			this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0));
		}

		if (this.getDalekData().canFly()) {
			this.moveControl = new FlyingMovementController(this, 10, false);
			FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, this.level);
			flyingpathnavigator.setCanOpenDoors(false);
			flyingpathnavigator.setCanFloat(true);
			flyingpathnavigator.setCanPassDoors(true);
			this.navigation = flyingpathnavigator;
			this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(0.4);
		}

	}

	public void set() {
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5);
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1.2000000476837158);
		this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(15.0);
	}

	public int getFuse() {
		return (Integer)this.getEntityData().get(DALEK_FUSE);
	}

	public boolean canBreakIn() {
		return (Boolean)this.getEntityData().get(DALEK_BREAK_IN);
	}

	public void calculateEntityAnimation(LivingEntity entity, boolean b) {
		if (this.getDalekData().canFly()) {
			super.calculateEntityAnimation(entity, b);
		}

	}

	public void getBreakIn(boolean state) {
		this.getEntityData().set(DALEK_BREAK_IN, state);
		if (state) {
			this.goalSelector.removeGoal(this.destroyTargetBlock);
		} else {
			this.goalSelector.addGoal(7, this.destroyTargetBlock);
		}

	}

	public ITextComponent getName() {
		return this.hasCustomName() ? this.getCustomName() : super.getName();
	}

	public void die(DamageSource cause) {
		this.getDalekData().onDeath(this);
		this.getDalekData().setDead(true);
		super.die(cause);
	}

	public void kill() {
		this.getDalekData().onDeath(this);
		this.getDalekData().setDead(true);
		super.kill();
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.30000001192092896).add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 4.0).add(Attributes.FOLLOW_RANGE, 15.0).add(Attributes.FLYING_SPEED, 0.0);
	}

	public void goCrazy() {
		this.goalSelector.removeGoal(this.waterOne);
		this.goalSelector.removeGoal(this.laserRange);
		this.goalSelector.removeGoal(this.lookAt);
		this.goalSelector.removeGoal(this.randomGoal);
		this.targetSelector.removeGoal(this.hurtGoal);
		this.targetSelector.removeGoal(this.nearestPlayerGoal);
	}

	public boolean hurt(DamageSource damageSource, float amount) {
		if (damageSource != null && damageSource.getEntity() != this) {
			this.getDalekData().onAttacked(this, damageSource.getEntity(), damageSource);
			if (damageSource.getEntity() != null) {
				this.lookAt(damageSource.getEntity(), 1.0F, 1.0F);
			}

			this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), (SoundEvent)DMSoundEvents.ENTITY_DALEK_HURT.get(), this.getSoundSource(), 1.0F, 1.0F, false);
		}

		return super.hurt(damageSource, amount);
	}

	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}

	protected PathNavigator createNavigation(World world) {
		return super.createNavigation(world);
	}

	protected float playFlySound(float f) {
		return 0.0F;
	}

	protected void playStepSound(BlockPos pos, BlockState state) {
		SoundEvent sound = this.getDalekData().getMovementSound(this);
		if (sound != null) {
			this.playSound(sound, 0.2F, 1.0F);
		}

	}

	private void calculateLifting() {
		this.oLift = this.lift;
		this.oLiftSpeed = this.liftSpeed;
		this.liftSpeed = (float)((double)this.liftSpeed + (double)(!this.onGround ? 4 : -1) * 0.3);
		this.liftSpeed = MathHelper.clamp(this.liftSpeed, 0.0F, 1.0F);
		if (!this.onGround && this.lifting < 1.0F) {
			if (this.tickCount % 15 == 0) {
				this.playSound((SoundEvent)DMSoundEvents.ENTITY_DALEK_HOVER.get(), 1.0F, 1.0F);
			}

			this.lifting = 1.0F;
		}

		this.lifting = (float)((double)this.lifting * 0.9);
		Vector3d vector3d = this.getDeltaMovement();
		if (!this.onGround && vector3d.y < 0.0) {
			this.setDeltaMovement(vector3d.multiply(1.0, 0.6, 1.0));
		}

		this.lift += this.lifting * 2.0F;
	}

	public boolean causeFallDamage(float distance, float damageMultiplier) {
		return this.getDalekData().canFly() ? false : super.causeFallDamage(distance, damageMultiplier);
	}

	public boolean isFlying() {
		return !this.onGround;
	}

	public void aiStep() {
		super.aiStep();
		if (this.getDalekData().canFly()) {
			this.calculateLifting();
		}

		if (!this.isSetup) {
			this.setupDalek();
			this.isSetup = true;
		}

		this.getDalekData().onUpdate(this);
		if (!this.level.isClientSide && this.getFuse() >= 0) {
			this.getEntityData().set(DALEK_FUSE, this.getFuse() + 1);
			if (this.getFuse() > 25) {
				if (this.getTarget() != null) {
					this.lookAt(this.getTarget(), 10.0F, 10.0F);
					this.getDalekData().spawnLaserAttack(this, this.getTarget());
				}

				this.getEntityData().set(DALEK_FUSE, -1);
			}
		}

	}

	public float getEyeHeight(Pose pose) {
		return 0.5F;
	}

	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
		return 1.5F;
	}

	public double getMyRidingOffset() {
		return -0.6;
	}

	public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		this.getDalekData().mobInteract(player, hand, this);
		return super.mobInteract(player, hand);
	}

	public void tick() {
		super.tick();
	}

	public boolean checkSpawnRules(IWorld worldIn, SpawnReason spawnReasonIn) {
		if (this.level.getDifficulty().getId() == 0) {
			this.remove();
			return false;
		} else {
			return true;
		}
	}

	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		String idToUse = null;
		if (reason != SpawnReason.SPAWN_EGG && reason != SpawnReason.DISPENSER) {
			IDalek dalekSpawn = DMDalekRegistry.getDalekForBiome(this.level, worldIn.getBiome(this.blockPosition()));
			if (dalekSpawn != null) {
				if (reason != SpawnReason.NATURAL && reason != SpawnReason.CHUNK_GENERATION) {
					idToUse = DMDalekRegistry.getRandomDalek(this.random);
				} else {
					idToUse = DMDalekRegistry.getRandomDalek(this.random, dalekSpawn.getType()).getID();
				}
			}
		}

		if (idToUse != null) {
			this.setID(idToUse);
		}

		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public IDalek getDalekData() {
		if (this.cachedData == null) {
			this.cachedData = DMDalekRegistry.getDalek((String)this.getEntityData().get(DALEK_TYPE));
		}

		return this.cachedData;
	}

	public ItemStack getPickedResult(RayTraceResult target) {
		Item i = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation("dalekmod", this.getDalekData().getType().getRegistryName() + "_spawner"));
		return i != null ? new ItemStack(i) : new ItemStack((IItemProvider)DMItems.DALEK_SPAWNER[0].get());
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		if (this.level.dimension().location().equals(Dimension.NETHER.location())) {
			this.entityData.define(DALEK_TYPE, DMDalekRegistry.NETHER_DALEK.getID());
		} else {
			this.entityData.define(DALEK_TYPE, DMDalekRegistry.getRandomDalek(this.random));
		}

		this.entityData.define(DALEK_FUSE, -1);
		this.entityData.define(DALEK_ARM_LEFT, this.getDalekData().getRandomLeftArm(this));
		this.entityData.define(DALEK_ARM_RIGHT, this.getDalekData().getRandomRightArm(this));
	}

	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putString(DMNBTKeys.TYPE_DALEK, (String)this.getEntityData().get(DALEK_TYPE));
		compound.putString(DMNBTKeys.ARM_ATTACHMENT_LEFT, this.getDalekArmLeft());
		compound.putString(DMNBTKeys.ARM_ATTACHMENT_RIGHT, this.getDalekArmRight());
		compound.putInt(DMNBTKeys.DALEK_FUSE, (Integer)this.getEntityData().get(DALEK_FUSE));
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.entityData.set(DALEK_TYPE, compound.getString(DMNBTKeys.TYPE_DALEK));
		this.entityData.set(DALEK_ARM_LEFT, compound.getString(DMNBTKeys.ARM_ATTACHMENT_LEFT));
		this.entityData.set(DALEK_ARM_RIGHT, compound.getString(DMNBTKeys.ARM_ATTACHMENT_RIGHT));
		this.entityData.set(DALEK_FUSE, compound.getInt(DMNBTKeys.DALEK_FUSE));
		this.cachedData = null;
	}

	public void onSyncedDataUpdated(DataParameter<?> key) {
		if (DALEK_TYPE.equals(key)) {
			this.cachedData = DMDalekRegistry.getDalek((String)this.getEntityData().get(DALEK_TYPE));
		}

		super.onSyncedDataUpdated(key);
	}

	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		this.getDalekData().onPreLaserAttack(this, target, distanceFactor);
	}

	public void fireLaserAt(BlockPos target, float distanceFactor) {
	}

	public boolean canPlayAmbientSound() {
		return true;
	}

	protected SoundEvent getAmbientSound() {
		return this.getTarget() == null && this.tickCount >= 200 ? this.getDalekData().getAmbientSound(this) : null;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return this.getDalekData().getHurtSound(this);
	}

	protected SoundEvent getDeathSound() {
		return this.getDalekData().getDeathSound(this);
	}

	public boolean isLeftArm(String s) {
		return this.getDalekArmLeft().equalsIgnoreCase(s);
	}

	public boolean isRightArm(String s) {
		return this.getDalekArmRight().equalsIgnoreCase(s);
	}

	static {
		DALEK_TYPE = EntityDataManager.defineId(IronsideDalekEntity.class, DataSerializers.STRING);
		DALEK_ARM_LEFT = EntityDataManager.defineId(IronsideDalekEntity.class, DataSerializers.STRING);
		DALEK_ARM_RIGHT = EntityDataManager.defineId(IronsideDalekEntity.class, DataSerializers.STRING);
		DALEK_FUSE = EntityDataManager.defineId(IronsideDalekEntity.class, DataSerializers.INT);
		DALEK_BREAK_IN = EntityDataManager.defineId(IronsideDalekEntity.class, DataSerializers.BOOLEAN);
	}
}
