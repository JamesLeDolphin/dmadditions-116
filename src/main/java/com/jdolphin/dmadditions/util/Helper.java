package com.jdolphin.dmadditions.util;

import com.jdolphin.dmadditions.DMAdditions;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.main.DalekMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.World;

import java.awt.*;

public class Helper {
	public static ResourceLocation BUTTONS_LOCATION = Helper.createAdditionsRL("textures/gui/buttons.png");
	public static void renderWidgets(MatrixStack stack, int pMouseX, int pMouseY, float pPartialTick, Widget... widgets) {
		for (Widget widget : widgets) {
			widget.render(stack, pMouseX, pMouseY, pPartialTick);
		}
	}

	public static void drawWhiteString(MatrixStack stack, String text, int x, int y) {
		AbstractGui.drawString(stack, Minecraft.getInstance().font, text, x, y, Color.WHITE.getRGB());
	}

	public static void drawWhiteCenteredString(MatrixStack stack, ITextComponent text, int x, int y) {
		drawWhiteCenteredString(stack, text.getString(), x, y);
	}

	public static void drawWhiteCenteredString(MatrixStack stack, String text, int x, int y) {
		AbstractGui.drawCenteredString(stack, Minecraft.getInstance().font, text, x, y, Color.WHITE.getRGB());
	}

	public static void renderTooltip(Screen screen, MatrixStack stack, ITextComponent component, Widget widget) {
		if (widget.isHovered()) screen.renderTooltip(stack, component, widget.x, widget.y);
	}

	public static Style getStyle(Screen screen, int x, int y) {
		return screen.getMinecraft().gui.getChat().getClickedComponentStyleAt(x, y);
	}


	public static ResourceLocation createAdditionsRL(String string) {
		return new ResourceLocation(DMAdditions.MODID, string);
	}

	public static ResourceLocation createDMRL(String string) {
		return new ResourceLocation(DalekMod.MODID, string);
	}

	public static boolean isTardis(World world) {
		return world.dimension().equals(DMDimensions.TARDIS);
	}

	public static Vector3d blockPosToVec3(BlockPos pos) {
		return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
	}

	public static BlockPos vec3ToBlockPos(Vector3d vec) {
		return new BlockPos(vec.x, vec.y, vec.z);
	}

	public static void playSound(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory category) {
		playSound(world, pos, soundEvent, category, 1.0f);
	}

	public static void playSound(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory category, float pitch) {
		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), soundEvent, category, 1.0f, pitch);
	}

	public static int minutes(int min) {
		return seconds(60) * min;
	}

	public static int seconds(int s) {
		return 20 * s;
	}

	public static void print(Object obj) {
		System.out.println(obj);
	}

	public static void info(Object obj) {
		DMAdditions.LOGGER.info(obj);
	}

	public static void warn(Object obj) {
		DMAdditions.LOGGER.warn(obj);
	}

	public static void addEntities(World world, Entity... entities) {
		for (Entity entity : entities) {
			world.addFreshEntity(entity);
		}
	}
}
