package com.jdolphin.dmadditions.compat.tconstruct;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.init.DMAFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.data.tags.FluidTagProvider;

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
	}


	private void tagLocal(FluidObject<?> fluid) {
		this.tag(fluid.getLocalTag()).add(new Fluid[]{fluid.getStill(), fluid.getFlowing()});
	}

	private void tagAll(FluidObject<?> fluid) {
		this.tagLocal(fluid);
		this.tag(fluid.getForgeTag()).addTag(fluid.getLocalTag());
	}
}
