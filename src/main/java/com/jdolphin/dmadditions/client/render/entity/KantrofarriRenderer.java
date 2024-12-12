package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.KantrofarriModel;
import com.jdolphin.dmadditions.entity.KantrofarriEntity;
import com.swdteam.model.javajson.JSONModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class KantrofarriRenderer extends MobRenderer<KantrofarriEntity, KantrofarriModel> {
	public KantrofarriRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new KantrofarriModel(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull KantrofarriEntity kantrofarriEntity) {
		JSONModel.ModelInformation modelData = model.getModel().getModelData();
		return modelData.getTexture();
	}
}
