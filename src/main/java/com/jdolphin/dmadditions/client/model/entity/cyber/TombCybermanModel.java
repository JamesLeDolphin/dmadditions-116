package com.jdolphin.dmadditions.client.model.entity.cyber;

import com.jdolphin.dmadditions.common.util.Helper;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;

public class TombCybermanModel extends AbstractCybermanModel {

	public TombCybermanModel() {
		super(ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/cyber/tomb_cyberman.json")));
		ModelReloaderRegistry.register(this);
	}

	public void init() {
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
