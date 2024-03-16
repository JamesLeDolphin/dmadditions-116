package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.TankModel;
import com.jdolphin.dmadditions.entity.TankEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class TankRenderer<T extends TankEntity> extends MobRenderer<T, TankModel<T>>{

	public TankRenderer(EntityRendererManager entityRendererManager, TankModel<T> tTankModel, float v) {
		super(entityRendererManager, tTankModel, v);
	}

	public TankRenderer(EntityRendererManager rendererManager){
		this(rendererManager, new TankModel<>(), 1f);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_110775_1_) {
		return this.model.getModel().getModelData().getTexture(); 
	}

}
