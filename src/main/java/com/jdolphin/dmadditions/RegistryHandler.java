package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.init.*;

public class RegistryHandler {
	//	public static DMAdditionsBlocks dmAdditionsBlocks;
	//	public static DMAdditionsParticleTypes dmParticles;
	//	public static DMAdditionsBiomes dmBiomes;
	//	public static DMAdditionsEntities dmEntities;
	public static DMAdditionsBlockEntities dmaTiles;
	//	public static DMAdditionsWorldCarvers dmCarvers;
	//	public static DMAdditionsCraftingTypes dmAdditionsCraftingTypes;
	public static DMAdditionsItems dmaItems;
	public static DMAdditionsSoundEvents dmaSounds;
	public static DMAdditionsBlocks dmaBlocks;

	public static void init() {

		DMAdditionsProjectiles.init();


		dmaSounds = new DMAdditionsSoundEvents();
		dmaItems = new DMAdditionsItems();
		dmaBlocks = new DMAdditionsBlocks();
		dmaTiles = new DMAdditionsBlockEntities();
	}
}
