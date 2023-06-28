package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.CyberDroneModel;
import com.jdolphin.dmadditions.entity.CyberDroneEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class CyberDroneRenderer extends LivingRenderer<CyberDroneEntity, CyberDroneModel> {

	public CyberDroneRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new CyberDroneModel(), 1.0f);
	}
	protected boolean shouldShowName(CyberDroneEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}

	@Override
	public ResourceLocation getTextureLocation(CyberDroneEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}