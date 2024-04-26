package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.PilotFishModel;
import com.jdolphin.dmadditions.entity.RegeneratingEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;

public abstract class RegeneratingEntityRenderer<E extends RegeneratingEntity, M extends BipedModel<E>> extends BipedRenderer<E, M> {

	public RegeneratingEntityRenderer(EntityRendererManager manager, M model, float shadowRadius) {
		super(manager, model, shadowRadius);
	}
}
