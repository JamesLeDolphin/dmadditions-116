package com.jdolphin.dmadditions.client.model.entity;


import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.entity.TorchwoodSuvEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

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
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(DmAdditions.MODID, "models/entity/torchwood_suv.json"));
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
	public void setupAnim(TorchwoodSuvEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}
}
