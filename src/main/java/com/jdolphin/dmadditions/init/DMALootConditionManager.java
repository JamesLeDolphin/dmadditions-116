package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.loot.conditions.DalekIDLootCondition;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.registry.Registry;

public class DMALootConditionManager{
	public static final LootConditionType DALEK_TYPE = register("dalek_type", new DalekIDLootCondition.Serializer());

	private static LootConditionType register(String name, ILootSerializer<? extends ILootCondition> iLootSerializer) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, Helper.createAdditionsRL(name), new LootConditionType(iLootSerializer));
	}
}
