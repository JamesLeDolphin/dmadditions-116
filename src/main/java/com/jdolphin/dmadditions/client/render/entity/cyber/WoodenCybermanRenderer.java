package com.jdolphin.dmadditions.client.render.entity.cyber;

import com.jdolphin.dmadditions.client.model.entity.cyber.WoodenCybermanModel;
import com.jdolphin.dmadditions.entity.cyber.WoodenCybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class WoodenCybermanRenderer extends LivingRenderer<WoodenCybermanEntity, WoodenCybermanModel> {
	protected static final ResourceLocation WOODEN_CYBERMAN = Helper.createAdditionsRL("textures/entity/cyber/wooden_cyberman.png");

	public WoodenCybermanRenderer(EntityRendererManager renderManager) {
		super(renderManager, new WoodenCybermanModel(1.0F), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(WoodenCybermanEntity woodenCybermanEntity) {
		return this.model.model.getModelData().getTexture();
	}

	protected boolean shouldShowName(WoodenCybermanEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}
}
