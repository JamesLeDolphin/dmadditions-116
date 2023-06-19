package com.jdolphin.dmadditions.entity;

import com.swdteam.main.DalekMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static com.jdolphin.dmadditions.init.DMAItems.PILOT_FISH_TRUMPET;

public enum PilotFishType {
	SANTA("santa", random -> {
		if (random.nextBoolean() && PILOT_FISH_TRUMPET != null)
			return Collections.singletonList(new ItemStack(PILOT_FISH_TRUMPET.get()));

		return null;
	}),
	SANTA_PILOT_FISH("santa_pilot_fish"),
	CLOAKED("cloaked");

	private final String name;
	public final Function<Random, List<ItemStack>> getInventory;

	public ResourceLocation getModelLocation() {
		return new ResourceLocation(DalekMod.MODID, String.format("models/entity/pilot_fish/%s.json", this.name));
	}

	public String getName() {
		return this.name;
	}

	PilotFishType(String name) {
		this.name = name;
		this.getInventory = (random) -> null;
	}

	PilotFishType(String name, Function<Random, List<ItemStack>> getInventory) {
		this.name = name;
		this.getInventory = getInventory;
	}

	public static PilotFishType get(String name) {
		return Arrays.stream(values())
			.filter(pilotFishType -> pilotFishType.name.equals(name)).findFirst()
			.orElse(SANTA);
	}
}
