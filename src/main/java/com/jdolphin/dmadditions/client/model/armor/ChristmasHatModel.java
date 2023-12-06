package com.jdolphin.dmadditions.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.DmAdditions;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class ChristmasHatModel extends BipedModel<LivingEntity> implements IModelPartReloader {
	public JSONModel model;

	protected int color;

	public ChristmasHatModel(float p_i1148_1_, int color) {
		super(p_i1148_1_);

		this.color = color;
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(DmAdditions.MODID, "models/entity/armor/christmas_hat.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.head = modelWrapper.getPart("hat");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(head);
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int int1, int int2,
			float r, float g, float b, float p_225598_8_) {

		int i = this.color;
		r = (float)(i >> 16 & 255) / 255.0F;
		g = (float)(i >> 8 & 255) / 255.0F;
		b = (float)(i & 255) / 255.0F;


		super.renderToBuffer(matrixStack, vertexBuilder, int1, int2, r, g, b,
				p_225598_8_);
	}
}
