package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.client.init.DMAEntityRenderRegistry;
import com.jdolphin.dmadditions.init.*;
import com.swdteam.common.init.DMDalekRegistry;

public class RegistryHandler {
	//	public static DMAdditionsBlocks dmAdditionsBlocks;
	//	public static DMAdditionsParticleTypes dmParticles;
	public static DMABiomes dmaBiomes;
	//public static DMAEntities dmaEntities;
	public static DMABlockEntities dmaTiles;
	//	public static DMAdditionsWorldCarvers dmCarvers;
	//	public static DMAdditionsCraftingTypes dmAdditionsCraftingTypes;
	public static DMAEntityRenderRegistry dmaEntityRenderer;
	public static DMAItems dmaItems;
	public static DMASoundEvents dmaSounds;
	public static DMABlocks dmaBlocks;


	public static void init() {
		//DMADalekRegistry.init();
		DMAProjectiles.init();
		DMAEntities.init();
		DMASpawnerRegistry.init();

		dmaBiomes = new DMABiomes();
		dmaSounds = new DMASoundEvents();
		dmaItems = new DMAItems();
		dmaBlocks = new DMABlocks();
		dmaTiles = new DMABlockEntities();
		dmaEntityRenderer = new DMAEntityRenderRegistry();
	}
}
