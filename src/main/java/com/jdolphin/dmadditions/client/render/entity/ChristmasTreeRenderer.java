package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ChristmasTreeModel;
import com.jdolphin.dmadditions.entity.ChristmasTreeEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ChristmasTreeRenderer extends MobRenderer<ChristmasTreeEntity, ChristmasTreeModel> {
	public ChristmasTreeRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new ChristmasTreeModel(), 0.6f);
	}

	@Override
	@Nonnull
	public ResourceLocation getTextureLocation(ChristmasTreeEntity christmasTreeEntity) {
		return this.model.model.getModelData().getTexture();
	}
}
