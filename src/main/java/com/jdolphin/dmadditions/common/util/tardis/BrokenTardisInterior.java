package com.jdolphin.dmadditions.common.util.tardis;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class BrokenTardisInterior {
	public final BlockPos spawnPos;
	public final float spawnRot;
	public final ResourceLocation interiorSchem;

	public BrokenTardisInterior(BlockPos spawnPos, float spawnRot, ResourceLocation interiorSchem) {
		this.spawnPos = spawnPos;
		this.spawnRot = spawnRot;
		this.interiorSchem = interiorSchem;
	}
}
