package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.init.DMABlocks;
import com.swdteam.common.block.IRust;
import com.swdteam.common.init.DMBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.swdteam.common.block.IRust.rustedMap;

@Mixin(IRust.class)
public class AddRustMixin {
	@Inject(method = "addRustedVariants()V", at = @At("HEAD"), remap = false)

	static void addRustedVariants(CallbackInfo ci) {
		rustedMap.put(DMABlocks.STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_REINFORCED_WALLING_ROUNDEL_CONTAINER.get());
		rustedMap.put(DMABlocks.STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
		rustedMap.put(DMABlocks.FILLED_STEEL_BEAMS_ROUNDEL_CONTAINER.get(), DMABlocks.FILLED_RUSTED_STEEL_BEAMS_ROUNDEL_CONTAINER.get());
	}
}
