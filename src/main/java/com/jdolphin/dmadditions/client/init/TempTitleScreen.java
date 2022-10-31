package com.jdolphin.dmadditions.client.init;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TempTitleScreen extends RenderSkyboxCube {
	private final ResourceLocation[] images = new ResourceLocation[9];
	public static float time = 0F;

	public TempTitleScreen() {
		super(new ResourceLocation("textures/gui/title/background/panorama"));

		for(int i = 0; i < 9; ++i) {
			this.images[i] = new ResourceLocation("dmadditions", "textures/gui/main/bg_" + i + ".png");
		}

	}

	protected void init() {
	}

	public void render(Minecraft p_217616_1_, float p_217616_2_, float p_217616_3_, float p_217616_4_) {
		p_217616_2_ = 0F;
		p_217616_3_ = -time;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		RenderSystem.matrixMode(5889);
		RenderSystem.pushMatrix();
		RenderSystem.loadIdentity();
		RenderSystem.multMatrix(Matrix4f.perspective(85.0, (float)p_217616_1_.getWindow().getWidth() / (float)p_217616_1_.getWindow().getHeight(), 0.05F, 10.0F));
		RenderSystem.matrixMode(5888);
		RenderSystem.pushMatrix();
		RenderSystem.loadIdentity();
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		RenderSystem.disableCull();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		boolean i = true;

		for(int j = 0; j < 4; ++j) {
			RenderSystem.pushMatrix();
			float f = ((float)(j % 2) / 2.0F - 0.5F) / 256.0F;
			float f1 = ((float)(j / 2) / 2.0F - 0.5F) / 256.0F;
			float f2 = 0.0F;
			RenderSystem.translatef(f, f1, 0.0F);
			RenderSystem.rotatef(p_217616_2_, 1.0F, 0.0F, 0.0F);
			RenderSystem.rotatef(p_217616_3_, 0.0F, 1.0F, 0.0F);

			for(int k = 0; k < 6; ++k) {
				p_217616_1_.getTextureManager().bind(this.images[k]);
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				int l = Math.round(255.0F * p_217616_4_) / (j + 1);
				if (k == 0) {
					bufferbuilder.vertex(-1.0, -1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(-1.0, 1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, 1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, -1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
				}

				if (k == 1) {
					bufferbuilder.vertex(1.0, -1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, 1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
				}

				if (k == 2) {
					bufferbuilder.vertex(1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(-1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(-1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
				}

				if (k == 3) {
					bufferbuilder.vertex(-1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(-1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(-1.0, 1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(-1.0, -1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
				}

				if (k == 4) {
					bufferbuilder.vertex(-1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(-1.0, -1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, -1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
				}

				if (k == 5) {
					bufferbuilder.vertex(-1.0, 1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(-1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
					bufferbuilder.vertex(1.0, 1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
				}

				tessellator.end();
			}

			RenderSystem.popMatrix();
			RenderSystem.colorMask(true, true, true, false);
		}

		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.matrixMode(5889);
		RenderSystem.popMatrix();
		RenderSystem.matrixMode(5888);
		RenderSystem.popMatrix();
		RenderSystem.depthMask(true);
		RenderSystem.enableCull();
		RenderSystem.enableDepthTest();
	}

	public CompletableFuture<Void> preload(TextureManager manager, Executor executor) {
		CompletableFuture<?>[] completablefuture = new CompletableFuture[6];

		for(int i = 0; i < completablefuture.length; ++i) {
			completablefuture[i] = manager.preload(this.images[i], executor);
		}

		return CompletableFuture.allOf(completablefuture);
	}
}
