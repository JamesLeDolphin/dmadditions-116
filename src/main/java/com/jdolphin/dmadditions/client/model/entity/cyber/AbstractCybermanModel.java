package com.jdolphin.dmadditions.client.model.entity.cyber;

import com.jdolphin.dmadditions.common.entity.cyber.DMACybermanEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class AbstractCybermanModel extends BipedModel<DMACybermanEntity> implements IModelPartReloader {
	public JSONModel model;

	public AbstractCybermanModel(JSONModel model) {
		super(0.5f);
		this.model = model;
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
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

	@Override
	public JSONModel getModel() {
		return model;
	}
}
