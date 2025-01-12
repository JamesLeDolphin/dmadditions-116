package com.jdolphin.dmadditions.client.model.entity.cyber;

import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;

public class InvasionCybermanModel extends AbstractCybermanModel {

	public InvasionCybermanModel() {
		super(ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/cyber/invasion_cyberman.json")));
		ModelReloaderRegistry.register(this);
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
