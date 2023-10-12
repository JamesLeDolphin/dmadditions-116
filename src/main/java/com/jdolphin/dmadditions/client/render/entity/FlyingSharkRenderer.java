package com.jdolphin.dmadditions.client.render.entity;


import com.jdolphin.dmadditions.client.model.entity.FlyingSharkModel;
import com.jdolphin.dmadditions.entity.FlyingSharkEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class FlyingSharkRenderer extends MobRenderer<FlyingSharkEntity, FlyingSharkModel> {

	public FlyingSharkRenderer(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_, new FlyingSharkModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(FlyingSharkEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}
