package com.jdolphin.dmadditions.common.entity.cyber;

import com.jdolphin.dmadditions.common.init.DMAEntities;
import com.swdteam.common.entity.*;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.StormEntity;
import com.swdteam.common.entity.goal.RangedLaserAttack;
import com.swdteam.common.init.*;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketLaserRecoil;
import com.swdteam.util.SWDMathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DMACybermanEntity extends MonsterEntity implements IRangedAttackMob {
	public static final DataParameter<String> CYBERMAN_TYPE = EntityDataManager.defineId(DMACybermanEntity.class, DataSerializers.STRING);
	public static final DataParameter<Boolean> HAS_GUN = EntityDataManager.defineId(DMACybermanEntity.class, DataSerializers.BOOLEAN);;
	private final ItemStack pickResult;
	private final SoundEvent ambientSound;
	private final  CybermanType type;

	public DMACybermanEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		this(type, worldIn, ItemStack.EMPTY);
	}

	public DMACybermanEntity(EntityType<? extends MonsterEntity> type, World world, ItemStack pickResult) {
		this(type, world, pickResult.getItem(), DMSoundEvents.ENTITY_CYBERMAN_LIVING.get());
	}

	public DMACybermanEntity(EntityType<? extends MonsterEntity> type, World world, Item pickResult) {
		this(type, world, pickResult, DMSoundEvents.ENTITY_CYBERMAN_LIVING.get());
	}

	public DMACybermanEntity(EntityType<? extends MonsterEntity> type, World world, Item pickResult, CybermanType cybermanType) {
		this(type, world, pickResult, DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), cybermanType);
	}

	public DMACybermanEntity(EntityType<? extends MonsterEntity> type, World world, Item pickResult, SoundEvent ambientSound) {
		this(type, world, pickResult, ambientSound, CybermanType.CYBERWARRIOR);
	}

	public DMACybermanEntity(EntityType<? extends MonsterEntity> entityType, World world, Item pickResult, SoundEvent ambientSound, CybermanType type) {
		super(entityType, world);
		this.pickResult = pickResult != null ? pickResult.getDefaultInstance() : ItemStack.EMPTY;
		this.ambientSound = ambientSound;
		this.type = type;
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.35F)
			.add(Attributes.MAX_HEALTH, 40.0F)
			.add(Attributes.ATTACK_DAMAGE, 2.0F)
			.add(Attributes.FOLLOW_RANGE, 20.0F);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinEntity.class, 6.0F, 1.0F, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinBruteEntity.class, 6.0F, 1.0F, 1.2));
		if (this.hasGun()) {
			this.goalSelector.addGoal(4, new RangedLaserAttack<>(this, 1.0F, 20, 15.0F));
		} else {
			this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0F, false));
		}

		this.goalSelector.addGoal(5, new LookAtGoalBetter(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.8));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AnimalEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, WanderingTraderEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, EvokerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PillagerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VindicatorEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IllusionerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, RavagerEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, WitchEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AutonEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, OodEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, ClamEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, MagnodonEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, DalekEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, StormEntity.class, true));
	}

	public void addAdditionalSaveData(CompoundNBT compound) {
		compound.putBoolean(DMNBTKeys.GUN_ARMED, this.getEntityData().get(HAS_GUN));
		if (this.entityData != null) {
			compound.putString(DMNBTKeys.TYPE_CYBER, this.entityData.get(CYBERMAN_TYPE));
		}

		super.addAdditionalSaveData(compound);
	}

	public void readAdditionalSaveData(CompoundNBT compound) {
		if (compound.contains(DMNBTKeys.GUN_ARMED)) {
			this.getEntityData().set(HAS_GUN, compound.getBoolean(DMNBTKeys.GUN_ARMED));
		}

		if (compound.contains(DMNBTKeys.TYPE_CYBER)) {
			this.setCybermanType(compound.getString(DMNBTKeys.TYPE_CYBER));
		} else {
			this.setCybermanType(getRandomCyberType());
		}

		super.readAdditionalSaveData(compound);
	}

	public boolean hasGun() {
		return this.getEntityData().get(HAS_GUN);
	}

	public CybermanType getRandomCyberType() {
		CybermanType[] types = CybermanType.values();
		return types[random.nextInt(types.length)];
	}

	protected void defineSynchedData() {
		this.getEntityData().define(HAS_GUN, this.level.random.nextInt(5) == 3);

		this.getEntityData().define(CYBERMAN_TYPE, type == null ? getRandomCyberType().getName() : type.getName());
		super.defineSynchedData();
	}

	public CybermanType getCybermanType() {
		return CybermanType.get(this.entityData.get(CYBERMAN_TYPE));
	}

	public void setCybermanType(String  type) {
		if (this.entityData != null) {
			this.entityData.set(CYBERMAN_TYPE, type);
		}
	}

	public void setCybermanType(CybermanType type) {
		if (this.entityData != null) {
			this.entityData.set(CYBERMAN_TYPE, type.getName());
		}
	}

	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		double d0;
		double d1;
		double d2;
		if (target.isAlive()) {
			d0 = target.getX() - this.getX();
			d1 = target.getY(0.3333333333333333) - this.getY() - (double)0.75F;
			d2 = target.getZ() - this.getZ();
			if (this.level.random.nextInt(5) == 2) {
				this.playSound(DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), 1.0F, 1.0F);
			}

			LaserEntity laser = new LaserEntity(this.level, this, 0.2F, 2.0F);
			laser.setLaserType(DMProjectiles.ORANGE_LASER);
			laser.shoot(d0, d1, d2, 2.5F, 0.0F);
			this.playSound(DMSoundEvents.ENTITY_CYBERMAN_SHOOT.get(), 1.0F, 1.0F);
			this.level.addFreshEntity(laser);

			for(ServerPlayerEntity p : this.level.getEntitiesOfClass(ServerPlayerEntity.class, this.getBoundingBox().inflate((double)32.0F))) {
				NetworkHandler.sendTo(p, new PacketLaserRecoil(this.uuid.toString(), PacketLaserRecoil.EntitiesWithRecoil.CYBERMAN));
			}

		}
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(DMSoundEvents.ENTITY_CYBERMAN_STEP.get(), 0.5F, SWDMathUtils.randomRange(0.8F, 1.2F));
	}

	public void playAmbientSound() {
		if (this.tickCount >= 200) {
			this.playSound(this.ambientSound, 0.5F, 1.0F);
		}

	}

	protected @NotNull SoundEvent getHurtSound(DamageSource source) {
		return DMSoundEvents.ENTITY_CYBERMAN_HURT.get();
	}

	private void goldHurtEffects(float attackDamage) {
		if (this.getHealth() - 3.0F <= 0.0F) {
			this.setHealth(this.getHealth() - (this.getHealth() - attackDamage));
		} else {
			this.setHealth(this.getHealth() - 3.0F);
		}

		if (this.level.isClientSide) {
			this.level.addParticle(DMParticleTypes.GOLD_DUST.get(),
				this.getRandomX(-0.5F), this.getY(0.5F),
				this.getRandomZ(0.5F), 0.0F, 0.0F, 0.0F);
		}

	}

	public void die(DamageSource source) {
		super.die(source);
		if (!this.level.isClientSide() && SWDMathUtils.randomRange(1.0F, 7.0F) == 3.0F) {
			for (int e = 0; (float) e < SWDMathUtils.randomRange(1.0F, 3.0F); ++e) {
				CybermatEntity cybermat = (CybermatEntity) DMEntities.CYBERMAT_ENTITY.get()
					.spawn((ServerWorld) this.level, null, null, new BlockPos(this.position()), SpawnReason.NATURAL, true, true);
				if (cybermat != null) {
					cybermat.moveTo(this.getX(), this.getY(), this.getZ(), this.getYHeadRot(), (float) this.getMaxHeadXRot());
					cybermat.push(SWDMathUtils.randomDouble(-0.4, 0.4), 0.5F, SWDMathUtils.randomDouble(-0.4, 0.4));
					cybermat.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SWDMathUtils.randomRange(0.6F, 1.0F), SWDMathUtils.randomRange(0.6F, 1.0F));
					this.level.addFreshEntity(cybermat);
				}
			}
		}
	}

	public <E extends MobEntity> void convert(ServerWorld world, MobEntity target, EntityType<E> convertToType) {
		if (ForgeEventFactory.canLivingConvert(target, convertToType, (timer) -> {})) {
			E converted = target.convertTo(convertToType, false);
			if (converted != null) {
				converted.finalizeSpawn(world, world.getCurrentDifficultyAt(converted.blockPosition()), SpawnReason.CONVERSION,
					new ZombieEntity.GroupData(false, true), null);
				ForgeEventFactory.onLivingConvert(target, converted);
				if (!this.isSilent()) {
					world.levelEvent(null, 1026, this.blockPosition(), 0);
				}
			}
		}
	}

	public void killed(ServerWorld level, LivingEntity target) {
		super.killed(level, target);
		if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD)) {
			if (target instanceof VillagerEntity)
				convert(level, ((VillagerEntity) target), DMEntities.CYBERMANVILLAGER_ENTITY.get());
			if (target instanceof PiglinEntity)
				convert(level, ((PiglinEntity) target), DMAEntities.CYBER_PIGLIN.get());
			if (target instanceof PiglinBruteEntity)
				convert(level, ((PiglinBruteEntity) target), DMAEntities.NETHERITE_CYBERMAN.get());
		}
	}

	public boolean hurt(DamageSource source, float f) {
		if (source.getEntity() instanceof LivingEntity) {
			Item item = ((LivingEntity) source.getEntity()).getMainHandItem().getItem();
			boolean isGold = item.is(DMTags.Items.CYBERMAN_WEAKNESS) || item instanceof TieredItem &&
				((TieredItem) item).getTier().equals(ItemTier.GOLD) || item instanceof ArmorItem &&
				((ArmorItem) item).getMaterial().equals(ArmorMaterial.GOLD);

			if (isGold) {
				this.goldHurtEffects(f);
			}
		} else if (source.getEntity() != null && source.getEntity().getType().is(DMTags.EntityTypes.GOLD)) {
			this.goldHurtEffects(f);
		}

		return !source.isFire() && super.hurt(source, f);
	}

	public ItemStack getPickedResult(RayTraceResult target) {
		return this.pickResult;
	}

	public enum CybermanType {
		CYBERWARRIOR("cyberwarrior"),
		TOMB("tomb"),
		TOMB_CONTROLLER("tomb_controller"),
		INVASION("invasion"),

		;
		private final String name;
		public String getName() {
			return this.name;
		}

		CybermanType(String name) {
			this.name = name;
		}

		public static CybermanType get(String name) {
			return Arrays.stream(values())
				.filter(type -> type.name.equals(name)).findFirst()
				.orElse(CYBERWARRIOR);
		}
	}
}
