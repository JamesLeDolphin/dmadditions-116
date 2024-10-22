package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.MondasCybermanModel;
import com.jdolphin.dmadditions.entity.cyber.MondasCybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MondasCybermanRenderer extends BipedRenderer<MondasCybermanEntity, MondasCybermanModel> {
	protected static final ResourceLocation CYBER_TEXTURE = Helper.createAdditionsRL("textures/entity/cyber/mondas_cyberman.png");

	public MondasCybermanRenderer(EntityRendererManager renderManager) {
		super(renderManager, new MondasCybermanModel(1.0f), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(MondasCybermanEntity woodenCybermanEntity) {
		return CYBER_TEXTURE;
	}

	protected boolean shouldShowName(MondasCybermanEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || (entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity));
	}
}
