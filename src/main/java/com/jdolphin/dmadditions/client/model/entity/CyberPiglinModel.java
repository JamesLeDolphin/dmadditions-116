package com.jdolphin.dmadditions.client.model.entity;


import com.jdolphin.dmadditions.entity.cyber.CyberPiglinEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class CyberPiglinModel extends BipedModel<CyberPiglinEntity> {
	private final ModelRenderer head;
	private final ModelRenderer left_ear;
	private final ModelRenderer right_ear;
	private final ModelRenderer body;
	private final ModelRenderer left_arm;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;

	public CyberPiglinModel() {
		super(0.5f);
		texWidth = 64;
		texHeight = 64;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(51, 39).addBox(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
		head.texOffs(17, 34).addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		head.texOffs(39, 53).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		head.texOffs(42, 33).addBox(-1.5F, -10.0F, -1.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);
		head.texOffs(25, 17).addBox(-7.5F, -9.5F, 0.0F, 15.0F, 6.0F, 0.0F, 0.0F, false);

		left_ear = new ModelRenderer(this);
		left_ear.setPos(5.0F, -3.5F, 0.0F);
		left_ear.texOffs(42, 24).addBox(-0.5F, -2.5F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);

		right_ear = new ModelRenderer(this);
		right_ear.setPos(-5.0F, -3.5F, 0.0F);
		right_ear.texOffs(0, 51).addBox(-1.5F, -2.5F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		left_arm = new ModelRenderer(this);
		left_arm.setPos(5.0F, 2.0F, 0.0F);
		left_arm.texOffs(25, 24).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		left_arm.texOffs(34, 41).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.4F, false);
		left_arm.texOffs(51, 45).addBox(3.0F, -1.0F, 0.0F, 2.0F, 7.0F, 0.0F, 0.0F, false);

		right_arm = new ModelRenderer(this);
		right_arm.setPos(-5.0F, 2.0F, 0.0F);
		right_arm.texOffs(0, 34).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		right_arm.texOffs(34, 53).addBox(-4.5F, -1.0F, 0.0F, 2.0F, 7.0F, 0.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setPos(2.0F, 12.0F, 0.0F);
		left_leg.texOffs(37, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setPos(-2.0F, 12.0F, 0.0F);
		right_leg.texOffs(17, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(CyberPiglinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.right_arm.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.left_arm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.right_leg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.left_leg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		left_ear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		right_ear.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}