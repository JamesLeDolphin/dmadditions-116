package com.jdolphin.dmadditions.client.render.entity.cyber;

import com.jdolphin.dmadditions.client.model.entity.cyber.AbstractCybermanModel;
import com.jdolphin.dmadditions.common.entity.cyber.DMACybermanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DMACybermanRenderer extends LivingRenderer<DMACybermanEntity, AbstractCybermanModel> {

	public DMACybermanRenderer(EntityRendererManager manager, AbstractCybermanModel model) {
		super(manager, model, 0.5f);
	}

	protected boolean shouldShowName(DMACybermanEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(DMACybermanEntity entity) {
		return this.model.model.getModelData().getTexture();
	}
}
