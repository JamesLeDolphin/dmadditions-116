package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DMADimensions {
	public static RegistryKey<World> MOON;
	public static RegistryKey<World> ANDROZANIMINOR;

	public DMADimensions() {
	}

	static {
		MOON = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createDMRL("moon"));
		ANDROZANIMINOR = RegistryKey.create(Registry.DIMENSION_REGISTRY,Helper.createDMRL("androzaniminor"));
	}
}