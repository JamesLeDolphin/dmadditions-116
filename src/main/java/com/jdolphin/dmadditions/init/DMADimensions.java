package com.jdolphin.dmadditions.init;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DMADimensions {
	public static RegistryKey<World> MOON;
	public static RegistryKey<World> ANDROZANIMINOR;

	public DMADimensions() {
	}

	static {
		MOON = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("dalekmod", "moon"));
		ANDROZANIMINOR = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("dalekmod", "androzaniminor"));
	}
}