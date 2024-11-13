package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.init.DMItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class EmptyChildEntity extends MonsterEntity {

	public EmptyChildEntity(EntityType<EmptyChildEntity> type, World world) {
		super(type, world);
		this.setHealth(20.0F);
	}

	public EmptyChildEntity(World world) {
		super(DMAEntities.EMPTY_CHILD.get(), world);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return LivingEntity.createLivingAttributes()
			.add(Attributes.ATTACK_DAMAGE, 1.0D)
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
			if (!source.getEntity().getType().equals(DMAEntities.EMPTY_CHILD.get())) {
				this.setTarget((LivingEntity) entity);
			}
		}
		return super.hurt(source, f);
	}

	public boolean doHurtTarget(@NotNull Entity entity) {
		boolean bool = super.doHurtTarget(entity);
		if (bool && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity) {
			float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
			((LivingEntity)entity).addEffect(new EffectInstance(Effects.POISON, 20 * 3 * (int) f));
		}

		return bool;
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance,
										   SpawnReason spawnReason, ILivingEntityData iLivingEntityData, CompoundNBT compoundNBT) {

		this.populateDefaultEquipmentSlots(difficultyInstance);
		this.setCanPickUpLoot(true);

		return super.finalizeSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
	}

	@Override
	protected void populateDefaultEquipmentSlots(@NotNull DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		equipItemIfPossible(DMItems.GAS_MASK.get().getDefaultInstance());
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