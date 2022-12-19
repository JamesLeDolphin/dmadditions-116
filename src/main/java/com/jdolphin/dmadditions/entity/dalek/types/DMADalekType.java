package com.jdolphin.dmadditions.entity.dalek.types;

import com.swdteam.common.entity.dalek.DalekType;

import java.util.ArrayList;
import java.util.List;

public class DMADalekType {
	public static List<DMADalekType> DMA_DALEK_TYPES = new ArrayList();
	public static DalekType SANTA = new DalekType("santa_dalek");
	public static DalekType IRONSIDE = new DalekType("ironside_dalek");
	public static DalekType CANDYCANE = new DalekType("candycane_dalek");
	public static DalekType PFD = new DalekType("pink_fluffy_dalek");
	public static DalekType STORM = new DalekType("dalek_storm");
	public static DalekType SNOW = new DalekType("snow_dalek");
	public static DalekType STEAMPUNK = new DalekType("steampunk_dalek");



	public String registryName;

	public DMADalekType(String registryName) {
		this.registryName = registryName;
		DMA_DALEK_TYPES.add((DMADalekType) DMADalekType.DMA_DALEK_TYPES);
	}

	public String getRegistryName() {
		return this.registryName;
	}
}
