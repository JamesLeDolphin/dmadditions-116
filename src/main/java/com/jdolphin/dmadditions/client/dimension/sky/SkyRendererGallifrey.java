package com.jdolphin.dmadditions.client.dimension.sky;

import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.init.ModClientEvents.ClientPlayerData;
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
import org.lwjgl.opengl.GL11;

public class SkyRendererGallifrey implements ISkyRenderHandler {
	public static SkyRendererGallifrey INSTANCE = new SkyRendererGallifrey();
	public static ResourceLocation SUN = Helper.createAdditionsRL("textures/sky/gallifrey/sun.png");

	public SkyRendererGallifrey() {
	}

	@SuppressWarnings("resource")
	public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
		if (ClientPlayerData.inCustomLava) {
			RenderSystem.enableFog();
		}

		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		ActiveRenderInfo renderInfo = Minecraft.getInstance().gameRenderer.getMainCamera();
		Vector2f angle = new Vector2f(renderInfo.getXRot(), renderInfo.getYRot());
		matrixStack.pushPose();
		RenderSystem.enableDepthTest();
		matrixStack.mulPose(new Quaternion(angle.x, angle.y, 0.0F, true));

		Matrix4f matrix4f = matrixStack.last().pose();
		for (int i = 0; i < 6; ++i) {
			matrixStack.pushPose();
			if (i == 1) {
				matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
			}

			if (i == 2) {
				matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
			}

			if (i == 3) {
				matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
			}

			if (i == 4) {
				matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
			}

			if (i == 5) {
				matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
			}

			float skyDepth = 10.0F;
			skyDepth = skyDepth * (float) Minecraft.getInstance().options.renderDistance / 4.0F;
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.vertex(matrix4f, -skyDepth, -skyDepth, -skyDepth).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, -skyDepth, -skyDepth, skyDepth).uv(0.0F, 2.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, skyDepth, -skyDepth, skyDepth).uv(2.0F, 2.0F).color(255, 255, 255, 255).endVertex();
			bufferbuilder.vertex(matrix4f, skyDepth, -skyDepth, -skyDepth).uv(2.0F, 0.0F).color(255, 255, 255, 255).endVertex();
			tessellator.end();
			matrixStack.popPose();
		}

		RenderSystem.disableDepthTest();
		matrixStack.popPose();
		RenderSystem.depthMask(true);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();

		matrixStack.pushPose();
		matrixStack.translate(0, 0, -100);
		RenderSystem.enableBlend();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
		mc.textureManager.bind(SUN);
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex(matrix4f, -10, 100, -10).uv(0, 0).endVertex();
		bufferbuilder.vertex(matrix4f, 10, 100, -10).uv(1, 0).endVertex();
		bufferbuilder.vertex(matrix4f, 10, 100, 10).uv(1, 1).endVertex();
		bufferbuilder.vertex(matrix4f, -10, 100, 10).uv(0, 1).endVertex();
		tessellator.end();
		GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
		RenderSystem.disableBlend();
		matrixStack.popPose();
	}
}
