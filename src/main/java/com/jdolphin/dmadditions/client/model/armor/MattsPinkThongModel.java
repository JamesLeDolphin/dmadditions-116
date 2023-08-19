package com.jdolphin.dmadditions.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.DmAdditions;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class MattsPinkThongModel extends BipedModel<LivingEntity> implements IModelPartReloader {
	public JSONModel model;

	public MattsPinkThongModel(float p_i1148_1_) {
		super(p_i1148_1_);
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(DmAdditions.MODID, "models/entity/armor/matts_pink_thong.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.leftLeg = modelWrapper.getPart("LeftLeg");
		this.rightLeg = modelWrapper.getPart("RightLeg");
		this.body = modelWrapper.getPart("Waist");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(leftLeg, rightLeg, body);
	}
}
