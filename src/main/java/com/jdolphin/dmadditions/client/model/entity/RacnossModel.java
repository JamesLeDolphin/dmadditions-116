package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.entity.RacnossEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RacnossModel extends SegmentedModel<RacnossEntity> implements IModelPartReloader {
	public JSONModel model;

	public ModelRenderer all;
	public ModelRenderer body;
	public ModelRenderer head;

	public ModelRenderer leg0;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer leg5;
	public ModelRenderer leg6;
	public ModelRenderer leg7;

	public ModelRenderer rightArm;
	public ModelRenderer leftArm;
	public BipedModel.ArmPose leftArmPose = BipedModel.ArmPose.EMPTY;
	public BipedModel.ArmPose rightArmPose = BipedModel.ArmPose.EMPTY;

	public RacnossModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(DmAdditions.MODID, "models/entity/racnoss.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.all = modelWrapper.getPart("all");
		this.body = modelWrapper.getPart("Body");
		this.head = modelWrapper.getPart("Head");

		this.leg0 = modelWrapper.getPart("RightLeg1");
		this.leg1 = modelWrapper.getPart("LeftLeg3");
		this.leg2 = modelWrapper.getPart("RightLeg2");
		this.leg3 = modelWrapper.getPart("LeftLeg2");
		this.leg4 = modelWrapper.getPart("RightLeg3");
		this.leg5 = modelWrapper.getPart("LeftLeg1");
		this.leg6 = modelWrapper.getPart("RightLeg4");
		this.leg7 = modelWrapper.getPart("LeftLeg4");

		this.rightArm = modelWrapper.getPart("RightArm");
		this.leftArm = modelWrapper.getPart("LeftArm");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(all);
	}

	@Override
	public void setupAnim(RacnossEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		entity.setupAnim(this, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	public HandSide getAttackArm(RacnossEntity p_217147_1_) {
		HandSide handside = p_217147_1_.getMainArm();
		return p_217147_1_.swingingArm == Hand.MAIN_HAND ? handside : handside.getOpposite();
	}
	public ModelRenderer getArm(HandSide p_187074_1_) {
		return p_187074_1_ == HandSide.LEFT ? this.leftArm : this.rightArm;
	}

	public void poseRightArm(RacnossEntity entity) {
		switch (this.rightArmPose) {
			case EMPTY:
				this.rightArm.yRot = 0.0F;
				break;
			case BLOCK:
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.9424779F;
				this.rightArm.yRot = (-(float) Math.PI / 6F);
				break;
			case ITEM:
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - ((float) Math.PI / 10F);
				this.rightArm.yRot = 0.0F;
				break;
			case THROW_SPEAR:
				this.rightArm.xRot = this.rightArm.xRot * 0.5F - (float) Math.PI;
				this.rightArm.yRot = 0.0F;
				break;
			case BOW_AND_ARROW:
				this.rightArm.yRot = -0.1F + this.head.yRot;
				this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
				this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				break;
			case CROSSBOW_CHARGE:
				ModelHelper.animateCrossbowCharge(this.rightArm, this.leftArm, entity, true);
				break;
			case CROSSBOW_HOLD:
				ModelHelper.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
		}

	}

	public void poseLeftArm(RacnossEntity entity) {
		switch (this.leftArmPose) {
			case EMPTY:
				this.leftArm.yRot = 0.0F;
				break;
			case BLOCK:
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
				this.leftArm.yRot = ((float) Math.PI / 6F);
				break;
			case ITEM:
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - ((float) Math.PI / 10F);
				this.leftArm.yRot = 0.0F;
				break;
			case THROW_SPEAR:
				this.leftArm.xRot = this.leftArm.xRot * 0.5F - (float) Math.PI;
				this.leftArm.yRot = 0.0F;
				break;
			case BOW_AND_ARROW:
				this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
				this.leftArm.yRot = 0.1F + this.head.yRot;
				this.rightArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				this.leftArm.xRot = (-(float) Math.PI / 2F) + this.head.xRot;
				break;
			case CROSSBOW_CHARGE:
				ModelHelper.animateCrossbowCharge(this.rightArm, this.leftArm, entity, false);
				break;
			case CROSSBOW_HOLD:
				ModelHelper.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
		}

	}
}
