package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.common.util.BrokenTardisType;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class DMABrokenTardisTypes {

	public static final Map<ResourceLocation, BrokenTardisType> ALL = new HashMap<>();

	public static final BrokenTardisType DEFAULT = register("default", new BrokenTardisType(new BlockPos(16, 19, 22), 0,
		getModelPath("default"), Helper.getDMASchematic("default")));

	public static final BrokenTardisType INT_2005 = register("2005", new BrokenTardisType(new BlockPos(0, 0, 0), 0,
		getModelPath("2005_police_box"), Helper.getDMASchematic("coral")));


	private static BrokenTardisType register(String name, BrokenTardisType tardis) {
		ALL.put(Helper.createAdditionsRL(name), tardis);
		return tardis;
	}

	private static ResourceLocation getModelPath(String name) {
		return Helper.createAdditionsRL("models/tileentity/tardis/abandoned/" + name + ".json");
	}

	public static BrokenTardisType getRandom() {
		List<BrokenTardisType> types = new ArrayList<>(ALL.values());
		Random random = DMAdditions.RANDOM;

		return types.get(random.nextInt(types.size()));
	}
}
