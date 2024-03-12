package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.WoodenCybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class WoodenCybermanModel extends BipedModel<WoodenCybermanEntity> implements IModelPartReloader {
	public JSONModel model;

	public WoodenCybermanModel(float modelSize) {
		super(modelSize);
		ModelReloaderRegistry.register(this);
	}

	public void setupAnim(WoodenCybermanEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/cyber/wooden_cyberman.json"));
		if (this.model != null) {
			this.head = this.model.getModelData().getModel().getPart("Head");
			this.body = this.model.getModelData().getModel().getPart("Body");
			this.leftArm = this.model.getModelData().getModel().getPart("LeftArm");
			this.rightArm = this.model.getModelData().getModel().getPart("RightArm");
			this.leftLeg = this.model.getModelData().getModel().getPart("LeftLeg");
			this.rightLeg = this.model.getModelData().getModel().getPart("RightLeg");
			this.hat.visible = false;
		}

	}
}
