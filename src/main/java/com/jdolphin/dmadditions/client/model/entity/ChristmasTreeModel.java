package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.ChristmasTreeEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ChristmasTreeModel extends SegmentedModel<ChristmasTreeEntity> implements IModelPartReloader {

	protected ModelRenderer bottom;
	protected ModelRenderer middle;
	protected ModelRenderer top;
	public JSONModel model;

	public ChristmasTreeModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/christmas_tree.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.bottom = modelWrapper.getPart("bottom");
		this.middle = modelWrapper.getPart("middle");
		this.top = modelWrapper.getPart("top");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(bottom, middle, top);
	}

	@Override
	public void setupAnim(ChristmasTreeEntity christmasTreeEntity, float v, float v1, float v2, float v3, float v4) {
		boolean flag = christmasTreeEntity.getFallFlyingTicks() > 4;

		float f = 1.0F;
		if (flag) {
			f = (float) christmasTreeEntity.getDeltaMovement().lengthSqr();
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.bottom.yRot = v * 0.75f;
		this.middle.yRot = -v;
		this.top.yRot = v;
	}
}
