package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.CyberDroneEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.main.DalekMod;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class CyberDroneModel extends SegmentedModel<CyberDroneEntity> implements IModelPartReloader {
	public JSONModel model;
	protected ModelRenderer head;

	public CyberDroneModel() {
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(DalekMod.MODID, "models/entity/cyber/cyber_drone.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();
		if (this.model != null) {
			this.head = modelWrapper.getPart("Head");
		}
	}

	@Override
	public void setupAnim(CyberDroneEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {

	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(head);
	}
}
