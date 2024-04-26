package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.TimeLordEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

import javax.annotation.Nonnull;

public class TimeLordModel extends BipedModel<TimeLordEntity> implements IModelPartReloader {
	public JSONModel model;
	public TimeLordModel(float size) {
		super(size);
	}

	@Override
	@Nonnull
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
	}

	@Override
	@Nonnull
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/timelord/ari.json"));
		if (this.model != null) {
			this.head = this.model.getModelData().getModel().getPart("head");
			this.body = this.model.getModelData().getModel().getPart("body");
			this.leftArm = this.model.getModelData().getModel().getPart("left_arm");
			this.rightArm = this.model.getModelData().getModel().getPart("right_arm");
			this.leftLeg = this.model.getModelData().getModel().getPart("left_leg");
			this.rightLeg = this.model.getModelData().getModel().getPart("right_leg");
		}
	}

	@Override
	public JSONModel getModel() {
		return this.model;
	}
}
