package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.client.dimension.EmptyCloudRenderer;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererMondas;
import com.jdolphin.dmadditions.init.DMADimensions;
import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.SBToggleLaserScrewdriverMode;
import com.swdteam.client.gui.GuiDMU;
import com.swdteam.client.init.DMKeybinds;
import com.swdteam.main.DMConfig;
import com.swdteam.util.helpers.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.EventPriority;
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
		if (minecraft.level.dimension().equals(DMADimensions.MONDAS)) {
			world = minecraft.level;
			DimensionRenderInfo info = world.effects();
			if (info.getSkyRenderHandler() == null) {
				info.setSkyRenderHandler(SkyRendererMondas.INSTANCE);
				info.setCloudRenderHandler(EmptyCloudRenderer.INSTANCE);
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
