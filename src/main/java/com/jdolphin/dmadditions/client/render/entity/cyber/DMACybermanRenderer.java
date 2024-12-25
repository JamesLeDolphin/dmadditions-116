package com.jdolphin.dmadditions.client.render.entity.cyber;

import com.jdolphin.dmadditions.entity.cyber.DMACybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DMACybermanRenderer extends LivingRenderer<DMACybermanEntity, BipedModel<DMACybermanEntity>> {

	public DMACybermanRenderer(EntityRendererManager manager, BipedModel<DMACybermanEntity> model) {
		super(manager, model, 0.5f);
	}

	protected boolean shouldShowName(DMACybermanEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(DMACybermanEntity entity) {
		return Helper.createAdditionsRL("textures/entity/cyber/" + entity.getCybermanType().getName() + ".png");
	}
}
