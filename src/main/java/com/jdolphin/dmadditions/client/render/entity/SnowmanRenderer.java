package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.SnowmanModel;
import com.jdolphin.dmadditions.common.entity.SnowmanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.util.ResourceLocation;

public class SnowmanRenderer extends MobRenderer<SnowmanEntity, SnowmanModel> {
	public SnowmanRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new SnowmanModel(), 0.5f);
		this.addLayer(new HeadLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(SnowmanEntity snowmanEntity) {
		return this.model.model.getModelData().getTexture();
	}
}
