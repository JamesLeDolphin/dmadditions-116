package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class JamesLeDolphinModel<T extends DolphinEntity> extends DolphinModel<T> implements IModelPartReloader, IHasHead {
	public JSONModel model;
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer tail;
	private ModelRenderer tailFin;

	public JamesLeDolphinModel() {
		this.texWidth = 64;
		this.texHeight = 64;

		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/jamesledolphin.json"));

		if (this.model != null) {
			ModelWrapper modelWrapper = this.model.getModelData().getModel();
			this.head = modelWrapper.getPart("head");
			this.body = modelWrapper.getPart("body");
			this.tail = modelWrapper.getPart("tail");
			this.tailFin = modelWrapper.getPart("tail_fin");
		}
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	@Nonnull
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(this.body);
	}

	@Override
	public ModelRenderer getHead() {
		return this.body;
	}


	@Override
	public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {
		this.body.xRot = v4 * ((float) Math.PI / 180F);
		this.body.yRot = v3 * ((float) Math.PI / 180F);
		if (Entity.getHorizontalDistanceSqr(t.getDeltaMovement()) > 1.0E-7D) {
			this.body.xRot += -0.05F + -0.05F * MathHelper.cos(v2 * 0.3F);
			this.tail.xRot = -0.1F * MathHelper.cos(v2 * 0.3F);
			this.tailFin.xRot = -0.2F * MathHelper.cos(v2 * 0.3F);
		}
	}
}
