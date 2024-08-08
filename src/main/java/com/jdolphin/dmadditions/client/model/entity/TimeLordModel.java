package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.timelord.TimeLordEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class TimeLordModel extends BipedModel<TimeLordEntity> implements IModelPartReloader {
	public JSONModel model;
	public TimeLordModel(float size) {
		super(size);
		ModelReloaderRegistry.register(this);
	}

	@Override
	@Nonnull
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
	}

	@Override
	public void setupAnim(TimeLordEntity timeLord, float v, float v1, float v2, float v3, float v4) {
		super.setupAnim(timeLord, v, v1, v2, v3, v4);

		if (timeLord.isUsingItem()) {
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

	@Override
	@Nonnull
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/timelord/ari.json"));

		if (this.model != null) {
			ModelWrapper wrapper = this.model.getModelData().getModel();
			this.head = wrapper.getPart("head");
			this.body = wrapper.getPart("body");
			this.leftArm = wrapper.getPart("left_arm");
			this.rightArm = wrapper.getPart("right_arm");
			this.leftLeg = wrapper.getPart("left_leg");
			this.rightLeg = wrapper.getPart("right_leg");
		}
	}

	@Override
	public JSONModel getModel() {
		return this.model;
	}
}
