package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.BessieModel;
import com.jdolphin.dmadditions.entity.BessieEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BessieRenderer extends MobRenderer<BessieEntity, BessieModel> {

	public BessieRenderer(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_, new BessieModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(BessieEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}
