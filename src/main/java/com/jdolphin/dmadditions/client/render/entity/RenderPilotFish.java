package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.PilotFishModel;
import com.jdolphin.dmadditions.entity.PilotFishEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderPilotFish extends BipedRenderer<PilotFishEntity, PilotFishModel> {
	public RenderPilotFish(EntityRendererManager rendererManager){
		super(rendererManager, new PilotFishModel(1.0f), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(PilotFishEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}

	@Override
	public void render(PilotFishEntity entity, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
		this.model.model = ModelLoader.loadModel(entity.getPilotFishType().getModelLocation());
		super.render(entity, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
	}
}
