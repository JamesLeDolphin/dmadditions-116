package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.DalekMutantEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class DalekMutantModel extends EntityModel<DalekMutantEntity> {
	private final ModelRenderer mutant;
	private final ModelRenderer tentacles;
	private final ModelRenderer tentaclebottomright_r1;
	private final ModelRenderer tentaclebottomleft_r1;
	private final ModelRenderer tentacletopright_r1;
	private final ModelRenderer tentacleleft_r1;

	public DalekMutantModel() {
		texWidth = 64;
		texHeight = 64;

		mutant = new ModelRenderer(this);
		mutant.setPos(2.7F, 22.4F, 1.7F);
		setRotationAngle(mutant, -1.5708F, 0.0F, 0.0F);
		mutant.texOffs(0, 0).addBox(-4.5F, -8.5F, -1.5F, 9.0F, 6.0F, 3.0F, 0.0F, false);
		mutant.texOffs(0, 10).addBox(-4.0F, -2.5F, -1.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);

		tentacles = new ModelRenderer(this);
		tentacles.setPos(-2.7F, 16.6F, -1.7F);
		mutant.addChild(tentacles);
		tentacles.texOffs(21, 10).addBox(6.6129F, -17.0F, 1.9863F, 11.0F, 5.0F, 0.0F, 0.0F, false);

		tentaclebottomright_r1 = new ModelRenderer(this);
		tentaclebottomright_r1.setPos(4.0F, -14.0F, 2.4F);
		tentacles.addChild(tentaclebottomright_r1);
		setRotationAngle(tentaclebottomright_r1, 0.0F, 0.0F, -1.7453F);
		tentaclebottomright_r1.texOffs(25, 0).addBox(-10.803F, -2.9106F, 0.0121F, 11.0F, 5.0F, 0.0F, 0.0F, false);

		tentaclebottomleft_r1 = new ModelRenderer(this);
		tentaclebottomleft_r1.setPos(-0.4F, -13.4F, 1.5F);
		tentacles.addChild(tentaclebottomleft_r1);
		setRotationAngle(tentaclebottomleft_r1, 0.0F, 0.0436F, -0.7679F);
		tentaclebottomleft_r1.texOffs(23, 22).addBox(-11.3781F, -2.116F, -0.021F, 11.0F, 5.0F, 0.0F, 0.0F, false);

		tentacletopright_r1 = new ModelRenderer(this);
		tentacletopright_r1.setPos(5.1F, -18.9F, 2.5F);
		tentacles.addChild(tentacletopright_r1);
		setRotationAngle(tentacletopright_r1, 0.0F, 0.0F, -0.4189F);
		tentacletopright_r1.texOffs(23, 16).addBox(-0.0556F, -2.3848F, 0.0695F, 11.0F, 5.0F, 0.0F, 0.0F, false);

		tentacleleft_r1 = new ModelRenderer(this);
		tentacleleft_r1.setPos(-1.2F, -15.0F, 1.6F);
		tentacles.addChild(tentacleleft_r1);
		setRotationAngle(tentacleleft_r1, 0.0F, 0.0F, 0.1571F);
		tentacleleft_r1.texOffs(0, 21).addBox(-10.6424F, -2.4789F, 0.0707F, 11.0F, 5.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(DalekMutantEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.tentaclebottomright_r1.yRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.tentaclebottomleft_r1.yRot = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.tentacletopright_r1.yRot = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.tentacleleft_r1.yRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		mutant.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}