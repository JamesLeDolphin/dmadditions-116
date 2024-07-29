package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.dimension.EmptyCloudRenderer;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererGallifrey;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererMondas;
import com.jdolphin.dmadditions.init.DMADimensions;
import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.SBToggleLaserScrewdriverMode;
import com.swdteam.client.init.DMKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
	modid = DmAdditions.MODID,
	bus = Mod.EventBusSubscriber.Bus.FORGE,
	value = {Dist.CLIENT}
)
public class ClientForgeEvents {

	@SubscribeEvent
	public static void skyRenderer(RenderWorldLastEvent event) {
		ClientWorld world;
		Minecraft minecraft = Minecraft.getInstance();
		assert minecraft.level != null;
		world = minecraft.level;
		DimensionRenderInfo info = world.effects();
		if (info.getSkyRenderHandler() == null) {
			if (minecraft.level.dimension().equals(DMADimensions.MONDAS)) {
				info.setSkyRenderHandler(SkyRendererMondas.INSTANCE);
				info.setCloudRenderHandler(EmptyCloudRenderer.INSTANCE);
			}
			if (minecraft.level.dimension().equals(DMADimensions.GALLIFREY)) {
				info.setSkyRenderHandler(SkyRendererGallifrey.INSTANCE);
			}
		}
	}

	@SubscribeEvent
	public static void keyEvent(InputEvent.KeyInputEvent event) {
		if (DMKeybinds.GUN_CHANGE_BULLET.consumeClick()) {
			SBToggleLaserScrewdriverMode packet = new SBToggleLaserScrewdriverMode();
			DMAPackets.INSTANCE.sendToServer(packet);
		}
	}
}
