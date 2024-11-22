package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.advent.TimedUnlock;
import com.jdolphin.dmadditions.entity.cyber.MondasCybermanEntity;
import com.jdolphin.dmadditions.entity.cyber.MondasianEntity;
import com.jdolphin.dmadditions.entity.cyber.WoodenCybermanEntity;
import com.jdolphin.dmadditions.entity.dalek.IDalekEntityMixin;
import com.jdolphin.dmadditions.entity.timelord.TimeLordEntity;
import com.swdteam.common.entity.dalek.DalekEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.jdolphin.dmadditions.init.DMABlocks.CARVED_DALEK_PUMPKIN;


@Mixin(DalekEntity.class)
public abstract class DalekEntityMixin extends MobEntity implements IDalekEntityMixin {
	protected DalekEntityMixin(EntityType<? extends MobEntity> p_i48576_1_, World p_i48576_2_) {
		super(p_i48576_1_, p_i48576_2_);
	}

	private boolean party;
	private BlockPos jukebox;

	@Inject(at = @At("TAIL"), remap = false, method = "registerGoals()V")
	protected void registerGoals(CallbackInfo ci) {
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, TimeLordEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, MondasianEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, MondasCybermanEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, WoodenCybermanEntity.class, true));
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty,
			SpawnReason reason, ILivingEntityData data, CompoundNBT nbt) {

		if (TimedUnlock.isHalloween() && random.nextBoolean()) {
			Block block = Blocks.CARVED_PUMPKIN;
			float f = random.nextFloat();

			if (f < 0.1) {
				block = Blocks.JACK_O_LANTERN;
			} else if (f < 0.7 && CARVED_DALEK_PUMPKIN != null) {
				block = CARVED_DALEK_PUMPKIN.get();
			}
			this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(block));
			this.armorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
		}

		return super.finalizeSpawn(world, difficulty, reason, data, nbt);
	}

	@Inject(method = "aiStep", at = @At("TAIL"))
	public void aiStep(CallbackInfo ci) {
		if (this.level.isClientSide) {

			if (this.jukebox == null
				|| !this.jukebox.closerThan(this.position(), 5.0D)
				|| !this.level.getBlockState(this.jukebox).is(Blocks.JUKEBOX)) {

				this.party = false;
				this.jukebox = null;
			}
		}
	}

	@Override
	public void setRecordPlayingNearby(BlockPos blockPos, boolean b) {
		this.jukebox = blockPos;
		this.party = b;
	}

	public boolean isPartyDalek() {
		return this.party;
	}
}
