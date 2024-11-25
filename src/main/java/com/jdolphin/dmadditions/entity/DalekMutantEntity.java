package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.init.DMAEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

public class DalekMutantEntity extends KantrofarriEntity {

	public DalekMutantEntity(EntityType<? extends MonsterEntity> entityType, World world) {
		super(entityType, world);
	}

	public DalekMutantEntity(World world) {
		super(DMAEntities.DALEK_MUTANT.get(), world);
	}
}
