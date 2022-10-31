package com.jdolphin.dmadditions.client.init;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.swdteam.client.gui.title.RenderPanorama;
import net.minecraft.client.gui.screen.MainMenuScreen;

@OnlyIn(Dist.CLIENT)
public class ClassicDMTitleScreen extends Screen {
	private final ResourceLocation[] images = new ResourceLocation[9];
	private String splash;

	protected ClassicDMTitleScreen(ITextComponent p_i51108_1_) {
		super(p_i51108_1_);
	}

	public void RenderBackground() {
		for(int i = 0; i < 9; ++i) {
			this.images[i] = new ResourceLocation("dmadditions", "textures/gui/main/background" + i + ".png");
		}

	}
	public boolean isPauseScreen() {
		return false;
	}

	public boolean shouldCloseOnEsc() {
		return false;
	}
	protected void init() {
		if (this.splash == null) {
			this.splash = this.minecraft.getSplashManager().getSplash();
		}

	}
}
