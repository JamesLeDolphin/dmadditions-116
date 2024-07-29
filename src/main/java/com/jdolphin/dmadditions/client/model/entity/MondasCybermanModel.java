package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.cyber.MondasCybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class MondasCybermanModel extends BipedModel<MondasCybermanEntity> implements IModelPartReloader {
	public JSONModel model;

	public MondasCybermanModel(float modelSize) {
		super(modelSize);
		ModelReloaderRegistry.register(this);
	}

	public void setupAnim(MondasCybermanEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if (entityIn.hasGun()) {
			this.rightArm.xRot = -((float)Math.toRadians(90.0));
		}

	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/cyber/mondas_cyberman.json"));
		if (this.model != null) {
			ModelWrapper wrapper = this.model.getModelData().getModel();
			this.head = wrapper.getPart("head");
			this.body = wrapper.getPart("body");
			this.leftArm = wrapper.getPart("leftarm");
			this.rightArm = wrapper.getPart("rightarm");
			this.leftLeg = wrapper.getPart("leftleg");
			this.rightLeg = wrapper.getPart("rightleg");
			//this.hat.visible = false;
		}

	}
}
