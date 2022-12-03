package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.SnowmanModel;
import com.jdolphin.dmadditions.entity.SnowmanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderSnowman extends MobRenderer<SnowmanEntity, SnowmanModel> {
	public RenderSnowman(EntityRendererManager p_i50961_1_) {
		super(p_i50961_1_, new SnowmanModel(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(SnowmanEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}
