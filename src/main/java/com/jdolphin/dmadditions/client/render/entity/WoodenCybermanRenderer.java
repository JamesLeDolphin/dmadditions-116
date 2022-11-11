package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.WoodenCybermanModel;
import com.jdolphin.dmadditions.entity.WoodenCybermanEntity;
import com.swdteam.client.model.ModelClassicSpider;
import com.swdteam.client.model.ModelCyberman;
import com.swdteam.common.entity.CybermanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class WoodenCybermanRenderer extends LivingRenderer<WoodenCybermanEntity, WoodenCybermanModel> {
	protected static final ResourceLocation WOODEN_CYBERMAN = new ResourceLocation("dalekmod", "textures/entity/cyber/wooden_cyberman.png");

	public WoodenCybermanRenderer(EntityRendererManager renderManager) {
		super(renderManager, new WoodenCybermanModel(1.0F), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(WoodenCybermanEntity p_110775_1_) {
		return ((WoodenCybermanModel)this.model).model.getModelData().getTexture();
	}

	protected boolean shouldShowName(WoodenCybermanEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}
}
