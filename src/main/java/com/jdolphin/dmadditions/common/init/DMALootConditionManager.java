package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.common.loot.conditions.DalekIDLootCondition;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.registry.Registry;

public class DMALootConditionManager {
	public static LootConditionType DALEK_TYPE;

	public static void init() {
		DALEK_TYPE = register("dalek_type", new DalekIDLootCondition.Serializer());
	}

	private static LootConditionType register(String name, ILootSerializer<? extends ILootCondition> iLootSerializer) {
		return Registry.register(Registry.LOOT_CONDITION_TYPE, Helper.createAdditionsRL(name), new LootConditionType(iLootSerializer));
	}
}
