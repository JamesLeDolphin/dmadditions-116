package com.jdolphin.dmadditions.client.render.entity;


import com.jdolphin.dmadditions.client.model.entity.FlyingSharkModel;
import com.jdolphin.dmadditions.entity.FlyingSharkEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FlyingSharkRenderer extends MobRenderer<FlyingSharkEntity, FlyingSharkModel> {

	public FlyingSharkRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new FlyingSharkModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(FlyingSharkEntity flyingSharkEntity) {
		return this.model.model.getModelData().getTexture();
	}
}
