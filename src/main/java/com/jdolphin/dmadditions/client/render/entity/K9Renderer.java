package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.K9Model;
import com.jdolphin.dmadditions.entity.K9Entity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class K9Renderer extends MobRenderer<K9Entity, K9Model> {
	public K9Renderer(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_, new K9Model(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(K9Entity p_110775_1_) {
		return this.getModel().getModel().getModelData().getTexture();
	}
}
