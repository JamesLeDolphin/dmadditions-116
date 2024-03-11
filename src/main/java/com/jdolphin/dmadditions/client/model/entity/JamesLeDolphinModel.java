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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

import static com.jdolphin.dmadditions.DmAdditions.MODID;

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
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		this.body.xRot = p_225597_6_ * ((float) Math.PI / 180F);
		this.body.yRot = p_225597_5_ * ((float) Math.PI / 180F);
		if (Entity.getHorizontalDistanceSqr(p_225597_1_.getDeltaMovement()) > 1.0E-7D) {
			this.body.xRot += -0.05F + -0.05F * MathHelper.cos(p_225597_4_ * 0.3F);
			this.tail.xRot = -0.1F * MathHelper.cos(p_225597_4_ * 0.3F);
			this.tailFin.xRot = -0.2F * MathHelper.cos(p_225597_4_ * 0.3F);
		}
	}
}
