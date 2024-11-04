package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.timelord.TimeLordEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class TimeLordModel extends BipedModel<TimeLordEntity> {
	private final ModelRenderer head;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftArm;
	private final ModelRenderer body;

	public TimeLordModel() {
		super(0.7f);
		texWidth = 64;
		texHeight = 64;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 28).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.25F, false);
		head.texOffs(0, 16).addBox(-9.0F, -12.575F, 4.26F, 18.0F, 12.0F, 0.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(0, 44).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(-2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(0, 44).addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(5.0F, 2.0F, 0.0F);
		rightArm.texOffs(17, 44).addBox(-13.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
		rightArm.texOffs(48, 0).addBox(-13.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, true);
		rightArm.texOffs(34, 44).addBox(-12.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, true);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(-5.0F, 2.0F, 0.0F);
		leftArm.texOffs(17, 44).addBox(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
		leftArm.texOffs(48, 0).addBox(9.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.4F, false);
		leftArm.texOffs(34, 44).addBox(9.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(32, 24).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(TimeLordEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * 0.017453292F;
		this.head.yRot = netHeadYaw * 0.017453292F;
		this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.rightArm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftArm.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.leftArm.zRot = 0;
		this.rightArm.zRot = 0;
		rightArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.setPos(-5.0F, 2.0F, 0.0F);

		int regenTicks = entity.getRegenTicks();

		if (regenTicks > 0) {
			this.head.xRot = (float) MathHelper.lerp((float) regenTicks / 200, -0.2f, 0f);
			//LogManager.getLogger().debug("regenTicks: {}; {}", regenTicks, (float) regenTicks / 200);
			this.rightArm.zRot = 10;
			this.leftArm.zRot = -10;
			rightArm.setPos(-4.0F, -3.0F, 0.0F);
			leftArm.setPos(4.0F, -3.0F, 0.0F);
		}
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}

