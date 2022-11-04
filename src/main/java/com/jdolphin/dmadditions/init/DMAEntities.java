package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.entity.WoodenCybermanEntity;
import com.swdteam.common.RegistryHandler;
import com.swdteam.common.entity.CybermanEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class DMAEntities {
	public static final RegistryObject<EntityType<WoodenCybermanEntity>> WOODEN_CYBERMAN_ENTITY;

	static {
		WOODEN_CYBERMAN_ENTITY = RegistryHandler.ENTITY_TYPES.register("wooden_cyberman", () -> EntityType.Builder.of(WoodenCybermanEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.9F).build((new ResourceLocation("dalekmod", "wooden_cyberman")).toString()));
	}
}
