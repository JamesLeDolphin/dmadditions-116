package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.RacnossModel;
import com.jdolphin.dmadditions.common.entity.RacnossEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RacnossRenderer extends MobRenderer<RacnossEntity, RacnossModel> {

	public RacnossRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new RacnossModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(RacnossEntity racnossEntity) {
		return this.model.model.getModelData().getTexture();
	}
}
