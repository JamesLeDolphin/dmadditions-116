package com.jdolphin.dmadditions.client.render.entity;

import org.jetbrains.annotations.NotNull;

import com.jdolphin.dmadditions.entity.cyber.MondasianEntity;
import com.jdolphin.dmadditions.util.Helper;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class MondasianRenderer extends BipedRenderer<MondasianEntity, PlayerModel<MondasianEntity>> {
	public MondasianRenderer(EntityRendererManager manager) {
		super(manager, new PlayerModel<>(0.0f, false), 0.5f);
	}

	protected boolean shouldShowName(MondasianEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || (entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(MondasianEntity entity) {
		return Helper.createAdditionsRL("textures/entity/mondasian/" + entity.getMondasianType().getName() + ".png");
	}
}
