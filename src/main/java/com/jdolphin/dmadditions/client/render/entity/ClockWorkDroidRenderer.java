package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ClockWorkDroidModel;
import com.jdolphin.dmadditions.entity.ClockWorkDroidEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ClockWorkDroidRenderer extends BipedRenderer<ClockWorkDroidEntity, ClockWorkDroidModel> {

	public ClockWorkDroidRenderer(EntityRendererManager rendererManager){
		super(rendererManager, new ClockWorkDroidModel(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(ClockWorkDroidEntity entity) {
		return Helper.createAdditionsRL("textures/entity/clock_work_droid/" + entity.getClockWorkDroidType().getName() + ".png");
	}
}