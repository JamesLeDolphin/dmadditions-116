package com.jdolphin.dmadditions.util;

import com.jdolphin.dmadditions.client.gui.panels.PlayerLocatorScreen;
import com.jdolphin.dmadditions.client.gui.vm.VortexManipulatorMainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GuiHandler {
	public static final int PLAYER_LOCATOR = 0;
	public static final int VORTEX_MANIUPULATOR = 1;

	public GuiHandler() {
	}

	@OnlyIn(Dist.CLIENT)
	private static Screen getGui(int guiID, BlockPos p, PlayerEntity player, ItemStack stack) {
		switch (guiID) {
			case 0:
				return new PlayerLocatorScreen();
			case 1:
				return new VortexManipulatorMainScreen();
			default:
				return null;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void openGui(int guiID, BlockPos p, PlayerEntity player) {
		Minecraft.getInstance().setScreen(getGui(guiID, p, player, (ItemStack) null));
	}

	@OnlyIn(Dist.CLIENT)
	public static void openGui(int guiID, ItemStack stack, PlayerEntity player) {
		Minecraft.getInstance().setScreen(getGui(guiID, (BlockPos) null, player, stack));
	}
}