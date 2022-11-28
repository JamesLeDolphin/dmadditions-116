package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.loot.conditions.DalekIDLootCondition;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class DMALootConditionManager{
	public static final LootConditionType DALEK_TYPE = register("dalek_type", new DalekIDLootCondition.Serializer());

	private static LootConditionType register(String p_237475_0_, ILootSerializer<? extends ILootCondition> p_237475_1_) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(p_237475_0_), new LootConditionType(p_237475_1_));
	}
}
