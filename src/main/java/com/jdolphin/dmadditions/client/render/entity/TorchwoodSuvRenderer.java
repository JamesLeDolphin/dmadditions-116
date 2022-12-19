package com.jdolphin.dmadditions.client.render.entity;


import com.jdolphin.dmadditions.client.model.entity.TorchwoodSuvModel;
import com.jdolphin.dmadditions.entity.TorchwoodSuvEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class TorchwoodSuvRenderer extends MobRenderer<TorchwoodSuvEntity, TorchwoodSuvModel> {

	public TorchwoodSuvRenderer(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_, new TorchwoodSuvModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(TorchwoodSuvEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}
