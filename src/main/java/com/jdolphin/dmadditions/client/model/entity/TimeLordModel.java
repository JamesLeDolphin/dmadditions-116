package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.timelord.TimeLordEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TimeLordModel extends BipedModel<TimeLordEntity> {
	private final ModelRenderer head;
	private final ModelRenderer right_leg;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_arm;
	private final ModelRenderer body;

	public TimeLordModel() {
		super(0.7f);
		texWidth = 64;
		texHeight = 64;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 28).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.25F, false);
		head.texOffs(0, 16).addBox(-9.0F, -12.575F, 4.25F, 18.0F, 12.0F, 0.0F, 0.0F, false);

		right_leg = new ModelRenderer(this);
		right_leg.setPos(2.0F, 12.0F, 0.0F);
		right_leg.texOffs(0, 44).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		left_leg = new ModelRenderer(this);
		left_leg.setPos(-2.0F, 12.0F, 0.0F);
		left_leg.texOffs(0, 44).addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		right_arm = new ModelRenderer(this);
		right_arm.setPos(5.0F, 2.0F, 0.0F);
		right_arm.texOffs(17, 44).addBox(-13.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
		right_arm.texOffs(48, 0).addBox(-13.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, true);
		right_arm.texOffs(34, 44).addBox(-12.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, true);

		left_arm = new ModelRenderer(this);
		left_arm.setPos(-5.0F, 2.0F, 0.0F);
		left_arm.texOffs(17, 44).addBox(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		left_arm.texOffs(48, 0).addBox(9.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, false);
		left_arm.texOffs(34, 44).addBox(9.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(32, 24).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(TimeLordEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}