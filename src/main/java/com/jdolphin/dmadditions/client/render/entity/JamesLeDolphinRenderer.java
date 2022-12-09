package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.JamesLeDolphinModel;
import com.jdolphin.dmadditions.entity.JamesLeDolphinEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class JamesLeDolphinRenderer extends MobRenderer<JamesLeDolphinEntity, JamesLeDolphinModel<JamesLeDolphinEntity>> {

	public JamesLeDolphinRenderer(EntityRendererManager p_i50961_1_) {
		super(p_i50961_1_, new JamesLeDolphinModel<>(), 0.6f);
	}

	@Override
	@Nonnull
	public ResourceLocation getTextureLocation(JamesLeDolphinEntity entity) {
		return this.model.model.getModelData().getTexture();
	}
}
