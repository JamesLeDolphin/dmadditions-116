package com.jdolphin.dmadditions.common.init;

import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DMADimensions {
	public static RegistryKey<World> MOON = createKey("moon");
	public static RegistryKey<World> WINTER_PLANET = createKey("tree_farm_457");
	public static RegistryKey<World> MONDAS = createKey("mondas");
	public static RegistryKey<World> BROKEN_TARDIS = createKey("broken_tardis");
	public static RegistryKey<World> GALLIFREY = createKey("gallifrey");

	private static RegistryKey<World> createKey(String name) {
		return RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL(name));
	}

}
