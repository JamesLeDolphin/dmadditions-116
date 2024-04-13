package com.jdolphin.dmadditions.mixin.other;

import com.jdolphin.dmadditions.client.title.MenuBackGround;
import com.jdolphin.dmadditions.config.DMAClientConfig;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MainMenuScreen.class)
public abstract class MainMenuScreenMixin extends Screen {

	protected MainMenuScreenMixin(ITextComponent iTextComponent) {
		super(iTextComponent);
	}

	@Mutable
	@Shadow
	@Final private static ResourceLocation PANORAMA_OVERLAY;

	@Unique
	private static void dmadditions_116$getBg(String name) {
		PANORAMA_OVERLAY = Helper.createAdditionsRL("textures/gui/main/background/" + name + ".png");
	}


	@Inject(method = "render", at = @At(value = "HEAD"))
	public void render(MatrixStack stack, int width, int height, float scale, CallbackInfo ci) {
		this.minecraft.getTextureManager().bind(PANORAMA_OVERLAY);
	}

	@Inject(method = "init", at = @At(value = "TAIL"))
	private void init(CallbackInfo ci) {
		if (DMAClientConfig.dma_classic.get()) {
			int i = new Random().nextInt(MenuBackGround.values().length);
			dmadditions_116$getBg(MenuBackGround.values()[i].getName());
    }
  }
}
