package com.jdolphin.dmadditions.client.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

public class JavaJsonModelLoaderScreen extends Screen {
	private final BlockPos pos;
	public JavaJsonModelLoaderScreen(BlockPos pos) {
		super(new TranslationTextComponent("dmadditions.gui.javajson_model_loader"));
		this.pos = pos;
	}
}
