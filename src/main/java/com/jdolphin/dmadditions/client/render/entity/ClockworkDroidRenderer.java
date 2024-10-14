package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ClockworkDroidModel;
import com.jdolphin.dmadditions.entity.ClockworkDroidEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ClockworkDroidRenderer extends BipedRenderer<ClockworkDroidEntity, ClockworkDroidModel> {

	public ClockworkDroidRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new ClockworkDroidModel(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(ClockworkDroidEntity entity) {
		return Helper.createAdditionsRL("textures/entity/clockwork_droid/" + entity.getClockworkDroidType().getName() + ".png");
	}
}