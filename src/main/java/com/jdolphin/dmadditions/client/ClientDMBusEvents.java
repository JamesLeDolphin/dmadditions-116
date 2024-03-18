package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.client.dimension.EmptyCloudRenderer;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererMoon;
import com.jdolphin.dmadditions.init.DMADimensions;
import com.swdteam.main.DalekMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
	modid = DalekMod.MODID,
	bus = Mod.EventBusSubscriber.Bus.FORGE,
	value = {Dist.CLIENT}
)
public class ClientDMBusEvents {

	public ClientDMBusEvents() {}

	@SubscribeEvent
	public static void skyRenderer(RenderWorldLastEvent event) {
		ClientWorld world;
		Minecraft minecraft = Minecraft.getInstance();
		assert minecraft.level != null;
		if (minecraft.level.dimension().equals(DMADimensions.MOON)) {
			world = minecraft.level;
			ISkyRenderHandler handler = world.effects().getSkyRenderHandler();
			if (handler == null) {
				world.effects().setSkyRenderHandler(SkyRendererMoon.INSTANCE);
				world.effects().setCloudRenderHandler(EmptyCloudRenderer.INSTANCE);
			}
		}
	}
}
