package com.jdolphin.dmadditions.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Bus.FORGE)
public class SonicBlasterItemHandler {
	public static final KeyBinding RESTORE_MODE = new KeyBinding("key.dmadditions.restore_mode", GLFW.GLFW_KEY_R, "key.categories.gameplay");

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		if (RESTORE_MODE.consumeClick()) {
			PlayerEntity player = Minecraft.getInstance().player;
			SonicBlasterItem.toggleRestoreMode(player);
		}
	}
}