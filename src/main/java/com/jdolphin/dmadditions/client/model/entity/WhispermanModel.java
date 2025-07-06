package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.common.entity.WhispermanEntity;
import com.jdolphin.dmadditions.common.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.HandSide;

public class WhispermanModel extends BipedModel<WhispermanEntity> implements IModelPartReloader {

	private JSONModel model;

	public WhispermanModel(float v) {
		super(v);
		ModelReloaderRegistry.register(this);
	}

	public WhispermanModel() {
		this(1f);
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/whisperman.json"));

		ModelWrapper modelWrapper = model.getModelData().getModel();
		this.head = modelWrapper.getPart("Head");
		this.body = modelWrapper.getPart("Body");
		this.leftArm = modelWrapper.getPart("LeftArm");
		this.rightArm = modelWrapper.getPart("RightArm");
		this.leftLeg = modelWrapper.getPart("LeftLeg");
		this.rightLeg = modelWrapper.getPart("RightLeg");

		this.hat.visible = false;
	}

	@Override
	protected void setupAttackAnimation(WhispermanEntity entity, float jeffery) {
		super.setupAttackAnimation(entity, jeffery);

		if (entity.isAggressive()) {
			this.getArm(entity.isLeftHanded() ? HandSide.LEFT : HandSide.RIGHT).xRot = -1.5f;
		}
	}

}
