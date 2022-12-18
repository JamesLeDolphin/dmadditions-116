package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.BessieModel;
import com.jdolphin.dmadditions.entity.BessieEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class BessieRenderer extends MobRenderer<BessieEntity, BessieModel> {

	public BessieRenderer(EntityRendererManager p_i46179_1_) {
		super(p_i46179_1_, new BessieModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(BessieEntity p_110775_1_) {
		return this.model.model.getModelData().getTexture();
	}
}
