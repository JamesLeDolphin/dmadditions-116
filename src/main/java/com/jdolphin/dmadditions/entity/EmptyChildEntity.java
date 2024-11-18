package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMAEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

public class EmptyChildEntity extends MonsterEntity {

	public EmptyChildEntity(EntityType<? extends EmptyChildEntity> type, World world) {
		super(type, world);
		this.setHealth(20.0F);
	}

	public EmptyChildEntity(World world) {
		this(DMAEntities.EMPTY_CHILD.get(), world);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes()
			.add(Attributes.ATTACK_DAMAGE, 4D)
			.add(Attributes.MOVEMENT_SPEED, 0.2F)
			.add(Attributes.ATTACK_SPEED, 1.0f)
			.add(Attributes.MAX_HEALTH, 20.0f)
			.add(Attributes.ATTACK_KNOCKBACK)
			.add(Attributes.FOLLOW_RANGE, 30.0D);
	}

	@Override
	public boolean hurt(DamageSource source, float f) {
		Entity entity = source.getEntity();
		if (entity instanceof LivingEntity) {
			if (!(source.getEntity() instanceof EmptyChildEntity)) {
				this.setTarget((LivingEntity) entity);
			}
		}
		return super.hurt(source, f);
	}

	public void killed(ServerWorld level, LivingEntity target) {
		super.killed(level, target);
		if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) && target instanceof VillagerEntity && ForgeEventFactory.canLivingConvert(target,
			DMAEntities.EMPTY_CHILD.get(), (timer) -> {
		})) {
			if (this.random.nextBoolean()) {
				return;
			}

			VillagerEntity villagerentity = (VillagerEntity)target;
			VillagerData data = villagerentity.getVillagerData();
			EmptyVillagerEntity emptyVillager = (EmptyVillagerEntity)villagerentity.convertTo((EntityType)DMAEntities.EMPTY_VILLAGER.get(), false);
			emptyVillager.finalizeSpawn(level, level.getCurrentDifficultyAt(emptyVillager.blockPosition()),
				SpawnReason.CONVERSION,
				new ZombieEntity.GroupData(false, true), null);

			ForgeEventFactory.onLivingConvert(target, emptyVillager);
			emptyVillager.setVillagerData(data);
			if (!this.isSilent()) {
				level.levelEvent(null, 1026, this.blockPosition(), 0);
			}
		}

	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(10, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.goalSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, false));
		this.goalSelector.addGoal(11, new MeleeAttackGoal(this, 1f, false));
		this.goalSelector.addGoal(12, new HurtByTargetGoal(this));
	}

	@Override
	public void addAdditionalSaveData(@Nonnull CompoundNBT tag) {
		super.addAdditionalSaveData(tag);
	}

	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);

	}

	@Override
	public HandSide getMainArm() {
		return HandSide.RIGHT;
	}
}