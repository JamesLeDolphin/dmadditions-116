package com.jdolphin.dmadditions.client.model.entity.control;

import com.jdolphin.dmadditions.common.entity.control.TardisControl;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TardisControlModel extends EntityModel<TardisControl> {
	private final ModelRenderer bb_main;

	public TardisControlModel() {
		super(RenderType::entityTranslucent);
		texWidth = 32;
		texHeight = 32;

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 0).addBox(-8.0F, -24.0F, 0.0F, 16.0F, 32.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(TardisControl tardisControl, float v, float v1, float v2, float v3, float v4) {

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
