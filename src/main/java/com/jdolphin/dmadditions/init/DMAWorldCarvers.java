package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.carver.AndrozaniminorCarver;
import com.jdolphin.dmadditions.carver.GallifreyCarver;
import com.jdolphin.dmadditions.carver.MoonCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMAWorldCarvers {
	public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DmAdditions.MODID);

	public static final RegistryObject<MoonCarver> CARVER = WORLD_CARVERS.register("moon_carver",
		() -> new MoonCarver(ProbabilityConfig.CODEC));

	public static final RegistryObject<AndrozaniminorCarver> ANDROZANIMINOR_CARVER = WORLD_CARVERS.register("androzaniminor_carver",
		() -> new AndrozaniminorCarver(ProbabilityConfig.CODEC));

	public static final RegistryObject<AndrozaniminorCarver> MONDAS_CARVER = WORLD_CARVERS.register("mondas_carver",
		() -> new AndrozaniminorCarver(ProbabilityConfig.CODEC));

	public static final RegistryObject<GallifreyCarver> GALLIFREY_CARVER = WORLD_CARVERS.register("gallifrey_carver",
		() -> new GallifreyCarver(ProbabilityConfig.CODEC));

}
