package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.entity.HerobrineEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class HerobrineRenderer extends BipedRenderer<HerobrineEntity, BipedModel<HerobrineEntity>>{

	public HerobrineRenderer(EntityRendererManager erm, BipedModel<HerobrineEntity> model, float v) {
		super(erm, model, v);
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
		this.addLayer(new HeldItemLayer<>(this));
		this.addLayer(new HeadLayer<>(this));
		this.addLayer(new ElytraLayer<>(this));
	}

	public HerobrineRenderer(EntityRendererManager erm) {
		this(erm, new BipedModel<HerobrineEntity>(0f), 0.5f);
	}


	@Override
	public ResourceLocation getTextureLocation(HerobrineEntity entity) {
		return Helper.createAdditionsRL("textures/entity/herobrine.png");
	}

}
