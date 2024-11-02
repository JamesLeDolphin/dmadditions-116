package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.BessieModel;
import com.jdolphin.dmadditions.client.model.entity.DavrosChairModel;
import com.jdolphin.dmadditions.entity.VehicleEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class DavrosChairRenderer extends MobRenderer<VehicleEntity, DavrosChairModel> {

	public DavrosChairRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new DavrosChairModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(VehicleEntity bessieEntity) {
		return this.model.model.getModelData().getTexture();
	}
}
