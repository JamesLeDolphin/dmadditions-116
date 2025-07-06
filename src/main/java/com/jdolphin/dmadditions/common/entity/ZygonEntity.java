package com.jdolphin.dmadditions.common.entity;

import com.jdolphin.dmadditions.common.init.DMAEntities;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ZygonEntity extends MonsterEntity implements IVillagerDataHolder {
	public static final String TAG_DISGUISED = "Disguised";
	public static final DataParameter<Boolean> DISGUISED = EntityDataManager.defineId(ZygonEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<VillagerData> DATA_VILLAGER_DATA = EntityDataManager.defineId(ZygonEntity.class, DataSerializers.VILLAGER_DATA);

	public ZygonEntity(EntityType<ZygonEntity> type, World world) {
		super(type, world);
		this.setHealth(20.0F);
	}

	public ZygonEntity(World world) {
		super(DMAEntities.ZYGON.get(), world);
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
	public void onAddedToWorld() {
		super.onAddedToWorld();
		this.setVillagerData(new VillagerData(VillagerType.byBiome(this.level.getBiomeName(this.blockPosition())), VillagerProfession.NONE, 1));
	}

	@Override
	public boolean hurt(DamageSource source, float f) {
		Entity entity = source.getEntity();
		if (entity instanceof LivingEntity) {
			if (!source.getEntity().getType().equals(DMAEntities.ZYGON.get())) {
				this.entityData.set(DISGUISED, false);
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

			if (entity instanceof IVillagerDataHolder) {
				if (random.nextFloat() > 0.4) {
					this.setVillagerData(((IVillagerDataHolder) entity).getVillagerData());
					this.entityData.set(DISGUISED, true);
				}
			}
		}

		return bool;
	}

	protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
		if (!isDisguised()) return ActionResultType.PASS;
		this.entityData.set(DISGUISED, false);
		return ActionResultType.SUCCESS;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DISGUISED, true);
		this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
	}

	public boolean isDisguised() {
		return this.entityData.get(DISGUISED);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(10, new ZygonNearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
		this.goalSelector.addGoal(8, new ZygonNearestAttackableTargetGoal<>(this, VillagerEntity.class, false));
		this.goalSelector.addGoal(8, new ZygonNearestAttackableTargetGoal<>(this, ZombieVillagerEntity.class, false));
		this.goalSelector.addGoal(11, new MeleeAttackGoal(this, 1f, false));
		this.goalSelector.addGoal(12, new HurtByTargetGoal(this));
	}

	@Override
	public void addAdditionalSaveData(@Nonnull CompoundNBT tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean(TAG_DISGUISED, isDisguised());
		VillagerData.CODEC.encodeStart(NBTDynamicOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent((data) -> {
			tag.put("VillagerData", data);
		});
	}

	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains(TAG_DISGUISED)) {
			this.entityData.set(DISGUISED, tag.getBoolean(TAG_DISGUISED));
		}
		if (tag.contains("VillagerData", 10)) {
			DataResult<VillagerData> dataresult = VillagerData.CODEC
					.parse(new Dynamic<>(NBTDynamicOps.INSTANCE, tag.get("VillagerData")));
			dataresult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
		}
	}

	@Override
	public HandSide getMainArm() {
		return HandSide.RIGHT;
	}

	@Override
	public VillagerData getVillagerData() {
		return this.entityData.get(DATA_VILLAGER_DATA);
	}

	public void setVillagerData(VillagerData villagerData) {
		this.entityData.set(DATA_VILLAGER_DATA, villagerData);
	}

	public final class ZygonNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		private ZygonNearestAttackableTargetGoal(MobEntity entity, Class<T> target, boolean mustSee) {
			super(entity, target, mustSee);
		}

		public boolean canUse() {
			return super.canUse() && !isDisguised();
		}
	}

	protected ITextComponent getTypeName() {
		if (!this.isDisguised())
			return super.getTypeName();

		ResourceLocation profName = this.getVillagerData().getProfession().getRegistryName();
		return new TranslationTextComponent(EntityType.VILLAGER.getDescriptionId() + '.'
				+ (!"minecraft".equals(profName.getNamespace()) ? profName.getNamespace() + '.' : "")
				+ profName.getPath());
	}

}
