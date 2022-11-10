package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.init.DMABlocks;
import com.swdteam.common.block.IRust;

public interface IRustToo extends IRust {
	static void addRustedVariants() {
		rustedMap.put(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
		rustedMap.put(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
		rustedMap.put(DMABlocks.FILLED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.FILLED_RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
		rustedMap.put(DMABlocks.STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get());

	}

}
