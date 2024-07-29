package com.jdolphin.dmadditions.entity;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public enum TimeLordType {
	ARI("ari"),
	VILLAGER("villager");

	private final String name;
	public final Function<Random, List<ItemStack>> getInventory;

	public ResourceLocation getModelLocation() {
		return Helper.createAdditionsRL(String.format("models/entity/timelord/%s.json", this.name));
	}

	public String getName() {
		return this.name;
	}

	TimeLordType(String name) {
		this.name = name;
		this.getInventory = (random) -> null;
	}

	TimeLordType(String name, Function<Random, List<ItemStack>> getInventory) {
		this.name = name;
		this.getInventory = getInventory;
	}

	public static TimeLordType get(String name) {
		return Arrays.stream(values())
			.filter(timeLordType -> timeLordType.name.equals(name)).findFirst()
			.orElse(ARI);
	}
}
