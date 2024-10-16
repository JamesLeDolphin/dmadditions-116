package com.jdolphin.dmadditions.client.dimension.sky;

import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ISkyRenderHandler;

public class SkyRendererMoon implements ISkyRenderHandler {
	public static SkyRendererMoon INSTANCE = new SkyRendererMoon();
	public static MatrixStack matrixStackIn = new MatrixStack();
	public static ResourceLocation SKY = Helper.createDMRL("textures/sky/moon.png");

	public SkyRendererMoon() {
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
		matrixStackIn.pushPose();
		RenderSystem.enableDepthTest();
		matrixStackIn.mulPose(new Quaternion(angle.x, angle.y, 0.0F, true));

		for (int i = 0; i < 6; ++i) {
			matrixStackIn.pushPose();
			if (i == 1) {
				matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
			}

			if (i == 2) {
				matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
			}

			if (i == 3) {
				matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180.0F));
			}

			if (i == 4) {
				matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
			}

			if (i == 5) {
				matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
			}

			float skyDepth = 10.0F;
			skyDepth = skyDepth * (float) minecraft.options.renderDistance / 4.0F;
			Matrix4f matrix4f = matrixStackIn.last().pose();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.vertex(matrix4f, -skyDepth, -skyDepth, -skyDepth).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, -skyDepth, -skyDepth, skyDepth).uv(0.0F, 2.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, skyDepth, -skyDepth, skyDepth).uv(2.0F, 2.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, skyDepth, -skyDepth, -skyDepth).uv(2.0F, 0.0F).color(255, 255, 255, 255).endVertex();
			tessellator.end();
			matrixStackIn.popPose();
		}

		RenderSystem.disableDepthTest();
		matrixStackIn.popPose();
		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
	}
}

