package com.jdolphin.dmadditions.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CubeModel extends Model {
	private final ModelRenderer bb_main;

	public CubeModel() {
		super(RenderType::entityTranslucent);
		texWidth = 16;
		texHeight = 16;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(2, 2).addBox(-8.0F, -16.0F, 8.0F, 16.0F, 16.0F, 0.0F, 0.0F, false);
		bb_main.texOffs(2, 2).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 0.0F, 0.0F, false);
		bb_main.texOffs(-14, -14).addBox(-8.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, false);
		bb_main.texOffs(-14, -14).addBox(8.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, false);
		bb_main.texOffs(-14, -14).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, 0.0F, false);
		bb_main.texOffs(-14, -14).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 0.0F, 16.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		matrixStack.pushPose();
		float scale = 5.4f;
		matrixStack.scale(scale, scale, scale);
		matrixStack.translate(0.15, -0.6, 0.15);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		matrixStack.popPose();
	}
}
