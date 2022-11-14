package com.jdolphin.dmadditions.client.clientinit;

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
		if (Minecraft.getInstance().level.dimension().equals(DMDimensions.CLASSIC)) {
			world = Minecraft.getInstance().level;
			if (world.effects().getSkyRenderHandler() == null) {
				world.effects().setSkyRenderHandler(SkyRendererMCClassic.INSTANCE);
			}
		}
	}
	@SubscribeEvent
	public static void skyRenderer(RenderPlayerEvent event) {
	}
}
