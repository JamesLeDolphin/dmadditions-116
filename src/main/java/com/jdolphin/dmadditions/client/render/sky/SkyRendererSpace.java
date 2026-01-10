package com.jdolphin.dmadditions.client.render.sky;

import com.jdolphin.dmadditions.common.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nullable;
import java.util.Random;

public class SkyRendererSpace implements ISkyRenderHandler {
	public static SkyRendererSpace INSTANCE = new SkyRendererSpace();
	public static ResourceLocation SKY = Helper.createAdditionsRL("textures/sky/space/sky.png");
	@Nullable
	private VertexBuffer starBuffer;
	private final VertexFormat skyFormat = DefaultVertexFormats.POSITION;
	public SkyRendererSpace() {
		createStars();

	}

	public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		mc.textureManager.bind(SKY);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		Minecraft minecraft = Minecraft.getInstance();
		ActiveRenderInfo renderInfo = minecraft.gameRenderer.getMainCamera();
		Vector2f angle = new Vector2f(renderInfo.getXRot(), renderInfo.getYRot());
		MatrixStack stack = new MatrixStack();
		stack.pushPose();
		RenderSystem.enableDepthTest();
		stack.mulPose(new Quaternion(angle.x, angle.y, 0.0F, true));

		for (int i = 0; i < 6; ++i) {
			stack.pushPose();
			if (i == 1) {
				stack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
			}

			if (i == 2) {
				stack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
			}

			if (i == 3) {
				stack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
			}

			if (i == 4) {
				stack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
			}

			if (i == 5) {
				stack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
			}

			float skyDepth = 10.0F;
			skyDepth = skyDepth * (float) minecraft.options.renderDistance / 4.0F;
			Matrix4f matrix4f = stack.last().pose();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.vertex(matrix4f, -skyDepth, -skyDepth, -skyDepth).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, -skyDepth, -skyDepth, skyDepth).uv(0.0F, 2.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, skyDepth, -skyDepth, skyDepth).uv(2.0F, 2.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, skyDepth, -skyDepth, -skyDepth).uv(2.0F, 0.0F).color(255, 255, 255, 255).endVertex();
			tessellator.end();
			stack.popPose();
		}

		RenderSystem.disableDepthTest();
		stack.popPose();
		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
	}

	private void createStars() {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		if (this.starBuffer != null) {
			this.starBuffer.close();
		}

		this.starBuffer = new VertexBuffer(this.skyFormat);
		this.drawStars(bufferbuilder);
		bufferbuilder.end();
		this.starBuffer.upload(bufferbuilder);
	}

	private void drawStars(BufferBuilder p_180444_1_) {
		Random random = new Random(10842L);
		p_180444_1_.begin(7, DefaultVertexFormats.POSITION);

		for(int i = 0; i < 1500; ++i) {
			double d0 = random.nextFloat() * 2.0F - 1.0F;
			double d1 = random.nextFloat() * 2.0F - 1.0F;
			double d2 = random.nextFloat() * 2.0F - 1.0F;
			double d3 = 0.15F + random.nextFloat() * 0.1F;
			double d4 = d0 * d0 + d1 * d1 + d2 * d2;
			if (d4 < (double)1.0F && d4 > 0.01) {
				d4 = (double)1.0F / Math.sqrt(d4);
				d0 *= d4;
				d1 *= d4;
				d2 *= d4;
				double d5 = d0 * (double)100.0F;
				double d6 = d1 * (double)100.0F;
				double d7 = d2 * (double)100.0F;
				double d8 = Math.atan2(d0, d2);
				double d9 = Math.sin(d8);
				double d10 = Math.cos(d8);
				double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
				double d12 = Math.sin(d11);
				double d13 = Math.cos(d11);
				double d14 = random.nextDouble() * Math.PI * (double)2.0F;
				double d15 = Math.sin(d14);
				double d16 = Math.cos(d14);

				for(int j = 0; j < 4; ++j) {
					double d18 = (double)((j & 2) - 1) * d3;
					double d19 = (double)((j + 1 & 2) - 1) * d3;
					double d21 = d18 * d16 - d19 * d15;
					double d22 = d19 * d16 + d18 * d15;
					double d23 = d21 * d12 + (double)0.0F * d13;
					double d24 = (double)0.0F * d12 - d21 * d13;
					double d25 = d24 * d9 - d22 * d10;
					double d26 = d22 * d9 + d24 * d10;
					p_180444_1_.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
				}
			}
		}

	}
}

