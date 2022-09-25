package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.init.DMAdditionsItems;
import com.jdolphin.dmadditions.init.DMAdditionsProjectiles;
import com.jdolphin.dmadditions.init.DMAdditionsSoundEvents;

public class RegistryHandler {
	//	public static DMAdditionsBlocks dmAdditionsBlocks;
	//	public static DMAdditionsParticleTypes dmParticles;
	//	public static DMAdditionsBiomes dmBiomes;
	//	public static DMAdditionsEntities dmEntities;
	//	public static DMAdditionsBlockEntities dmTiles;
	//	public static DMAdditionsWorldCarvers dmCarvers;
	//	public static DMAdditionsCraftingTypes dmAdditionsCraftingTypes;
	public static DMAdditionsItems dmaItems;
	public static DMAdditionsSoundEvents dmaSounds;

	public static void init() {

		DMAdditionsProjectiles.init();

		dmaSounds = new DMAdditionsSoundEvents();
		dmaItems = new DMAdditionsItems();
	}
}
