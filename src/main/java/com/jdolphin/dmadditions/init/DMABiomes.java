package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class DMABiomes {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DmAdditions.MODID);
	public DMABiomes() {}
	@Deprecated
	public static final RegistryObject<Biome> MOON_BIOME = BIOMES.register("moon", BiomeMaker::theVoidBiome);;
	public static RegistryKey<Biome> MOON;
	public static final RegistryObject<Biome> ANDROZANIMINOR_BIOME = BIOMES.register("androzaniminor", BiomeMaker::theVoidBiome);
	public static RegistryKey<Biome> ANDROZANIMINOR;
	public static final RegistryObject<Biome> MONDAS_BIOME = BIOMES.register("bluelands", BiomeMaker::theVoidBiome);
	public static final RegistryObject<Biome> MONDAS_FROZEN = BIOMES.register("mondas_frozen", BiomeMaker::theVoidBiome);
	public static final RegistryObject<Biome> MONDAS_DEAD_FOREST = BIOMES.register("dead_forest", BiomeMaker::theVoidBiome);
	public static RegistryKey<Biome> MONDAS;

	public static RegistryKey<Biome> GALIFREY;
	public static final RegistryObject<Biome> GALIFREY_PLAINS = BIOMES.register("galifrey_plains", BiomeMaker::theVoidBiome);
	public static final RegistryObject<Biome> GALIFREY_DESERT = BIOMES.register("galifrey_desert", BiomeMaker::theVoidBiome);
	public static final RegistryObject<Biome> GALIFREY_MOUNTAINS = BIOMES.register("galifrey_mountains", BiomeMaker::theVoidBiome);
	public static final RegistryObject<Biome> GALIFREY_FOREST = BIOMES.register("galifrey_forest", BiomeMaker::theVoidBiome);


	private static RegistryKey<Biome> makeKey(String name) {
		return RegistryKey.create(Registry.BIOME_REGISTRY, Helper.createAdditionsRL(name));
	}
}
