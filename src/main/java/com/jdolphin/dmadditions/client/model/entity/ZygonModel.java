package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.ZygonEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class ZygonModel extends BipedModel<ZygonEntity> {
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;
	private final ModelRenderer head;
	private final ModelRenderer leftArm;
	private final ModelRenderer rightArm;
	private final ModelRenderer body;

	public ZygonModel() {
		super(0.7f);
		texWidth = 128;
		texHeight = 128;

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 11.6F, 0.0F);
		rightLeg.texOffs(0, 49).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 11.6F, 0.0F);
		leftLeg.texOffs(50, 35).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.1F, 0.0F);
		head.texOffs(0, 18).addBox(-5.0F, -10.5F, -4.0F, 10.0F, 10.0F, 8.0F, 0.0F, false);
		head.texOffs(0, 37).addBox(-4.5F, -14.5F, -3.5F, 9.0F, 4.0F, 7.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(-6.5F, -10.4F, -3.5F, 13.0F, 10.0F, 7.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(4.0F, 1.6F, 0.0F);
		leftArm.texOffs(41, 0).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-4.0F, 1.6F, 0.0F);
		rightArm.texOffs(33, 37).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 5.6F, 0.0F);
		body.texOffs(37, 18).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(ZygonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.rightArm.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leftArm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
