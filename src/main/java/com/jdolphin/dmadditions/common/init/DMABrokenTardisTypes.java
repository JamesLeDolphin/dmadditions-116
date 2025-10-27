package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.common.util.Helper;
import com.jdolphin.dmadditions.common.util.tardis.BrokenTardisInterior;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class DMABrokenTardisTypes {

	public static final Map<ResourceLocation, BrokenTardisInterior> INTERIORS = new HashMap<>();
	public static final Map<ResourceLocation, ResourceLocation> EXTERIORS = new HashMap<>();

	public static final BrokenTardisInterior DEFAULT = registerInterior("default", new BrokenTardisInterior(new BlockPos(16, 19, 22), 0,
		getInteriorPath("default")));
	public static final BrokenTardisInterior INT_2005 = registerInterior("2005", new BrokenTardisInterior(new BlockPos(0, 0, 0), 0,
		getInteriorPath("coral")));
	public static final BrokenTardisInterior SIDRAT = registerInterior("2005", new BrokenTardisInterior(new BlockPos(0, 0, 0), 0,
		getInteriorPath("sidrat")));

	public static ResourceLocation CORAL_EXT = registerExterior("coral", "2005_police_box");
	public static ResourceLocation DEFAULT_EXT = registerExterior("default", "default");
	public static ResourceLocation EXT_2018 = registerExterior("2018", "2018_police_box");

	private static BrokenTardisInterior registerInterior(String name, BrokenTardisInterior tardis) {
		INTERIORS.put(Helper.createAdditionsRL(name), tardis);
		return tardis;
	}

	private static ResourceLocation registerExterior(String name, String  modelLoc) {
		ResourceLocation rl = Helper.createAdditionsRL("models/tileentity/tardis/abandoned/" + modelLoc + ".json");
		EXTERIORS.put(Helper.createAdditionsRL(name), rl);
		return rl;
	}

	private static ResourceLocation getInteriorPath(String name) {
		return Helper.createAdditionsRL("tardis/" + name + "_abandoned");
	}

	public static BrokenTardisInterior getRandomInterior() {
		List<BrokenTardisInterior> types = new ArrayList<>(INTERIORS.values());
		Random random = DMAdditions.RANDOM;
		return types.get(random.nextInt(types.size()));
	}
	public static ResourceLocation getRandomExterior() {
		List<ResourceLocation> types = new ArrayList<>(EXTERIORS.values());
		Random random = DMAdditions.RANDOM;
		return types.get(random.nextInt(types.size()));
	}
}
