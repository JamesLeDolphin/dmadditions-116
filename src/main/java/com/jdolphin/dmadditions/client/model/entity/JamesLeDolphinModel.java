package com.jdolphin.dmadditions.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.swdteam.client.model.IModelPartReloader;
import com.swdteam.client.model.ModelReloaderRegistry;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelWrapper;
import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.util.ResourceLocation;

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
		this.model = ModelLoader.loadModel(new ResourceLocation(MODID, "models/entity/jamesledolphin.json"));

		if (this.model != null) {
			ModelWrapper modelWrapper = this.model.getModelData().getModel();
			this.head = modelWrapper.getPart("head");
			this.body = modelWrapper.getPart("body");
			this.tail = modelWrapper.getPart("tail");
			this.tailFin = modelWrapper.getPart("tail_fin");
		}
	}

	@Override
	@Nonnull
	public Iterable<ModelRenderer> parts() {
		return ImmutableList.of(this.body);
	}

	@Override
	public ModelRenderer getHead() {
		return this.head;
	}
}
