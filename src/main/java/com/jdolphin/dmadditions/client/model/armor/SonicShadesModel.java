package com.jdolphin.dmadditions.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.util.Helper;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class SonicShadesModel extends BipedModel<LivingEntity> implements IModelPartReloader {
	public JSONModel model;

	public SonicShadesModel(float v) {
		super(v);
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(Helper.createAdditionsRL("models/entity/armor/sonic_shades.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.head = modelWrapper.getPart("shades");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(head);
	}
}
