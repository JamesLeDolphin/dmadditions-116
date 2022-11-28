package com.jdolphin.dmadditions.loot.modifiers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddItemLootModifier extends LootModifier {
	protected List<ItemStack> items;

	/**
	 * Constructs a LootModifier.
	 *
	 * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
	 */
	protected AddItemLootModifier(ILootCondition[] conditionsIn, List<ItemStack> items) {
		super(conditionsIn);
		this.items = items;
	}

	@Nonnull
	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		generatedLoot.addAll(items);
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<AddItemLootModifier> {
		@Override
		public AddItemLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
			Iterator<JsonElement> itemIterator = JSONUtils.getAsJsonArray(object, "items").iterator();
			ArrayList<ItemStack> items = new ArrayList<>();

			while(itemIterator.hasNext())
				items.add(ShapedRecipe.itemFromJson((JsonObject) itemIterator.next()));

			return new AddItemLootModifier(conditionsIn, items);
		}

		@Override
		public JsonObject write(AddItemLootModifier instance) {
			Gson gson = new Gson();
			JsonObject jsonObject = new JsonObject();
			JsonArray items = new JsonArray();

			for(ItemStack item : instance.items)
				items.add(gson.toJson(item.serializeNBT()));

			jsonObject.add("items", items);
			return jsonObject;
		}
	}
}
