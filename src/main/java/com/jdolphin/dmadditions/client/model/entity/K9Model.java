package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.K9Entity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import static com.jdolphin.dmadditions.DmAdditions.MODID;


public class K9Model extends TintedAgeableModel<K9Entity> implements IModelPartReloader {
	protected JSONModel model;

	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer leg0;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer tail;

	private ModelRenderer upperBody;

	public K9Model() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(MODID, "models/entity/k9.json"));

		if(this.model != null){
			ModelWrapper modelWrapper = model.getModelData().getModel();
			this.head = modelWrapper.getPart("head");
			this.body = modelWrapper.getPart("body");
			this.leg0 = modelWrapper.getPart("leg0");
			this.leg1 = modelWrapper.getPart("leg1");
			this.leg2 = modelWrapper.getPart("leg2");
			this.leg3 = modelWrapper.getPart("leg3");
			this.tail = modelWrapper.getPart("tail");
			this.upperBody = modelWrapper.getPart("upperBody");
		}
	}

	@Override
	public JSONModel getModel() {
		return this.model;
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(body, upperBody, leg3, leg2, leg1, leg0, tail, head);
	}

	@Override
	public void setupAnim(K9Entity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}
}
