package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.client.dimension.EmptyCloudRenderer;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererGallifrey;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererMondas;
import com.jdolphin.dmadditions.init.DMACapabilities;
import com.jdolphin.dmadditions.init.DMADimensions;
import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.network.SBToggleLaserScrewdriverMode;
import com.swdteam.client.init.DMKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
	modid = DMAdditions.MODID,
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
	public static void renderEvent(RenderPlayerEvent event) {
		PlayerEntity player = event.getPlayer();
		PlayerRenderer renderer = event.getRenderer();

		player.getCapability(DMACapabilities.REGEN_CAPABILITY).ifPresent(cap -> {
			int ticks = cap.getRegenTicks();
			PlayerModel<AbstractClientPlayerEntity> model = renderer.getModel();
			if (cap.isRegenerating()) {
				model.rightArm.zRot = MathHelper.lerp((float) ticks / 200, -0.2f, 0);
			} else {
				model.rightArm.zRot = 0f;
			}
		});
	}

	@SubscribeEvent
	public static void keyEvent(InputEvent.KeyInputEvent event) {
		if (DMKeybinds.GUN_CHANGE_BULLET.consumeClick()) {
			SBToggleLaserScrewdriverMode packet = new SBToggleLaserScrewdriverMode();
			DMAPackets.INSTANCE.sendToServer(packet);
		}
	}
}
