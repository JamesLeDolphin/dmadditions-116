package com.jdolphin.dmadditions.client.event;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.client.dimension.EmptyCloudRenderer;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererGallifrey;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererMondas;
import com.jdolphin.dmadditions.common.init.DMADimensions;
import com.jdolphin.dmadditions.common.init.DMAEntities;
import com.jdolphin.dmadditions.common.init.DMAItems;
import com.jdolphin.dmadditions.common.init.DMAPackets;
import com.jdolphin.dmadditions.common.network.SBToggleLaserScrewdriverMode;
import com.swdteam.client.init.DMKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
	modid = DMAdditions.MODID,
	bus = Mod.EventBusSubscriber.Bus.FORGE,
	value = Dist.CLIENT
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
		PlayerModel<AbstractClientPlayerEntity> model = renderer.getModel();
		Entity vehicle = player.getVehicle();
		if (vehicle != null && vehicle.getType() == DMAEntities.DAVROS_CHAIR.get()) {
			model.rightLeg.visible = false;
			model.rightPants.visible = false;
			model.leftLeg.visible = false;
			model.leftPants.visible = false;
		} else {
			model.rightLeg.visible = true;
			model.rightPants.visible = true;
			model.leftLeg.visible = true;
			model.leftPants.visible = true;
		}
	}

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		PlayerEntity player = minecraft.player;
		if (player != null) {
			ItemStack stack = player.getMainHandItem();
			if (DMKeybinds.GUN_CHANGE_BULLET.consumeClick()) {
				if (stack.getItem().equals(DMAItems.LASER_SCREWDRIVER.get())) {
					SBToggleLaserScrewdriverMode packet = new SBToggleLaserScrewdriverMode();
					DMAPackets.INSTANCE.sendToServer(packet);
				}
			}
		}
	}
}
