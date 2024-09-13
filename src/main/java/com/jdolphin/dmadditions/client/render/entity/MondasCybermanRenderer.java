package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.MondasCybermanModel;
import com.jdolphin.dmadditions.entity.cyber.MondasCybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class MondasCybermanRenderer extends EntityRenderer<MondasCybermanEntity> {
	private MondasCybermanModel model;
	protected static final ResourceLocation CYBER_TEXTURE = Helper.createAdditionsRL("textures/entity/cyber/mondas_cyberman.png");

	public MondasCybermanRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		this.model = new MondasCybermanModel(1.0f);
	}

	@Override
	public void render(MondasCybermanEntity entity, float v, float v1, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i) {
		matrixStack.pushPose();

		this.model.renderToBuffer(matrixStack, iRenderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(CYBER_TEXTURE)),
			i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		matrixStack.popPose();
		super.render(entity, v, v1, matrixStack, iRenderTypeBuffer, i);
	}

	@Override
	public ResourceLocation getTextureLocation(MondasCybermanEntity woodenCybermanEntity) {
		return CYBER_TEXTURE;
	}

	protected boolean shouldShowName(MondasCybermanEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}
}
