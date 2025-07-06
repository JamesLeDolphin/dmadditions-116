package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.BessieModel;
import com.jdolphin.dmadditions.common.entity.VehicleEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BessieRenderer extends MobRenderer<VehicleEntity, BessieModel> {

	public BessieRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new BessieModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(VehicleEntity bessieEntity) {
		return this.model.model.getModelData().getTexture();
	}
}
