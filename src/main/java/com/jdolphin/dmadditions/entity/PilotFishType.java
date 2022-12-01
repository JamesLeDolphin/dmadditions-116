package com.jdolphin.dmadditions.entity;

import com.swdteam.main.DalekMod;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public enum PilotFishType {
	SANTA("santa"),
	SANTA_PILOT_FISH("santa_pilot_fish"),
	CLOAKED("cloaked");

	private final String name;

	public ResourceLocation getModelLocation() {
		return new ResourceLocation(DalekMod.MODID, String.format("models/entity/pilot_fish/%s.json", this.name));
	}

	public String getName() {
		return this.name;
	}

	PilotFishType(String name) {
		this.name = name;
	}

	public static PilotFishType get(String name) {
		return Arrays.stream(values())
			.filter(pilotFishType -> pilotFishType.name.equals(name)).findFirst()
			.orElse(SANTA);
	}
}
