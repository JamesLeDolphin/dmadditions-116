package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
//Really these are here just for refrence as we make the actual biomes with jsons
public class DMABiomes {

	//Moon
	public static final RegistryKey<Biome> MOON_BIOME = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("moon"));

	//Androzani Minor
	public static final RegistryKey<Biome> ANDROZANIMINOR_BIOME = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("androzaniminor"));

	//Mondas
	public static final RegistryKey<Biome> MONDAS_BIOME = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("bluelands"));
	public static final RegistryKey<Biome> MONDAS_FROZEN = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("mondas_frozen"));
	public static final RegistryKey<Biome> MONDAS_DEAD_FOREST = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("dead_forest"));

	//Gallifrey
	public static final RegistryKey<Biome> GALLIFREY_PLAINS = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("gallifrey_plains"));
	public static final RegistryKey<Biome> GALLIFREY_DESERT = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("gallifrey_desert"));
	public static final RegistryKey<Biome> GALLIFREY_MOUNTAINS = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("gallifrey_mountains"));
	public static final RegistryKey<Biome> GALLIFREY_FOREST = RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL("gallifrey_forest"));

}
