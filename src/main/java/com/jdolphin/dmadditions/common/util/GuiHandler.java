package com.jdolphin.dmadditions.common.util;

import com.jdolphin.dmadditions.client.gui.JavaJsonModelLoaderScreen;
import com.jdolphin.dmadditions.client.gui.LockpickingScreen;
import com.jdolphin.dmadditions.client.gui.panels.PlayerLocatorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GuiHandler {
	public static final int PLAYER_LOCATOR = 0;

	public GuiHandler() {
	}

	@OnlyIn(Dist.CLIENT)
	private static Screen getGui(int guiID, BlockPos pos, PlayerEntity player, ItemStack stack) {
		switch (guiID) {
			case 0:
				return new PlayerLocatorScreen();
			case 1: {
				return new LockpickingScreen(pos);
			}
			case 2: {
				return new JavaJsonModelLoaderScreen(pos);
			}
			default:
				return null;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void openGui(int guiID, BlockPos pos, PlayerEntity player) {
		Minecraft.getInstance().setScreen(getGui(guiID, pos, player, null));
	}

	@OnlyIn(Dist.CLIENT)
	public static void openGui(int guiID, ItemStack stack, PlayerEntity player) {
		Minecraft.getInstance().setScreen(getGui(guiID, null, player, stack));
	}
}