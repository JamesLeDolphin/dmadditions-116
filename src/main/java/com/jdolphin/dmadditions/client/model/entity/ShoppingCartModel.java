package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.ShoppingCartEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelRendererWrapper;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;


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
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/shopping_cart.json"));
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
	public void setupAnim(ShoppingCartEntity entity, float v, float v1, float v2, float v3, float v4) {
		this.engine.visible = entity.hasEngine();
	}
}
