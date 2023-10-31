package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.swdteam.common.RegistryHandler;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.fml.RegistryObject;

public class DMABiomes {
	public DMABiomes() {}
	@Deprecated
	public static final RegistryObject<Biome> MOON_BIOME = RegistryHandler.BIOMES.register("moon", BiomeMaker::theVoidBiome);;
	public static RegistryKey<Biome> MOON;
	public static final RegistryObject<Biome> ANDROZANIMINOR_BIOME = RegistryHandler.BIOMES.register("androzaniminor", BiomeMaker::theVoidBiome);
	public static RegistryKey<Biome> ANDROZANIMINOR;

	private static RegistryKey<Biome> makeKey(String name) {
		return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DmAdditions.MODID, name));
	}
}
