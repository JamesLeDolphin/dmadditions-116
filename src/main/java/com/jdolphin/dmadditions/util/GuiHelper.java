package com.jdolphin.dmadditions.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.fml.client.gui.widget.Slider;

import java.awt.*;

//Copied from portal gun mod code, credit: JamesLeDolphin (me)
public class GuiHelper {

	public static void renderWidgets(MatrixStack stack, int pMouseX, int pMouseY, float pPartialTick, Widget... widgets) {
		for (Widget widget : widgets) {
			widget.render(stack, pMouseX, pMouseY, pPartialTick);
		}
	}

	public static void updateSliders(Slider... sliders) {
		for (Slider slider : sliders) {
			slider.updateSlider();
		}
	}

	public static void drawWhiteString(MatrixStack stack, String text, int x, int y) {
		AbstractGui.drawString(stack, Minecraft.getInstance().font, text, x, y, Color.WHITE.getRGB());
	}

	public static void drawWhiteString(MatrixStack stack, ITextComponent text, int x, int y) {
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
}
