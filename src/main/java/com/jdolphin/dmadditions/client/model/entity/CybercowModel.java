package com.jdolphin.dmadditions.client.model.entity;

import com.jdolphin.dmadditions.entity.cyber.CyberCowEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.model.javajson.JSONModel;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CybercowModel extends QuadrupedModel<CyberCowEntity> {
	private final ModelRenderer body;
	private final ModelRenderer head;
	private final ModelRenderer leg0;
	private final ModelRenderer leg1;
	private final ModelRenderer leg2;
	private final ModelRenderer leg3;

	public CybercowModel() {
		super(12, 0.0F, false, 10.0F, 4.0F, 2.0F, 2.0F, 24);
		texWidth = 64;
		texHeight = 64;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 5.0F, 2.0F);
		setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
		body.texOffs(0, 0).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, 0.0F, false);
		body.texOffs(0, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 4.0F, -8.0F);
		head.texOffs(0, 28).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F, false);
		head.texOffs(0, 28).addBox(-5.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		head.texOffs(44, 10).addBox(4.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		head.texOffs(34, 8).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		head.texOffs(34, 0).addBox(-2.0F, -8.0F, -6.0F, 4.0F, 3.0F, 5.0F, 0.0F, false);

		leg0 = new ModelRenderer(this);
		leg0.setPos(-4.0F, 12.0F, 7.0F);
		leg0.texOffs(28, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		leg1 = new ModelRenderer(this);
		leg1.setPos(4.0F, 12.0F, 7.0F);
		leg1.texOffs(28, 28).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		leg2 = new ModelRenderer(this);
		leg2.setPos(-4.0F, 12.0F, -6.0F);
		leg2.texOffs(28, 28).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

		leg3 = new ModelRenderer(this);
		leg3.setPos(4.0F, 12.0F, -6.0F);
		leg3.texOffs(28, 28).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leg0.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		leg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
