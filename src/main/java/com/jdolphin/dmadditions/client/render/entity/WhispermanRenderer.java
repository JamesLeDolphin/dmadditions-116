package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.WhispermanModel;
import com.jdolphin.dmadditions.common.entity.WhispermanEntity;
import com.swdteam.model.javajson.JSONModel.ModelInformation;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class WhispermanRenderer extends BipedRenderer<WhispermanEntity, WhispermanModel> {

	public WhispermanRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new WhispermanModel(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(WhispermanEntity whispermanEntity) {
		ModelInformation modelData = model.getModel().getModelData();
		return modelData.getTexture();
	}

}

