package com.jdolphin.dmadditions.client.model.entity.cyber;

import com.jdolphin.dmadditions.entity.cyber.DMACybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class InvasionCybermanModel extends BipedModel<DMACybermanEntity> implements IModelPartReloader {
	public JSONModel model;

	public InvasionCybermanModel() {
		super(0.5f);
		ModelReloaderRegistry.register(this);
	}

	public void setupAnim(DMACybermanEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/cyber/invasion_cyberman.json"));
		if (this.model != null) {
			ModelWrapper wrapper = this.model.getModelData().getModel();
			this.head = wrapper.getPart("head");
			this.body = wrapper.getPart("torso");
			this.leftArm = wrapper.getPart("rightarm");
			this.rightArm = wrapper.getPart("leftarm");
			this.leftLeg = wrapper.getPart("rightleg");
			this.rightLeg = wrapper.getPart("leftleg");
			this.hat.visible = false;
		}

	}
}
