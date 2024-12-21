package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.TimeLordModel;
import com.jdolphin.dmadditions.entity.timelord.TimeLordEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class TimeLordRenderer extends BipedRenderer<TimeLordEntity, TimeLordModel> {

	public TimeLordRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new TimeLordModel(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(TimeLordEntity entity) {
		return Helper.createAdditionsRL("textures/entity/timelord/" + entity.getTimelordType().getName() + ".png");
	}
}
