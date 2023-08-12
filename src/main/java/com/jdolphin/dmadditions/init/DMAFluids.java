//package com.jdolphin.dmadditions.init;
//
//import com.jdolphin.dmadditions.DmAdditions;
//import net.minecraft.block.AbstractBlock;
//import net.minecraft.block.FlowingFluidBlock;
//import net.minecraft.block.material.Material;
//import net.minecraft.fluid.FlowingFluid;
//import net.minecraft.fluid.Fluid;
//import net.minecraft.util.ResourceLocation;
//
//import net.minecraftforge.fluids.FluidAttributes;
//import net.minecraftforge.fluids.ForgeFlowingFluid;
//import net.minecraftforge.fml.RegistryObject;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//
//public class DMAFluids {
//	public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
//	public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
//	public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
//
//	public static final DeferredRegister<Fluid> FLUIDS
//		= DeferredRegister.create(ForgeRegistries.FLUIDS, DmAdditions.MODID);
//
//	public static final RegistryObject<FlowingFluid> STEEL_FLUID
//		= FLUIDS.register("steel_fluid", () -> new ForgeFlowingFluid.Source(DMAFluids.STEEL_PROPERTIES));
//	public static final RegistryObject<FlowingFluid> STEEL_FLOWING
//		= FLUIDS.register("steel_flowing", () -> new ForgeFlowingFluid.Flowing(DMAFluids.STEEL_PROPERTIES));
//
//	public static final ForgeFlowingFluid.Properties STEEL_PROPERTIES = new ForgeFlowingFluid.Properties(
//		() -> STEEL_FLUID.get(), () -> STEEL_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
//		.density(15).luminosity(2).viscosity(5).overlay(WATER_OVERLAY_RL).color(0x4d4c4c)).slopeFindDistance(4).levelDecreasePerBlock(2)
//		.block(() -> DMAFluids.STEEL_BLOCK.get()).bucket(() -> DMAItems.STEEL_BUCKET.get());
//
//	public static final RegistryObject<FlowingFluidBlock> STEEL_BLOCK = DMABlocks.("steel",
//		() -> new FlowingFluidBlock(() -> DMAFluids.STEEL_FLUID.get(), AbstractBlock.Properties.of(Material.WATER)
//		));
//
//
//}
