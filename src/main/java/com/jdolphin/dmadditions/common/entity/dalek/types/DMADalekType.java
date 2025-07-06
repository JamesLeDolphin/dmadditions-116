package com.jdolphin.dmadditions.common.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekType;

import java.util.ArrayList;
import java.util.List;

public class DMADalekType {
	public static List<DalekType> DMA_DALEK_TYPES = new ArrayList<>();

	public static DalekType SANTA = register("santa_dalek");
	public static DalekType IRONSIDE = register("ironside_dalek");
	public static DalekType CANDYCANE = register("candycane_dalek");
	public static DalekType SNOW =register("snow_dalek");
	public static DalekType SWD =register("swd_dalek");
	public static DalekType STEAMPUNK = register("steampunk_dalek");
	public static DalekType GLASS = register("glass_dalek_with_mutant");
	public static DalekType SESAME_STREET =register("sesame_street_dalek");

	private static DalekType register(String name) {
		DalekType type = new DalekType(name);
		DMA_DALEK_TYPES.add(type);
		return type;
	}

}
