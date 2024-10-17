package com.jdolphin.dmadditions.client;

import com.jdolphin.dmadditions.DMAdditions;
import com.jdolphin.dmadditions.client.dimension.EmptyCloudRenderer;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererGallifrey;
import com.jdolphin.dmadditions.client.dimension.sky.SkyRendererMondas;
import com.jdolphin.dmadditions.init.DMADimensions;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.init.DMAPackets;
import com.jdolphin.dmadditions.item.SonicBlasterItem;
import com.jdolphin.dmadditions.item.SonicShadesItem;
import com.jdolphin.dmadditions.network.SBSonicInteractPacket;
import com.jdolphin.dmadditions.network.SBToggleLaserScrewdriverMode;
import com.swdteam.client.init.DMGuiHandler;
import com.swdteam.client.init.DMKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(
	modid = DMAdditions.MODID,
	bus = Mod.EventBusSubscriber.Bus.FORGE,
	value = {Dist.CLIENT}
)
public class ClientForgeEvents {
	public static final KeyBinding SONIC_SHADE_INTERACTION = new KeyBinding("key.dmadditions.sonic_shade_interaction", GLFW.GLFW_KEY_R, "key.categories.gameplay");

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
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
		ItemStack stack = player.getMainHandItem();
		if (DMKeybinds.GUN_CHANGE_BULLET.consumeClick()) {
			if (stack.getItem().equals(DMAItems.LASER_SCREWDRIVER.get())) {
				SBToggleLaserScrewdriverMode packet = new SBToggleLaserScrewdriverMode();
				DMAPackets.INSTANCE.sendToServer(packet);
			}
		}
		if (DMKeybinds.GUN_CHANGE_BULLET.consumeClick()) {
				if (DMAItems.SONIC_BLASTER != null && stack.getItem().equals(DMAItems.SONIC_BLASTER.get())) {
					SonicBlasterItem.toggleRestoreMode(stack);
				}
			}
		if (SONIC_SHADE_INTERACTION.consumeClick()) {
			if (DMAItems.SONIC_SHADES != null && player.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(DMAItems.SONIC_SHADES.get())) {
				SBSonicInteractPacket packet = new SBSonicInteractPacket();
				DMAPackets.INSTANCE.sendToServer(packet);
				if (player.isShiftKeyDown()) DMGuiHandler.openGui(12, player.getMainHandItem(), player);
				}
			}
		}
	}
}
