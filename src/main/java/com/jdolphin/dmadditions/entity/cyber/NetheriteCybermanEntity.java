package com.jdolphin.dmadditions.entity.cyber;

import com.jdolphin.dmadditions.init.DMAItems;
import com.swdteam.common.entity.*;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.StormEntity;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMParticleTypes;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTags;
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
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class NetheriteCybermanEntity extends MonsterEntity {

	public NetheriteCybermanEntity(EntityType<? extends NetheriteCybermanEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinEntity.class, 6.0F, (double) 1.0F, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, PiglinBruteEntity.class, 6.0F, (double) 1.0F, 1.2));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0F, false));
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

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.createMobAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.25F)
			.add(Attributes.ARMOR, 4)
			.add(Attributes.MAX_HEALTH, 60.0F)
			.add(Attributes.ATTACK_DAMAGE, 4.0F)
			.add(Attributes.FOLLOW_RANGE, 40.0f);
	}

	public ItemStack getPickedResult(RayTraceResult target) {
		return DMAItems.NETHERITE_CYBERMAN_SPAWNER.get().getDefaultInstance();
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(DMSoundEvents.ENTITY_CYBERMAN_STEP.get(), 0.5F, SWDMathUtils.randomRange(0.4F, 0.8F));
	}

	public void playAmbientSound() {
		if (this.tickCount >= 200) {
			this.playSound(DMSoundEvents.ENTITY_CYBERMAN_LIVING.get(), 0.5F, 0.75F);
		}
	}

	protected void playHurtSound(DamageSource source) {
		this.playSound(DMSoundEvents.ENTITY_CYBERMAN_HURT.get(), 0.5F, 0.75F);
	}

	public void die(@NotNull DamageSource source) {
		super.die(source);
		if (!this.level.isClientSide() && SWDMathUtils.randomRange(1.0F, 7.0F) == 3.0F) {
			for(int e = 0; (float)e < SWDMathUtils.randomRange(1.0F, 3.0F); ++e) {
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

	public void killed(ServerWorld level, LivingEntity target) {
		super.killed(level, target);
		if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) &&
			target instanceof VillagerEntity && ForgeEventFactory.canLivingConvert(target, DMEntities.CYBERMAN_ENTITY.get(), (timer) -> {})) {

			if (this.random.nextBoolean()) {
				return;
			}

			VillagerEntity villagerentity = (VillagerEntity)target;
			CybervillagerEntity cybermanentity = villagerentity.convertTo(DMEntities.CYBERMANVILLAGER_ENTITY.get(), false);
			if (cybermanentity != null) {
				cybermanentity.finalizeSpawn(level, level.getCurrentDifficultyAt(cybermanentity.blockPosition()), SpawnReason.CONVERSION, new ZombieEntity.GroupData(false, true), (CompoundNBT) null);
				ForgeEventFactory.onLivingConvert(target, cybermanentity);
				if (!this.isSilent()) {
					level.levelEvent(null, 1026, this.blockPosition(), 0);
				}
			}
		}

	}

	public boolean hurt(DamageSource source, float f) {
		if (source.getEntity() instanceof LivingEntity) {
			Item item = ((LivingEntity) source.getEntity()).getMainHandItem().getItem();
			boolean isGold = item.is(DMTags.Items.CYBERMAN_WEAKNESS) || item.getItem() instanceof TieredItem &&
				((TieredItem) item.getItem()).getTier().equals(ItemTier.GOLD) || item.getItem() instanceof ArmorItem &&
				((ArmorItem) item.getItem()).getMaterial().equals(ArmorMaterial.GOLD);

			if (isGold) {
				this.goldHurtEffects(f);
			}
		} else if (source.getEntity() != null && source.getEntity().getType().is(DMTags.EntityTypes.GOLD)) {
			this.goldHurtEffects(f);
		}

		return !source.isFire() && super.hurt(source, f);
	}

	private void goldHurtEffects(float attackDamage) {
		this.setHealth(this.getHealth() + attackDamage);

		if (this.level.isClientSide) {
			this.level.addParticle(DMParticleTypes.GOLD_DUST.get(),
				this.getRandomX(-0.5F), this.getY(0.5F),
				this.getRandomZ(0.5F),
				0.0F, 0.0F, 0.0F);
		}

	}
}
