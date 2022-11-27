package com.jdolphin.dmadditions;

import com.jdolphin.dmadditions.client.init.DMAEntityRenderRegistry;
import com.jdolphin.dmadditions.dimension.Gravity;
import com.jdolphin.dmadditions.init.*;
import com.swdteam.common.init.DMDalekRegistry;

public class RegistryHandler {
	//	public static DMAdditionsBlocks dmAdditionsBlocks;
	//	public static DMAdditionsParticleTypes dmParticles;
	public static DMABiomes dmaBiomes;
	//public static DMAEntities dmaEntities;
	public static DMABlockEntities dmaTiles;
	public static DMAWorldCarvers dmaCarvers;
	//	public static DMAdditionsCraftingTypes dmAdditionsCraftingTypes;
	public static DMAItems dmaItems;
	public static DMASoundEvents dmaSounds;
	public static DMABlocks dmaBlocks;


	public static void init() {
		DMAProjectiles.init();
		DMAEntities.init();

		dmaCarvers = new DMAWorldCarvers();
		dmaBiomes = new DMABiomes();
		dmaSounds = new DMASoundEvents();
		dmaItems = new DMAItems();
		dmaBlocks = new DMABlocks();
		dmaTiles = new DMABlockEntities();
	}
}
