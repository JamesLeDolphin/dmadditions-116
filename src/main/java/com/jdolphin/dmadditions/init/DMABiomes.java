package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMABiomes {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DmAdditions.MODID);
	public DMABiomes() {}
	@Deprecated
	public static final RegistryObject<Biome> MOON_BIOME = BIOMES.register("moon", BiomeMaker::theVoidBiome);;
	public static RegistryKey<Biome> MOON;
	public static final RegistryObject<Biome> ANDROZANIMINOR_BIOME = BIOMES.register("androzaniminor", BiomeMaker::theVoidBiome);
	public static RegistryKey<Biome> ANDROZANIMINOR;

	private static RegistryKey<Biome> makeKey(String name) {
		return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DmAdditions.MODID, name));
	}
}
