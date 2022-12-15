package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.entity.BessieEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;


public class BessieModel extends SegmentedModel<BessieEntity> implements IModelPartReloader {

	protected ModelRenderer wheels;
	protected ModelRenderer body;
	public JSONModel model;

	public BessieModel(){
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(wheels, body);
	}

	@Override
	public void setupAnim(BessieEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	@Override
	public void init() {

		this.model = ModelLoader.loadModel(new ResourceLocation(DmAdditions.MODID, "models/entity/bessie.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.wheels = modelWrapper.getPart("wheels");
		this.body = modelWrapper.getPart("body");
	}
}
