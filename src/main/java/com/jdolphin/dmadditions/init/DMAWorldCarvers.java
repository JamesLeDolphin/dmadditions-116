package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.carver.WinterPlanetCarver;
import com.jdolphin.dmadditions.carver.GallifreyCarver;
import com.jdolphin.dmadditions.carver.MoonCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMAWorldCarvers {
	public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DmAdditions.MODID);

	public static final RegistryObject<MoonCarver> MOON_CARVER = WORLD_CARVERS.register("moon_carver",
		() -> new MoonCarver(ProbabilityConfig.CODEC));

	public static final RegistryObject<WinterPlanetCarver> WINTER_PLANET_CARVER = WORLD_CARVERS.register("tree_farm_457_carver",
		() -> new WinterPlanetCarver(ProbabilityConfig.CODEC));

	public static final RegistryObject<GallifreyCarver> GALLIFREY_CARVER = WORLD_CARVERS.register("gallifrey_carver",
		() -> new GallifreyCarver(ProbabilityConfig.CODEC));

}
