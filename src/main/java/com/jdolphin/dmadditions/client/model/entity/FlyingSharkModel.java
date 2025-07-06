package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.common.entity.FlyingSharkEntity;
import com.jdolphin.dmadditions.common.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class FlyingSharkModel extends SegmentedModel<FlyingSharkEntity> implements IModelPartReloader {

	protected ModelRenderer flyingShark;
	protected ModelRenderer head;
	protected ModelRenderer mouth;
	protected ModelRenderer body;
	protected ModelRenderer tail;
	protected ModelRenderer tail1;
	protected ModelRenderer tail2;

	public JSONModel model;

	public FlyingSharkModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/flying_shark.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();
		this.flyingShark = modelWrapper.getPart("flying_shark");
		this.head = modelWrapper.getPart("head");
		this.mouth = modelWrapper.getPart("mouth");
		this.body = modelWrapper.getPart("chair");
		this.tail = modelWrapper.getPart("tail");
		this.tail1 = modelWrapper.getPart("tail1");
		this.tail2 = modelWrapper.getPart("tail2");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(flyingShark);
	}

	@Override
	public void setupAnim(FlyingSharkEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 270F);

		this.flyingShark.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 270F);

		this.flyingShark.xRot = headPitch * ((float) Math.PI / 180F);
		if (Entity.getHorizontalDistanceSqr(entity.getDeltaMovement()) > 1.0E-7D) {
			this.flyingShark.xRot += -0.05F + -0.05F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail.xRot = -0.1F * MathHelper.cos(ageInTicks * 0.3F);
			this.tail2.xRot = -0.2F * MathHelper.cos(ageInTicks * 0.3F);
		}

		if (entity.isAngry()) {
			LivingEntity target = entity.level.getNearestEntity(LivingEntity.class, EntityPredicate.DEFAULT, entity, entity.getX(), entity.getY(), entity.getZ(), entity.getBoundingBox().inflate(16));

			if (target != null && entity.distanceTo(target) <= 5F)
				this.mouth.xRot = 0.3F;

			else
				this.mouth.xRot = -0.1F * MathHelper.cos(ageInTicks * 0.3F);
		} else
			this.mouth.xRot = 0;
	}
}
