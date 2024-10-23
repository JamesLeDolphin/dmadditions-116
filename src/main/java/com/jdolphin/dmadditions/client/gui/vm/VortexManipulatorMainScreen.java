package com.jdolphin.dmadditions.client.gui.vm;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;

public class VortexManipulatorMainScreen extends Screen {
	protected VortexManipulatorMainScreen() {
		super(new TranslationTextComponent("screen.dmadditions.vortex_manipulator"));
	}

	public boolean isPauseScreen() {
		return false;
	}

	protected void init() {}

	public void render(@NotNull MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		super.render(stack, mouseX, mouseY, partialTicks);
	}
}
