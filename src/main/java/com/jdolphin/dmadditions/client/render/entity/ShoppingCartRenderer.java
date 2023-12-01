package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ShoppingCartModel;
import com.jdolphin.dmadditions.entity.ShoppingCartEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ShoppingCartRenderer extends MobRenderer<ShoppingCartEntity, ShoppingCartModel> {

	public ShoppingCartRenderer(EntityRendererManager p_i50961_1_, ShoppingCartModel p_i50961_2_, float p_i50961_3_) {
		super(p_i50961_1_, p_i50961_2_, p_i50961_3_);
	}

	public ShoppingCartRenderer(EntityRendererManager p_i50961_1_) {
		super(p_i50961_1_, new ShoppingCartModel(), 1f);
	}

	@Override
	public ResourceLocation getTextureLocation(ShoppingCartEntity entity) {
		return this.model.getModel().getModelData().getTexture();
	}
}
