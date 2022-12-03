package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.jdolphin.dmadditions.entity.SnowmanEntity;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

import static com.swdteam.main.DalekMod.MODID;

public class SnowmanModel extends SegmentedModel<SnowmanEntity> implements IModelPartReloader {
	public JSONModel model;

	protected ModelRenderer head;
	protected ModelRenderer neck;
	protected ModelRenderer torso;
	protected ModelRenderer skirt;

	public SnowmanModel(){
		super();
		ModelReloaderRegistry.register(this);
	}

	@Override
	public void setupAnim(SnowmanEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		this.head.yRot = p_225597_5_ * ((float)Math.PI / 180F);
		this.head.xRot = p_225597_6_ * ((float)Math.PI / 180F);
		this.skirt.yRot = p_225597_5_ * ((float)Math.PI / 180F) * 0.25F;
		float f = MathHelper.sin(this.skirt.yRot);
		float f1 = MathHelper.cos(this.skirt.yRot);
	}

	@Override
	public void init() {
		this.model = ModelLoader.loadModel(new ResourceLocation(MODID, "models/entity/snowman.json"));
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
}
