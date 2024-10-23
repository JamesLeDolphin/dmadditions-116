package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.init.DMAItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static com.jdolphin.dmadditions.init.DMAItems.SNOWMAN_SPAWNER;

public class SnowmanEntity extends MonsterEntity implements IForgeShearable {

	public SnowmanEntity(EntityType<? extends SnowmanEntity> type, World level) {
		super(type, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.addBehaviourGoals();
	}

	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, () -> false));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.2)
			.add(Attributes.MAX_HEALTH, 4.0)
			.add(Attributes.ATTACK_DAMAGE, 2.0)
			.add(Attributes.FOLLOW_RANGE, 20.0);
	}

	@Override
	public ItemStack getPickedResult(RayTraceResult target) {
		if (SNOWMAN_SPAWNER == null) return null;

		return new ItemStack(SNOWMAN_SPAWNER.get());
	}

	public boolean isSensitiveToWater() {
		return true;
	}

	@Nullable
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SNOW_GOLEM_AMBIENT;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.SNOW_GOLEM_HURT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.SNOW_GOLEM_DEATH;
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance instance) {
		super.populateDefaultEquipmentSlots(instance);

		TimedUnlock.handlePumpkinHead(this);

		if (random.nextFloat() <= 0.25 && DMAItems.SANTA_HAT != null) {
			this.equipItemIfPossible(new ItemStack(DMAItems.SANTA_HAT.get()));
		}
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance dif, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT tag) {
		data = super.finalizeSpawn(world, dif, reason, data, tag);
		this.populateDefaultEquipmentSlots(dif);
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * dif.getSpecialMultiplier());

		return data;
	}

	@Override
	public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
		if (!reason.equals(SpawnReason.NATURAL))
			return super.checkSpawnRules(world, reason);

		BlockPos blockPos = blockPosition();
		IChunk chunk = world.getChunk(blockPos);

		boolean noVillages = chunk.getReferencesForFeature(Structure.VILLAGE).isEmpty();
		if (noVillages) return false;

		if (world.getBiome(blockPos).getTemperature(blockPos) >= 0.15F) return false;

		return super.checkSpawnRules(world, reason);
	}

	@Nonnull
	@Override
	public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
		ItemStack hat = this.getItemBySlot(EquipmentSlotType.HEAD);

		this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(Items.AIR));
		world.playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, SoundCategory.PLAYERS, 1, 1);
		return Collections.singletonList(hat);
	}

	@Override
	public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
		return !this.getItemBySlot(EquipmentSlotType.HEAD).isEmpty();
	}
}
