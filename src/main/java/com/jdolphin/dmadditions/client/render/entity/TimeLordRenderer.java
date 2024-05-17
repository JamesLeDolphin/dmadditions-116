package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.PilotFishModel;
import com.jdolphin.dmadditions.client.model.entity.TimeLordModel;
import com.jdolphin.dmadditions.entity.PilotFishEntity;
import com.jdolphin.dmadditions.entity.TimeLordEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class TimeLordRenderer extends BipedRenderer<TimeLordEntity, TimeLordModel> {

	public TimeLordRenderer(EntityRendererManager rendererManager){
		super(rendererManager, new TimeLordModel(1.0f), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(TimeLordEntity entity) {
		return this.model.model.getModelData().getTexture();
	}

	@Override
	public void render(TimeLordEntity entity, float v, float v1, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i) {
		this.model.model = ModelLoader.loadModel(entity.getTimelordType().getModelLocation());
		super.render(entity, v, v1, matrixStack, iRenderTypeBuffer, i);
	}
}
