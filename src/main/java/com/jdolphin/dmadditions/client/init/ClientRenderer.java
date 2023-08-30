package com.jdolphin.dmadditions.client.init;

import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererMoon;
import com.jdolphin.dmadditions.init.DMADimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
	modid = "dalekmod",
	bus = Mod.EventBusSubscriber.Bus.FORGE,
	value = {Dist.CLIENT}
)
public class ClientRenderer {
	public ClientRenderer() {
	}
	@SubscribeEvent
	public static void skyRenderer(RenderWorldLastEvent event) {
		ClientWorld world;
		assert Minecraft.getInstance().level != null;
		if (Minecraft.getInstance().level.dimension().equals(DMADimensions.MOON)) {
			world = Minecraft.getInstance().level;
			if (world.effects().getSkyRenderHandler() == null) {
				world.effects().setSkyRenderHandler(SkyRendererMoon.INSTANCE);
			}
		}
	}
}
