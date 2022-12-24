package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.init.*;

public class RegistryHandler {
	//	public static DMAdditionsBlocks dmAdditionsBlocks;
	//	public static DMAdditionsParticleTypes dmParticles;
	//	public static DMAdditionsBiomes dmBiomes;
	//	public static DMAdditionsEntities dmEntities;
	public static DMABlockEntities dmaTiles;
	//	public static DMAdditionsWorldCarvers dmCarvers;
	//	public static DMAdditionsCraftingTypes dmAdditionsCraftingTypes;
	public static DMAItems dmaItems;
	public static DMASoundEvents dmaSounds;
	public static DMABlocks dmaBlocks;
	public static DMADaleks dmaDaleks;


	public static void init() {

		DMAProjectiles.init();


		dmaSounds = new DMASoundEvents();
		dmaItems = new DMAItems();
		dmaBlocks = new DMABlocks();
		dmaTiles = new DMABlockEntities();
		dmaDaleks = new DMADaleks();
	}
}
