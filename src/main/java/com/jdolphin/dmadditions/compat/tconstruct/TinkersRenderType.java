package com.jdolphin.dmadditions.compat.tconstruct;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class TinkersRenderType {
	public static void setTranslucent(slimeknights.mantle.registration.object.FluidObject<?> fluid) {
		RenderTypeLookup.setRenderLayer(fluid.getStill(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(fluid.getFlowing(), RenderType.translucent());
	}
}
