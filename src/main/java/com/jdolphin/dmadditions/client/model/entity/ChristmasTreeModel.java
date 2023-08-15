package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.ChristmasTreeEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import static com.swdteam.main.DalekMod.MODID;

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
		this.model = ModelLoader.loadModel(new ResourceLocation(MODID, "models/entity/christmas_tree.json"));
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
	public void setupAnim(ChristmasTreeEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		boolean flag = p_225597_1_.getFallFlyingTicks() > 4;

		float f = 1.0F;
		if (flag) {
			f = (float) p_225597_1_.getDeltaMovement().lengthSqr();
			f = f / 0.2F;
			f = f * f * f;
		}

		if (f < 1.0F) {
			f = 1.0F;
		}

		this.bottom.yRot = p_225597_2_ * 0.75f;
		this.middle.yRot = -p_225597_2_;
		this.top.yRot = p_225597_2_;
	}
}
