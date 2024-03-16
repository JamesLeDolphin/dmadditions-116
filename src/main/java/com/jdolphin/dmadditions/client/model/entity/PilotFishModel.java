package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.PilotFishEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class PilotFishModel extends BipedModel<PilotFishEntity> implements IModelPartReloader {
	public JSONModel model;

	public PilotFishModel(float modelSize){
		super(modelSize);
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/pilot_fish/santa.json"));

		if (this.model != null) {
			this.head = this.model.getModelData().getModel().getPart("Head");
			this.body = this.model.getModelData().getModel().getPart("Body");
			this.leftArm = this.model.getModelData().getModel().getPart("LeftArm");
			this.rightArm = this.model.getModelData().getModel().getPart("RightArm");
			this.leftLeg = this.model.getModelData().getModel().getPart("LeftLeg");
			this.rightLeg = this.model.getModelData().getModel().getPart("RightLeg");
		}

	}

	@Override
	public JSONModel getModel() {
		return model;
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
	public void setupAnim(PilotFishEntity pilotFishEntity, float v, float v1, float v2, float v3, float v4) {
		super.setupAnim(pilotFishEntity, v, v1, v2, v3, v4);

		if (pilotFishEntity.isUsingItem()) {
			float f = MathHelper.sin(this.attackTime * (float)Math.PI);
			float f1 = MathHelper.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float)Math.PI);
			this.rightArm.zRot = 0.0F;
			this.leftArm.zRot = 0.0F;
			this.rightArm.yRot = -(0.1F - f * 0.6F);
			this.leftArm.yRot = 0.1F - f * 0.6F;
			this.rightArm.xRot = (-(float)Math.PI / 2F);
			this.leftArm.xRot = (-(float)Math.PI / 2F);
			this.rightArm.xRot -= f * 1.2F - f1 * 0.4F;
			this.leftArm.xRot -= f * 1.2F - f1 * 0.4F;
			ModelHelper.bobArms(this.rightArm, this.leftArm, v2);
		}
	}
}
