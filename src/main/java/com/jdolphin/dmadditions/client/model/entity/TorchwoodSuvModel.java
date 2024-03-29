package com.jdolphin.dmadditions.client.model.entity;


import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.TorchwoodSuvEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TorchwoodSuvModel extends SegmentedModel<TorchwoodSuvEntity> implements IModelPartReloader {

	//	protected ModelRenderer floor;
//	protected ModelRenderer roof;
	protected ModelRenderer wheels;
	protected ModelRenderer body;
	//	protected ModelRenderer leftSide;
//	protected ModelRenderer rightSide;
//	protected ModelRenderer front;
//	protected ModelRenderer back;
//	protected ModelRenderer interior;
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

//		this.floor = modelWrapper.getPart("Floor");
//		this.roof = modelWrapper.getPart("Roof");
//		this.leftSide = modelWrapper.getPart("leftside");
//		this.rightSide = modelWrapper.getPart("rightside");
//		this.front = modelWrapper.getPart("front");
//		this.back = modelWrapper.getPart("back");
//		this.interior = modelWrapper.getPart("interior");

		this.wheels = modelWrapper.getPart("Wheels");
		this.body = modelWrapper.getPart("Body");
	}

	@Override
	public Iterable<ModelRenderer> parts() {
//		return ImmutableList.of(floor, roof, leftSide, rightSide, front, back, interior);
		return ImmutableList.of(body, wheels);
	}

	@Override
	public void setupAnim(TorchwoodSuvEntity torchwoodSuvEntity, float v, float v1, float v2, float v3, float v4) {

	}
}
