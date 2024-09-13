package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.PilotFishEntity;
import com.jdolphin.dmadditions.entity.cyber.MondasCybermanEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MondasCybermanModel extends BipedModel<MondasCybermanEntity> {
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;
	private final ModelRenderer body;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer leftArm;
	private final ModelRenderer rightArm;
	private final ModelRenderer head;

	public MondasCybermanModel(float scale) {
		super(scale);
		texWidth = 64;
		texHeight = 64;

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-1.9F, 12.0F, 0.0F);
		rightLeg.texOffs(32, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		rightLeg.texOffs(40, 48).addBox(-2.6F, 0.0F, 0.0F, 1.0F, 7.0F, 0.0F, 0.0F, false);
		rightLeg.texOffs(28, 32).addBox(-3.1F, 5.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		rightLeg.texOffs(28, 32).addBox(-3.1F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(1.9F, 12.0F, 0.0F);
		leftLeg.texOffs(16, 32).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leftLeg.texOffs(40, 48).addBox(1.6F, 0.0F, 0.0F, 1.0F, 7.0F, 0.0F, 0.0F, false);
		leftLeg.texOffs(28, 32).addBox(2.1F, 5.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		leftLeg.texOffs(28, 32).addBox(2.1F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 24.0F, 0.0F);
		body.texOffs(0, 16).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		body.texOffs(0, 48).addBox(-2.0F, -23.0F, -5.0F, 4.0F, 10.0F, 3.0F, 0.0F, false);
		body.texOffs(46, 46).addBox(-4.0F, -22.0F, -4.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
		body.texOffs(48, 0).addBox(-4.0F, -23.0F, -3.0F, 8.0F, 10.0F, 0.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, 0.48F);
		cube_r1.texOffs(30, 14).addBox(-18.0F, -19.0F, -5.0F, 6.0F, 0.0F, 6.0F, 0.0F, false);
		cube_r1.texOffs(34, 20).addBox(-5.25F, -12.0F, -5.0F, 6.0F, 0.0F, 6.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, -0.48F);
		cube_r2.texOffs(26, 8).addBox(12.125F, -19.0F, -5.0F, 6.0F, 0.0F, 6.0F, 0.0F, false);
		cube_r2.texOffs(34, 26).addBox(-0.75F, -12.0F, -5.0F, 6.0F, 0.0F, 6.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(0, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leftArm.texOffs(14, 48).addBox(-1.5F, -2.5F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, false);
		leftArm.texOffs(28, 32).addBox(3.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		leftArm.texOffs(28, 32).addBox(3.0F, 6.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(24, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		rightArm.texOffs(24, 48).addBox(-3.5F, -2.5F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, false);
		rightArm.texOffs(28, 32).addBox(-4.0F, 6.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		rightArm.texOffs(28, 32).addBox(-4.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(48, 10).addBox(-2.0F, -12.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(-1.0F, -11.0F, 1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		head.texOffs(24, 1).addBox(-6.0F, -10.5F, -1.0F, 12.0F, 7.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(MondasCybermanEntity entity,  float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.rightArm.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.leftArm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
