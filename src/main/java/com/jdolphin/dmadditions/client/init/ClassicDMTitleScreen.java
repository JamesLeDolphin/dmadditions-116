package com.jdolphin.dmadditions.client.init;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClassicDMTitleScreen extends Screen {
	private static final ResourceLocation[] IMAGES = new ResourceLocation[9];
	public static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation("textures/gui/options_background.png");
	private String splash;

	protected ClassicDMTitleScreen(ITextComponent p_i51108_1_) {
		super(p_i51108_1_);
	}

	public void RenderBackground() {
		for(int i = 0; i < 9; ++i) {
			this.IMAGES[i] = new ResourceLocation("dmadditions", "textures/gui/main/bg_" + i + ".png");
		}

	}
	/*public void renderBg(int p_231165_1_) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		this.minecraft.getTextureManager().bind(BACKGROUND_LOCATION);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f = 32.0F;
		bufferbuilder.begin(7, this.IMAGES);
		bufferbuilder.vertex(0.0D, (double)this.height, 0.0D).uv(0.0F, (float)this.height / 32.0F + (float)p_231165_1_).color(64, 64, 64, 255).endVertex();
		bufferbuilder.vertex((double)this.width, (double)this.height, 0.0D).uv((float)this.width / 32.0F, (float)this.height / 32.0F + (float)p_231165_1_).color(64, 64, 64, 255).endVertex();
		bufferbuilder.vertex((double)this.width, 0.0D, 0.0D).uv((float)this.width / 32.0F, (float)p_231165_1_).color(64, 64, 64, 255).endVertex();
		bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0.0F, (float)p_231165_1_).color(64, 64, 64, 255).endVertex();
		tessellator.end();
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent(this, new MatrixStack()));
	}*/

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
