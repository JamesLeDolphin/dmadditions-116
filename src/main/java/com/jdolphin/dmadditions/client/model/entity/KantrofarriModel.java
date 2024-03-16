package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.KantrofarriEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class KantrofarriModel extends SegmentedModel<KantrofarriEntity> implements IModelPartReloader {

	protected ModelRenderer body;
	protected ModelRenderer tail;
	protected ModelRenderer leg1;
	protected ModelRenderer leg2;
	protected ModelRenderer leg3;
	protected ModelRenderer leg4;
	protected ModelRenderer leg5;



	public JSONModel model;

	public KantrofarriModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/kantrofarri.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.body = modelWrapper.getPart("body");
		this.tail = modelWrapper.getPart("tail");
		this.leg1 = modelWrapper.getPart("leg1");
		this.leg2 = modelWrapper.getPart("leg2");
		this.leg3 = modelWrapper.getPart("leg3");
		this.leg4 = modelWrapper.getPart("leg4");
		this.leg5 = modelWrapper.getPart("leg5");
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(body, tail, leg1, leg2, leg3, leg4, leg5);
	}

	@Override
	public void setupAnim(KantrofarriEntity entity, float v, float v1, float v2, float v3, float v4) {
		if(!entity.isAggressive()){
			this.body.zRot = (float)(Math.PI);
			this.body.y = 24;

			this.tail.zRot = (float)(Math.PI);
			this.tail.y = 24;
			this.tail.xRot = -(float)(Math.PI/8);

			this.leg1.xRot = -(float)(Math.PI);
			this.leg1.y = 23;
			this.leg1.zRot = (float)(Math.PI/6);

			this.leg2.xRot = -(float)(Math.PI);
			this.leg2.y = 23;
			this.leg2.zRot = -(float)(Math.PI/6);

			this.leg3.zRot = (float)(Math.PI);
			this.leg3.y = 23;
			this.leg3.xRot = (float)(Math.PI/8);
			this.leg3.yRot = -(float)(Math.PI/6);

			this.leg4.zRot = (float)(Math.PI);
			this.leg4.y = 23;
			this.leg4.xRot = (float)(Math.PI/8);
			this.leg4.yRot = (float)(Math.PI/6);

			this.leg5.zRot = (float)(Math.PI);
			this.leg5.y = 23;
			this.leg5.xRot = (float)(Math.PI/8);
		}
		else{
			baseAnim();
		}
	}

	private void baseAnim(){
		this.body.zRot = 0;
		this.body.y = 22;

		this.tail.zRot = 0;
		this.tail.y = 22;
		this.tail.xRot = 0;

		this.leg1.xRot = 0;
		this.leg1.y = 22;
		this.leg1.zRot = 0;

		this.leg2.xRot = 0;
		this.leg2.y = 22;
		this.leg2.zRot = 0;

		this.leg3.zRot = 0;
		this.leg3.y = 22;
		this.leg3.xRot = 0;
		this.leg3.yRot = (float)(Math.PI/6);

		this.leg4.zRot = 0;
		this.leg4.y = 22;
		this.leg4.xRot = 0;
		this.leg4.yRot = -(float)(Math.PI/6);

		this.leg5.zRot = 0;
		this.leg5.y = 22;
		this.leg5.xRot = 0;
	}

}
