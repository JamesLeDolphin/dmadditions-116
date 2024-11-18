package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.advent.AdventUnlock;
import com.jdolphin.dmadditions.init.DMAItems;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.swdteam.common.init.DMItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class EmptyVillagerEntity extends EmptyChildEntity implements IVillagerDataHolder {
	private static final DataParameter<VillagerData> DATA_VILLAGER_DATA = EntityDataManager.defineId(EmptyVillagerEntity.class, DataSerializers.VILLAGER_DATA);

	public EmptyVillagerEntity(EntityType<EmptyVillagerEntity> type, World world) {
		super(type, world);
	}


	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		equipItemIfPossible(DMItems.GAS_MASK.get().getDefaultInstance());
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundNBT tag) {
		super.addAdditionalSaveData(tag);
		VillagerData.CODEC.encodeStart(NBTDynamicOps.INSTANCE,
			this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent((data) -> tag.put("VillagerData", data));
	}

	public void readAdditionalSaveData(CompoundNBT tag) {
		super.readAdditionalSaveData(tag);

		if (tag.contains("VillagerData", 10)) {
			DataResult<VillagerData> dataresult = VillagerData.CODEC
				.parse(new Dynamic<>(NBTDynamicOps.INSTANCE, tag.get("VillagerData")));
			dataresult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
		}
	}

	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance,
										   SpawnReason spawnReason, ILivingEntityData iLivingEntityData, CompoundNBT compoundNBT) {

		this.populateDefaultEquipmentSlots(difficultyInstance);
		this.setCanPickUpLoot(true);

		return super.finalizeSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
	}

	public void setVillagerData(VillagerData villagerData) {
		this.entityData.set(DATA_VILLAGER_DATA, villagerData);
	}

	@Override
	public VillagerData getVillagerData() {
		return this.entityData.get(DATA_VILLAGER_DATA);
	}
}
