package com.jdolphin.dmadditions.entity;

import static com.jdolphin.dmadditions.init.DMAItems.PILOT_FISH_SPAWNER;
import static com.jdolphin.dmadditions.init.DMAItems.PILOT_FISH_TRUMPET;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.jdolphin.dmadditions.entity.ai.goal.RangedLasergunAttackGoal;
import com.jdolphin.dmadditions.init.DMANBTKeys;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.entity.LookAtGoalBetter;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.item.GunItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.structure.Structure;

// Robo Santa or something
public class PilotFishEntity extends MonsterEntity implements IRangedAttackMob {
	public static final DataParameter<String> PILOT_FISH_TYPE = EntityDataManager.defineId(PilotFishEntity.class, DataSerializers.STRING);
	private final RangedLasergunAttackGoal<PilotFishEntity> rangedAttackGoal = new RangedLasergunAttackGoal<>(this, 1.0D, 20, 15.0F);
	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false);

	public PilotFishEntity(EntityType<? extends MonsterEntity> type, World world) {
		super(type, world);

		this.reassessWeaponGoal();
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.MAX_HEALTH, 20.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		if(PILOT_FISH_SPAWNER == null) return null;

		return new ItemStack(PILOT_FISH_SPAWNER.get());
	}

	@Override
	public void setItemSlot(EquipmentSlotType p_184201_1_, ItemStack p_184201_2_) {
		super.setItemSlot(p_184201_1_, p_184201_2_);
		if (!this.level.isClientSide) {
			this.reassessWeaponGoal();
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
		super.populateDefaultEquipmentSlots(p_180481_1_);
		List<ItemStack> inventory = this.getPilotFishType().getInventory.apply(this.random);
		if (inventory != null) {
			inventory.forEach(this::equipItemIfPossible);
		}
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {

		p_213386_4_ = super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
		this.populateDefaultEquipmentSlots(p_213386_2_);
		this.populateDefaultEquipmentEnchantments(p_213386_2_);
		this.reassessWeaponGoal();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * p_213386_2_.getSpecialMultiplier());

		return p_213386_4_;
	}

	public void addAdditionalSaveData(@Nonnull CompoundNBT compound) {
		if (this.entityData != null) {
			compound.putString(DMANBTKeys.TYPE_PILOT_FISH, this.entityData.get(PILOT_FISH_TYPE));
		}

		super.addAdditionalSaveData(compound);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(DMANBTKeys.TYPE_PILOT_FISH)) {
			this.setPilotFishType(compound.getString(DMANBTKeys.TYPE_PILOT_FISH));
		}

		super.readAdditionalSaveData(compound);

		this.reassessWeaponGoal();
	}

	protected void defineSynchedData() {
		PilotFishType[] types = PilotFishType.values();
		this.getEntityData().define(PILOT_FISH_TYPE, types[this.random.nextInt(types.length)].getName());
		super.defineSynchedData();
	}

	public PilotFishType getPilotFishType() {
		return PilotFishType.get(this.entityData.get(PILOT_FISH_TYPE));
	}

	public void setPilotFishType(String type) {
		setPilotFishType(PilotFishType.get(type));
	}

	public void setPilotFishType(PilotFishType type) {
		if (this.entityData != null) {
			this.entityData.set(PILOT_FISH_TYPE, type.getName());
		}
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinEntity.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinBruteEntity.class, 6.0F, 1.0, 1.2));

		this.goalSelector.addGoal(5, new LookAtGoalBetter(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.8));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Entity.class));
	}


	@Override
	public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {
		Hand hand = ProjectileHelper.getWeaponHoldingHand(this, item -> item instanceof GunItem);
		int damage = 2;
		ItemStack itemstack = this.getItemInHand(hand);

		LaserEntity laser = new LaserEntity(this.level, this, 0, (float) damage);
		if (PILOT_FISH_TRUMPET != null && itemstack.getItem().equals(PILOT_FISH_TRUMPET.get()))
			laser.setLaserType(DMProjectiles.EXPLOSIVE_LASER);

		double d0 = p_82196_1_.getX() - this.getX();
		double d1 = p_82196_1_.getY(0.3333333333333333D) - laser.getY();
		double d2 = p_82196_1_.getZ() - this.getZ();
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		laser.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level.getDifficulty().getId() * 4));
		this.playSound(DMSoundEvents.ENTITY_DALEK_CANNON_SHOOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level.addFreshEntity(laser);
	}

	public void reassessWeaponGoal() {
		if (this.level == null || this.level.isClientSide) return;

		this.goalSelector.removeGoal(this.meleeGoal);
		this.goalSelector.removeGoal(this.rangedAttackGoal);

		ItemStack itemstack = this.getItemInHand(ProjectileHelper.getWeaponHoldingHand(this, item -> item instanceof GunItem));
		if (itemstack.getItem() instanceof GunItem) {
			int i = 20;
			if (this.level.getDifficulty() != Difficulty.HARD) {
				i = 40;
			}

			this.rangedAttackGoal.setMinAttackInterval(i);
			this.goalSelector.addGoal(4, this.rangedAttackGoal);
		} else {
			this.goalSelector.addGoal(4, this.meleeGoal);
		}
	}

	@Override
	public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
		if(!reason.equals(SpawnReason.NATURAL))
			return super.checkSpawnRules(world, reason);

		BlockPos blockPos = blockPosition();
		IChunk chunk = world.getChunk(blockPos);

		boolean noVillages = chunk.getReferencesForFeature(Structure.VILLAGE).isEmpty();
		if(noVillages) return false;

		return super.checkSpawnRules(world, reason);
	}
}
