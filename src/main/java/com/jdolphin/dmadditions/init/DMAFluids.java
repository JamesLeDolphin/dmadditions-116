package com.jdolphin.dmadditions.init;


import com.jdolphin.dmadditions.DMAdditions;
import net.minecraft.block.material.Material;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.ModelFluidAttributes;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;

public class DMAFluids {
	public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(DMAdditions.MODID);

	public static final FluidObject<ForgeFlowingFluid> MOLTEN_DALEKANIUM = FLUIDS.register("molten_dalekanium", hotBuilder().temperature(1234), Material.LAVA, 9);
	public static final FluidObject<ForgeFlowingFluid> MOLTEN_STEEL = FLUIDS.register("molten_steel", hotBuilder().temperature(1234), Material.LAVA, 9);
	public static final FluidObject<ForgeFlowingFluid> MOLTEN_STAINLESS_STEEL = FLUIDS.register("MOLTEN_STAINLESS_STEEL", hotBuilder().temperature(1234), Material.LAVA, 9);
	public static final FluidObject<ForgeFlowingFluid> MOLTEN_METALERT = FLUIDS.register("MOLTEN_METALERT", hotBuilder().temperature(1234), Material.LAVA, 9);
	public static final FluidObject<ForgeFlowingFluid> MOLTEN_SILICON = FLUIDS.register("MOLTEN_SILICON", hotBuilder().temperature(1234), Material.LAVA, 9);


	private static FluidAttributes.Builder hotBuilder() {
		return ModelFluidAttributes.builder().density(2000).viscosity(10000).temperature(1000).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA);
	}
}
