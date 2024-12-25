package com.jdolphin.dmadditions.client.model.entity.cyber;

import com.jdolphin.dmadditions.entity.cyber.NetheriteCybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class NetheriteCybermanModel extends BipedModel<NetheriteCybermanEntity> implements IModelPartReloader {
	public JSONModel model;

	public NetheriteCybermanModel(float modelSize) {
		super(modelSize);
		ModelReloaderRegistry.register(this);
	}

	public void setupAnim(NetheriteCybermanEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/cyber/cyberwarrior.json"));
		if (this.model != null) {
			ModelWrapper wrapper = this.model.getModelData().getModel();
			this.head = wrapper.getPart("Head");
			this.body = wrapper.getPart("Body");
			this.leftArm = wrapper.getPart("LeftArm");
			this.rightArm = wrapper.getPart("RightArm");
			this.leftLeg = wrapper.getPart("LeftLeg");
			this.rightLeg = wrapper.getPart("RightLeg");
			this.hat.visible = false;
		}

	}
}
