package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.RacnossModel;
import com.jdolphin.dmadditions.entity.RacnossEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RacnossRenderer extends MobRenderer<RacnossEntity, RacnossModel> {

	public RacnossRenderer(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_, new RacnossModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(RacnossEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}
