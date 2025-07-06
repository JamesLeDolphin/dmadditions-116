package com.jdolphin.dmadditions.common.entity;

import com.jdolphin.dmadditions.common.init.DMAEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class DalekMutantEntity extends KantrofarriEntity {

	public DalekMutantEntity(World world) {
		super(DMAEntities.DALEK_MUTANT.get(), world);
	}

	public DalekMutantEntity(EntityType<? extends DalekMutantEntity> type, World world) {
		super(type, world);
	}
}
