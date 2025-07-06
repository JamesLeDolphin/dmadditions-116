package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.common.entity.SnowmanEntity;
import com.jdolphin.dmadditions.common.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

import javax.annotation.Nonnull;

public class SnowmanModel extends SegmentedModel<SnowmanEntity> implements IModelPartReloader, IHasHead {
	public JSONModel model;

	protected ModelRenderer head;
	protected ModelRenderer neck;
	protected ModelRenderer torso;
	protected ModelRenderer skirt;

	public SnowmanModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void setupAnim(SnowmanEntity snowmanEntity, float v, float v1, float v2, float v3, float v4) {
		this.head.yRot = v3 * ((float) Math.PI / 180F);
		this.head.xRot = v4 * ((float) Math.PI / 180F);
		this.skirt.yRot = v3 * ((float) Math.PI / 180F) * 0.25F;
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/snowman.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.head = modelWrapper.getPart("Head");
		this.neck = modelWrapper.getPart("Neck");
		this.torso = modelWrapper.getPart("Torso");
		this.skirt = modelWrapper.getPart("Skirt");
	}


	@Override
	@Nonnull
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(this.head, this.neck, this.torso, this.skirt);
	}

	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
}
