package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.entity.ShoppingCartEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelRendererWrapper;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;


public class ShoppingCartModel extends SegmentedModel<ShoppingCartEntity> implements IModelPartReloader {

	protected ModelRenderer wheels;
	protected ModelRenderer body;
	protected ModelRendererWrapper engine;
	protected ModelRendererWrapper handle;
	protected ModelRendererWrapper frameBottom;
	protected ModelRendererWrapper chair;

	public JSONModel model;
	public ShoppingCartModel() {
		super();
		ModelReloaderRegistry.register(this);
	}
	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(DmAdditions.MODID, "models/entity/shopping_cart.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();
		this.wheels = modelWrapper.getPart("Wheels");
		this.engine = modelWrapper.getPart("Engine");
		this.body = modelWrapper.getPart("TrollyBody");
		this.engine = modelWrapper.getPart("Engine");
		this.handle = modelWrapper.getPart("Handle");
		this.frameBottom = modelWrapper.getPart("FrameBottom");
		this.chair = modelWrapper.getPart("Chair");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(wheels, body, engine, handle, frameBottom, chair);
	}

	@Override
	public void setupAnim(ShoppingCartEntity entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		this.engine.visible = entity.hasEngine();
	}
}