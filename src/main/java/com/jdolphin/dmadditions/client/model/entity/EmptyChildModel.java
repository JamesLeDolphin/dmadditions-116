package com.jdolphin.dmadditions.client.model.entity;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.jdolphin.dmadditions.entity.EmptyChildEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class EmptyChildModel extends BipedModel<EmptyChildEntity> {
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer rightArm;
	private final ModelRenderer leftArm;
	private final ModelRenderer leftLeg;
	private final ModelRenderer rightLeg;

	public EmptyChildModel() {
		super(0.25f);
		texWidth = 64;
		texHeight = 64;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 5.875F, 0.375F);
		head.texOffs(0, 0).addBox(-4.0F, -7.875F, -4.375F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(32, 12).addBox(-3.5F, -6.875F, -5.375F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		head.texOffs(32, 30).addBox(-1.5F, -2.875F, -7.375F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		head.texOffs(32, 36).addBox(0.5F, -6.875F, -5.375F, 3.0F, 3.0F, 1.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 6.6F, 0.0F);
		body.texOffs(0, 16).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 10.0F, 4.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(-4.0F, 8.1F, 0.0F);
		leftArm.texOffs(24, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(4.0F, 8.1F, 0.0F);
		rightArm.texOffs(0, 30).addBox(0.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(2.0F, 16.1F, 0.0F);
		rightLeg.texOffs(32, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(-2.0F, 16.1F, 0.0F);
		leftLeg.texOffs(16, 30).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(EmptyChildEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.rightArm.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leftArm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.rightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}