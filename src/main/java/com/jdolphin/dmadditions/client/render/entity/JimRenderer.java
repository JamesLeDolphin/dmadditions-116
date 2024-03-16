package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.JimModel;
import com.jdolphin.dmadditions.entity.JimEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class JimRenderer extends BipedRenderer<JimEntity, BipedModel<JimEntity>>{

	public JimRenderer(EntityRendererManager erm, BipedModel<JimEntity> model, float v) {
		super(erm, model, v);
      this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
      this.addLayer(new HeldItemLayer<>(this));
      this.addLayer(new HeadLayer<>(this));
      this.addLayer(new ElytraLayer<>(this));
	}

	public JimRenderer(EntityRendererManager erm) {
		this(erm, new JimModel(0f), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(JimEntity entity) {
		return Helper.createAdditionsRL("textures/entity/jim.png");
	}

}
