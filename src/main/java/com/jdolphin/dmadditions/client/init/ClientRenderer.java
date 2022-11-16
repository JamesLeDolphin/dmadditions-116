package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererMoon;
import com.jdolphin.dmadditions.init.DMADimensions;
import com.swdteam.client.dimension.sky.SkyRendererMCClassic;
import com.swdteam.common.init.DMDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientRenderer {
	public ClientRenderer() {
	}
	@SubscribeEvent
	public static void skyRenderer(RenderWorldLastEvent event) {
		ClientWorld world;
		if (Minecraft.getInstance().level.dimension().equals(DMADimensions.MOON)) {
			world = Minecraft.getInstance().level;
			if (world.effects().getSkyRenderHandler() == null) {
				world.effects().setSkyRenderHandler(SkyRendererMoon.INSTANCE);
			}
		}
	}
	@SubscribeEvent
	public static void skyRenderer(RenderPlayerEvent event) {
	}
}
