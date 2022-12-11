package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ChristmasTreeModel;
import com.jdolphin.dmadditions.entity.ChristmasTreeEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ChristmasTreeRenderer extends MobRenderer<ChristmasTreeEntity, ChristmasTreeModel> {
	public ChristmasTreeRenderer(EntityRendererManager p_i50961_1_) {
		super(p_i50961_1_, new ChristmasTreeModel(), 0.6f);
	}

	@Override
	@Nonnull
	public ResourceLocation getTextureLocation(ChristmasTreeEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}
