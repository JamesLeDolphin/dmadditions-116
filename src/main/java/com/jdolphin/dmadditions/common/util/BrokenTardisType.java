package com.jdolphin.dmadditions.common.util;

import com.swdteam.util.world.Schematic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class BrokenTardisType {
	public final BlockPos spawnPos;
	public final float spawnRot;
	public final ResourceLocation exteriorModel;
	public final Schematic interiorSchem;

	public BrokenTardisType(BlockPos spawnPos, float spawnRot, ResourceLocation exteriorModel, Schematic interiorSchem) {
		this.spawnPos = spawnPos;
		this.spawnRot = spawnRot;
		this.exteriorModel = exteriorModel;
		this.interiorSchem = interiorSchem;
	}
}
