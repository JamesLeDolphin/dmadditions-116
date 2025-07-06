package com.jdolphin.dmadditions.client.model.entity;


import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.common.entity.VehicleEntity;
import com.jdolphin.dmadditions.common.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TorchwoodSuvModel extends SegmentedModel<VehicleEntity> implements IModelPartReloader {

	protected ModelRenderer wheels;
	protected ModelRenderer body;
	public JSONModel model;

	public TorchwoodSuvModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/torchwood_suv.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.wheels = modelWrapper.getPart("Wheels");
		this.body = modelWrapper.getPart("Body");
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(body, wheels);
	}

	@Override
	public void setupAnim(VehicleEntity torchwoodSuvEntity, float v, float v1, float v2, float v3, float v4) {

	}
}
