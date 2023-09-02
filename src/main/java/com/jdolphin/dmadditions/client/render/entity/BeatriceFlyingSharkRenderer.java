package com.jdolphin.dmadditions.client.render.entity;


import com.jdolphin.dmadditions.client.model.entity.BeatriceFlyingSharkModel;

import com.jdolphin.dmadditions.entity.BeatriceFlyingSharkEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BeatriceFlyingSharkRenderer extends MobRenderer<BeatriceFlyingSharkEntity, BeatriceFlyingSharkModel> {

	public BeatriceFlyingSharkRenderer(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_, new BeatriceFlyingSharkModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(BeatriceFlyingSharkEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}
