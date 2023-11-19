package com.jdolphin.dmadditions.entity.dalek;

import com.jdolphin.dmadditions.init.DMADalekRegistry;
import com.jdolphin.dmadditions.init.DMAProjectiles;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMProjectiles;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

public class GlassDalek extends DalekEntity {
	public GlassDalek(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
	}
}
