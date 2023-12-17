package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.KantrofarriModel;
import com.jdolphin.dmadditions.client.model.entity.WhispermanModel;
import com.jdolphin.dmadditions.entity.KantrofarriEntity;
import com.jdolphin.dmadditions.entity.WhispermanEntity;
import com.swdteam.model.javajson.JSONModel;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class KantrofarriRenderer extends MobRenderer<KantrofarriEntity, KantrofarriModel> {
	public KantrofarriRenderer(EntityRendererManager p_i46168_1_) {
		super(p_i46168_1_, new KantrofarriModel(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(KantrofarriEntity p_110775_1_) {
		JSONModel.ModelInformation modelData = model.getModel().getModelData();
		return modelData.getTexture();
	}
}
