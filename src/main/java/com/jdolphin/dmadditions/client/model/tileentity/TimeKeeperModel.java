package com.jdolphin.dmadditions.client.model.tileentity;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class TimeKeeperModel extends Model {

	public TimeKeeperModel(Function<ResourceLocation, RenderType> resourceLocationRenderTypeFunction) {
		super(resourceLocationRenderTypeFunction);
	}


	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {

	}
}
