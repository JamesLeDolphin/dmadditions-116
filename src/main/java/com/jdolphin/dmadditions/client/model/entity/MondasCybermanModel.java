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
	private final ModelRenderer rightleg;
	private final ModelRenderer leftleg;
	private final ModelRenderer body;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer leftarm;
	private final ModelRenderer rightarm;
	private final ModelRenderer head;

	public MondasCybermanModel(float scale) {
		super(scale);
		texWidth = 64;
		texHeight = 64;

		rightleg = new ModelRenderer(this);
		rightleg.setPos(-1.9F, 12.0F, 0.0F);
		rightleg.texOffs(32, 32).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		rightleg.texOffs(40, 48).addBox(-2.6F, 0.0F, 0.0F, 1.0F, 7.0F, 0.0F, 0.0F, false);
		rightleg.texOffs(28, 32).addBox(-3.1F, 5.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		rightleg.texOffs(28, 32).addBox(-3.1F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		leftleg = new ModelRenderer(this);
		leftleg.setPos(1.9F, 12.0F, 0.0F);
		leftleg.texOffs(16, 32).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leftleg.texOffs(40, 48).addBox(1.6F, 0.0F, 0.0F, 1.0F, 7.0F, 0.0F, 0.0F, false);
		leftleg.texOffs(28, 32).addBox(2.1F, 5.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		leftleg.texOffs(28, 32).addBox(2.1F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

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

		leftarm = new ModelRenderer(this);
		leftarm.setPos(5.0F, 2.0F, 0.0F);
		leftarm.texOffs(0, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leftarm.texOffs(14, 48).addBox(-1.5F, -2.5F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, false);
		leftarm.texOffs(28, 32).addBox(3.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		leftarm.texOffs(28, 32).addBox(3.0F, 6.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		rightarm = new ModelRenderer(this);
		rightarm.setPos(-5.0F, 2.0F, 0.0F);
		rightarm.texOffs(24, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		rightarm.texOffs(24, 48).addBox(-3.5F, -2.5F, 0.0F, 5.0F, 10.0F, 0.0F, 0.0F, false);
		rightarm.texOffs(28, 32).addBox(-4.0F, 6.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		rightarm.texOffs(28, 32).addBox(-4.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(48, 10).addBox(-2.0F, -12.0F, -3.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(-1.0F, -11.0F, 1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
		head.texOffs(24, 1).addBox(-6.0F, -10.5F, -1.0F, 12.0F, 7.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(MondasCybermanEntity cyber, float v, float v1, float v2, float v3, float v4) {
		super.setupAnim(cyber, v, v1, v2, v3, v4);
		float f = MathHelper.sin(this.attackTime * (float) Math.PI);
		float f1 = MathHelper.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);
		this.rightArm.zRot = 0.0F;
		this.leftArm.zRot = 0.0F;
		this.rightArm.yRot = -(0.1F - f * 0.6F);
		this.leftArm.yRot = 0.1F - f * 0.6F;
		this.rightArm.xRot = (-(float) Math.PI / 2F);
		this.leftArm.xRot = (-(float) Math.PI / 2F);
		this.rightArm.xRot -= f * 1.2F - f1 * 0.4F;
		this.leftArm.xRot -= f * 1.2F - f1 * 0.4F;
		ModelHelper.bobArms(this.rightArm, this.leftArm, v2);
	}


	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rightleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftleg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftarm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightarm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
