package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.entity.RacnossEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RacnossModel extends SegmentedModel<RacnossEntity> implements IModelPartReloader {
	public JSONModel model;

	public ModelRenderer all;
	public ModelRenderer body;
	public ModelRenderer head;

	public ModelRenderer leg0;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	public ModelRenderer leg5;
	public ModelRenderer leg6;
	public ModelRenderer leg7;

	public RacnossModel(){
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(DmAdditions.MODID, "models/entity/racnoss.json"));
		ModelWrapper modelWrapper = this.model.getModelData().getModel();

		this.all = modelWrapper.getPart("all");
		this.body = modelWrapper.getPart("Body");
		this.head = modelWrapper.getPart("Head");

		this.leg0 = modelWrapper.getPart("RightLeg1");
		this.leg1 = modelWrapper.getPart("LeftLeg3");
		this.leg2 = modelWrapper.getPart("RightLeg2");
		this.leg3 = modelWrapper.getPart("LeftLeg2");
		this.leg4 = modelWrapper.getPart("RightLeg3");
		this.leg5 = modelWrapper.getPart("LeftLeg1");
		this.leg6 = modelWrapper.getPart("RightLeg4");
		this.leg7 = modelWrapper.getPart("LeftLeg4");
	}

	@Override
	public JSONModel getModel() {
		return model;
	}

	@Override
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(all);
	}

	@Override
	public void setupAnim(RacnossEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		entity.setupAnim(this, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}
}
