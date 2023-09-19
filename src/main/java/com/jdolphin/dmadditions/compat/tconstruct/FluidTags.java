package com.jdolphin.dmadditions.compat.tconstruct;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.init.DMAFluids;
import com.jdolphin.dmadditions.init.DMATags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerTags;

public class FluidTags extends FluidTagsProvider {

	public FluidTags(DataGenerator p_i49156_1_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_i49156_1_, DmAdditions.MODID, existingFileHelper);
	}

	@Override
	public String getName() {
		return "DMA Fluid TinkerTags";
	}

	@Override
	public void addTags() {
		tagLocal(DMAFluids.molten_dalekanium);
		tagLocal(DMAFluids.molten_metalert);
		tagAll(DMAFluids.molten_steel);
		tagLocal(DMAFluids.molten_stainless_steel);
		tagLocal(DMAFluids.molten_silicon);
		this.tag(DMATags.Fluids.METALERT).addTag(DMAFluids.molten_metalert.getLocalTag());
		this.tag(DMATags.Fluids.DALEKANIUM).addTag(DMAFluids.molten_dalekanium.getLocalTag());
		this.tag(DMATags.Fluids.SILICON).addTag(DMAFluids.molten_silicon.getLocalTag());
		this.tag(DMATags.Fluids.STAINLESS_STEEL).addTag(DMAFluids.molten_stainless_steel.getLocalTag());
		this.tag(TinkerTags.Fluids.CHEAP_METAL_SPILLING).addTag(DMAFluids.molten_steel.getForgeTag()).addTag(DMAFluids.molten_stainless_steel.getLocalTag())
			.addTag(DMAFluids.molten_silicon.getLocalTag());
		this.tag(TinkerTags.Fluids.AVERAGE_METAL_SPILLING).addTag(DMAFluids.molten_dalekanium.getLocalTag());
		this.tag(TinkerTags.Fluids.EXPENSIVE_METAL_SPILLING).addTag(DMAFluids.molten_metalert.getLocalTag());
		this.tag(TinkerTags.Fluids.METAL_LIKE).addTag(DMAFluids.molten_silicon.getLocalTag()).addTag(DMAFluids.molten_metalert.getLocalTag()).addTag(DMAFluids.molten_dalekanium.getLocalTag())
			.addTag(DMAFluids.molten_steel.getForgeTag()).addTag(DMAFluids.molten_stainless_steel.getLocalTag());
	}


	private void tagLocal(FluidObject<?> fluid) {
		this.tag(fluid.getLocalTag()).add(new Fluid[]{fluid.getStill(), fluid.getFlowing()});
	}

	private void tagAll(FluidObject<?> fluid) {
		this.tagLocal(fluid);
		this.tag(fluid.getForgeTag()).addTag(fluid.getLocalTag());
	}
}
