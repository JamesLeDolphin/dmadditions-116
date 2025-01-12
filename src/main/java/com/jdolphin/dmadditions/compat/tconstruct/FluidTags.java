package com.jdolphin.dmadditions.compat.tconstruct;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.init.DMAFluids;
import com.jdolphin.dmadditions.init.DMATags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.TinkerTags;

public class FluidTags extends FluidTagsProvider {

	public FluidTags(DataGenerator p_i49156_1_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_i49156_1_, DMAdditions.MODID, existingFileHelper);
	}

	@Override
	public String getName() {
		return "DMA Fluid TinkerTags";
	}

	@Override
	public void addTags() {
		tagLocal(DMAFluids.MOLTEN_DALEKANIUM);
		tagLocal(DMAFluids.MOLTEN_METALERT);
		tagAll(DMAFluids.MOLTEN_STEEL);
		tagLocal(DMAFluids.MOLTEN_STAINLESS_STEEL);
		tagLocal(DMAFluids.MOLTEN_SILICON);
		this.tag(DMATags.Fluids.METALERT).addTag(DMAFluids.MOLTEN_METALERT.getLocalTag());
		this.tag(DMATags.Fluids.DALEKANIUM).addTag(DMAFluids.MOLTEN_DALEKANIUM.getLocalTag());
		this.tag(DMATags.Fluids.SILICON).addTag(DMAFluids.MOLTEN_SILICON.getLocalTag());
		this.tag(DMATags.Fluids.STAINLESS_STEEL).addTag(DMAFluids.MOLTEN_STAINLESS_STEEL.getLocalTag());
		this.tag(TinkerTags.Fluids.CHEAP_METAL_SPILLING).addTag(DMAFluids.MOLTEN_STEEL.getForgeTag()).addTag(DMAFluids.MOLTEN_STAINLESS_STEEL.getLocalTag())
			.addTag(DMAFluids.MOLTEN_SILICON.getLocalTag());
		this.tag(TinkerTags.Fluids.AVERAGE_METAL_SPILLING).addTag(DMAFluids.MOLTEN_DALEKANIUM.getLocalTag());
		this.tag(TinkerTags.Fluids.EXPENSIVE_METAL_SPILLING).addTag(DMAFluids.MOLTEN_METALERT.getLocalTag());
		this.tag(TinkerTags.Fluids.METAL_LIKE).addTag(DMAFluids.MOLTEN_SILICON.getLocalTag()).addTag(DMAFluids.MOLTEN_METALERT.getLocalTag()).addTag(DMAFluids.MOLTEN_DALEKANIUM.getLocalTag())
			.addTag(DMAFluids.MOLTEN_STEEL.getForgeTag()).addTag(DMAFluids.MOLTEN_STAINLESS_STEEL.getLocalTag());
	}


	private void tagLocal(FluidObject<?> fluid) {
		this.tag(fluid.getLocalTag()).add(fluid.getStill(), fluid.getFlowing());
	}

	private void tagAll(FluidObject<?> fluid) {
		this.tagLocal(fluid);
		this.tag(fluid.getForgeTag()).addTag(fluid.getLocalTag());
	}
}
