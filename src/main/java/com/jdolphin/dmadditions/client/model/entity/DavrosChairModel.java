package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.VehicleEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DavrosChairModel extends SegmentedModel<VehicleEntity> implements IModelPartReloader {

	protected ModelRenderer chair;
	public JSONModel model;

	public DavrosChairModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/davros_chair.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();
		this.chair = modelWrapper.getPart("chair");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(chair);
	}

	@Override
	public void setupAnim(VehicleEntity entity, float v, float v1, float v2, float v3, float v4) {

	}
}
