package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ShoppingCartModel;
import com.jdolphin.dmadditions.common.entity.ShoppingCartEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ShoppingCartRenderer extends MobRenderer<ShoppingCartEntity, ShoppingCartModel> {

	public ShoppingCartRenderer(EntityRendererManager entityRendererManager, ShoppingCartModel shoppingCartModel, float v) {
		super(entityRendererManager, shoppingCartModel, v);
	}

	public ShoppingCartRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new ShoppingCartModel(), 1f);
	}

	@Override
	public ResourceLocation getTextureLocation(ShoppingCartEntity entity) {
		return this.model.getModel().getModelData().getTexture();
	}
}
