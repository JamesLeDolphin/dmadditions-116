package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMABiomes {

	//Moon
	public static final RegistryKey<World> MOON_BIOME = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("moon"));

	//Androzani Minor
	public static final RegistryKey<World> ANDROZANIMINOR_BIOME = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("androzaniminor"));

	//Mondas
	public static final RegistryKey<World> MONDAS_BIOME = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("bluelands"));
	public static final RegistryKey<World> MONDAS_FROZEN = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("mondas_frozen"));
	public static final RegistryKey<World> MONDAS_DEAD_FOREST = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("dead_forest"));

	//Gallifrey
	public static final RegistryKey<World> GALLIFREY_PLAINS = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("gallifrey_plains"));
	public static final RegistryKey<World> GALLIFREY_DESERT = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("gallifrey_desert"));
	public static final RegistryKey<World> GALLIFREY_MOUNTAINS = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("gallifrey_mountains"));
	public static final RegistryKey<World> GALLIFREY_FOREST = RegistryKey.create(Registry.DIMENSION_REGISTRY, Helper.createAdditionsRL("gallifrey_forest"));

}
