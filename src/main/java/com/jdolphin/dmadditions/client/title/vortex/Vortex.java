package com.jdolphin.dmadditions.client.title.vortex;

import com.jdolphin.dmadditions.DmAdditions;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Vortex{
	private ResourceLocation TEXTURE_LOCATION;
	private float distortionSpeed = 0.5f;
	private final float distortionSeparationFactor = 32f;
	private float distortionFactor = 2f;
	private float scale = 10f;
	private float rotationFactor = 1f;
	private float time = 0;
	private float rotationSpeed = 1.0f;
	private float textureRotationOffsetFactor = 0.0f;
	private float speed = 4f;

	public Vortex(float rotationFactor, String id){
		this(id);
		this.distortionFactor = rotationFactor;
	}

	public Vortex(String name) {
		TEXTURE_LOCATION = new ResourceLocation(DmAdditions.MODID, "textures/vortex/" + name + ".png");
	}

	public void render() {
		int width = Minecraft.getInstance().screen.width;
		int height = Minecraft.getInstance().screen.height;
		float scale = Math.max(width, height) / 14;

		GlStateManager._clearColor(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager._clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, true);

		GlStateManager._pushMatrix();
		GlStateManager._translatef(width / 2, height / 2, -scale);
		GlStateManager._scalef(scale, scale, 0);

		renderVortex();

		GlStateManager._popMatrix();
	}

	public void renderVortex() {
		GlStateManager._pushMatrix();
		GlStateManager._enableCull();
		GlStateManager._enableTexture();
		GlStateManager._disableLighting();

		GL11.glScaled(scale, scale, scale);

		float f0 = (float) Math.toDegrees(this.rotationFactor * Math.sin(time * this.rotationSpeed));
		float f2 = f0 / 360.0f - (int) (f0 / 360.0);
		float f3 = this.textureRotationOffsetFactor * f2 - (int) (this.textureRotationOffsetFactor * f2);
		GL11.glRotated(f2 * 360.0, 0.0, 0.0, 1.0);

		Minecraft.getInstance().textureManager.bind(TEXTURE_LOCATION);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

		for (int i = 0; i < 24; ++i) {
			this.renderSection(buffer, i, time * -this.speed, f3, (float) Math.sin(i * Math.PI / 36), (float) Math.sin((i + 1) * Math.PI / 36));
		}
		tessellator.end();

		GlStateManager._popMatrix();
		time += Minecraft.getInstance().getDeltaFrameTime() / 100;
	}

	private static float oneEight = 1/8f;
	private static float sqrt3Over2 = (float) Math.sqrt(3) / 2.0f;

	public void renderSection(BufferBuilder builder, int locationOffset, float textureDistanceOffset, float textureRotationOffset, float startScale, float endScale) {
		int verticalOffset = (locationOffset * oneEight + textureDistanceOffset > 1.0) ? locationOffset - 6 : locationOffset;
		int horizontalOffset = (textureRotationOffset > 1.0) ? -6 : 0;



		builder.vertex(0, -startScale + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + textureRotationOffset, verticalOffset * oneEight + textureDistanceOffset).endVertex();

		builder.vertex(0, -endScale + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(endScale * -sqrt3Over2, endScale * -0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(startScale * -sqrt3Over2, startScale * -0.5 + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((oneEight + textureRotationOffset > 1.0) ? -5 : 1);


		builder.vertex(startScale * -sqrt3Over2, startScale * -0.5 + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();

		builder.vertex(endScale * -sqrt3Over2, endScale * -0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(endScale * -sqrt3Over2, endScale * 0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(startScale * -sqrt3Over2, startScale * 0.5 + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((1.0f / 3.0f + textureRotationOffset > 1.0) ? -4 : 2);


		builder.vertex(startScale * -sqrt3Over2, startScale * 0.5 + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();

		builder.vertex(endScale * -sqrt3Over2, endScale * 0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();
		builder.vertex(endScale * -0.0f, endScale + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(startScale * -0.0f, startScale + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((0.5f + textureRotationOffset > 1.0) ? -3 : 3);

		builder.vertex(startScale * -0.0f, startScale + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();

		builder.vertex(endScale * -0.0f, endScale + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(endScale * sqrt3Over2, endScale * 0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(startScale * sqrt3Over2, startScale * 0.5 + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((2.0f / 3.0f + textureRotationOffset > 1.0) ? -2 : 4);


		builder.vertex(startScale * sqrt3Over2, startScale * 0.5 + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();

		builder.vertex(endScale * sqrt3Over2, endScale * 0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(endScale * sqrt3Over2, endScale * -0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(startScale * sqrt3Over2, startScale * -0.5 + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();
		horizontalOffset = ((5.0f / 6.0f + textureRotationOffset > 1.0) ? -1 : 5);


		builder.vertex(startScale * sqrt3Over2, startScale * -0.5 + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();

		builder.vertex(endScale * sqrt3Over2, endScale * -0.5 + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + 0.0f + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();

		builder.vertex(endScale * -0.0f, endScale * -1.0f + this.computeDistortionFactor(time, locationOffset + 1), -1 - locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + oneEight + textureDistanceOffset).endVertex();
		
		builder.vertex(startScale * -0.0f, startScale * -1.0f + this.computeDistortionFactor(time, locationOffset), -locationOffset)
			.uv(horizontalOffset * oneEight + oneEight + textureRotationOffset, verticalOffset * oneEight + 0.0f + textureDistanceOffset).endVertex();
	}

	private float computeDistortionFactor(float time, int t) {
		return (float) (Math.sin(time * this.distortionSpeed * 2.0 * Math.PI + (13 - t) * this.distortionSeparationFactor) * this.distortionFactor) / 8;
	}
}
