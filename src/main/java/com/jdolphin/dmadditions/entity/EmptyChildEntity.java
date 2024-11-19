package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMAEntities;
import com.mojang.brigadier.LiteralMessage;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import java.util.List;

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
		if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) && target instanceof VillagerEntity) {

			VillagerEntity villagerentity = (VillagerEntity) target;
			VillagerData data = villagerentity.getVillagerData();
			EntityType<? extends EmptyChildEntity> convertToType = DMAEntities.EMPTY_VILLAGER.get();
			if (villagerentity.isBaby()) {
				convertToType = DMAEntities.EMPTY_CHILD.get();
			}
			if (ForgeEventFactory.canLivingConvert(target, convertToType, timer -> {})) {


				EmptyChildEntity emptyVillager = villagerentity.convertTo((EntityType<? extends EmptyChildEntity>) convertToType, false);
				if (emptyVillager != null) {
					emptyVillager.finalizeSpawn(level, level.getCurrentDifficultyAt(emptyVillager.blockPosition()),
						SpawnReason.CONVERSION,
						new ZombieEntity.GroupData(false, true), null);

					ForgeEventFactory.onLivingConvert(target, emptyVillager);
					if (convertToType.equals(DMAEntities.EMPTY_VILLAGER.get())) ((EmptyVillagerEntity) emptyVillager).setVillagerData(data);
					if (!this.isSilent()) {
						level.levelEvent(null, 1026, this.blockPosition(), 0);
					}
				}
			}
		}
	}

	@Override
	public void baseTick() {
		super.baseTick();
			if (level.getBlockState(this.blockPosition()).getBlock() instanceof AbstractSignBlock) {
				SignTileEntity tile = (SignTileEntity) level.getBlockEntity(this.blockPosition());
				if (tile != null) {
					System.out.println("Msg");
					tile.setMessage(0, new StringTextComponent("ARE"));
					tile.setMessage(1, new StringTextComponent("YOU"));
					tile.setMessage(2, new StringTextComponent("MY"));
					tile.setMessage(3, new StringTextComponent("MUMMY"));
				} else System.out.println("Null");
			}
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(10, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.goalSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
		this.goalSelector.addGoal(11, new MeleeAttackGoal(this, 1f, false));
		this.goalSelector.addGoal(12, new HurtByTargetGoal(this, EmptyChildEntity.class, EmptyVillagerEntity.class).setAlertOthers());
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