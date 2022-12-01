package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.PilotFishEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static com.swdteam.main.DalekMod.MODID;

public class PilotFishModel extends BipedModel<PilotFishEntity> implements IModelPartReloader {
	public JSONModel model;

	public PilotFishModel(float modelSize){
		super(modelSize);
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(MODID, "models/entity/pilot_fish/santa.json"));

		if (this.model != null) {
			this.head = this.model.getModelData().getModel().getPart("Head");
			this.body = this.model.getModelData().getModel().getPart("Body");
			this.leftArm = this.model.getModelData().getModel().getPart("LeftArm");
			this.rightArm = this.model.getModelData().getModel().getPart("RightArm");
			this.leftLeg = this.model.getModelData().getModel().getPart("LeftLeg");
			this.rightLeg = this.model.getModelData().getModel().getPart("RightLeg");
		}

	}

	@Override
	@Nonnull
	protected Iterable<ModelRenderer> bodyParts() {
		return ImmutableList.of(this.body, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg);
	}

	@Override
	@Nonnull
	protected Iterable<ModelRenderer> headParts() {
		return ImmutableList.of(this.head);
	}
}
