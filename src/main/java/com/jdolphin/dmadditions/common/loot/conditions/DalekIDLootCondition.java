package com.jdolphin.dmadditions.common.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.jdolphin.dmadditions.common.init.DMALootConditionManager;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMDalekRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;

import java.util.Set;

public class DalekIDLootCondition implements ILootCondition {
	protected String id;
	protected LootContext.EntityTarget entityTarget;

	protected DalekIDLootCondition(String type, LootContext.EntityTarget entityTarget) {
		this.id = type;
		this.entityTarget = entityTarget;
	}

	public Set<LootParameter<?>> getReferencedContextParams() {
		return ImmutableSet.of(LootParameters.ORIGIN, this.entityTarget.getParam());
	}

	@Override
	public LootConditionType getType() {
		return DMALootConditionManager.DALEK_TYPE;
	}

	@Override
	public boolean test(LootContext lootContext) {
		Entity entity = lootContext.getParamOrNull(this.entityTarget.getParam());
		if (!(entity instanceof DalekEntity)) return false;

		DalekEntity dalek = (DalekEntity) entity;
		IDalek dalekData = dalek.getDalekData();
		String dalekId = dalekData.getID();

		return dalekId.equals(this.id);
	}

	public static class Serializer implements ILootSerializer<DalekIDLootCondition> {
		public void serialize(JsonObject obj, DalekIDLootCondition condition, JsonSerializationContext context) {
			obj.addProperty("type", condition.id);
			obj.add("entity", context.serialize(condition.entityTarget));
		}

		public DalekIDLootCondition deserialize(JsonObject obj, JsonDeserializationContext context) {
			return new DalekIDLootCondition(
				DMDalekRegistry.getDalek(obj.get("type").getAsString()).getID(),
				JSONUtils.getAsObject(obj, "entity", context, LootContext.EntityTarget.class));
		}
	}
}
