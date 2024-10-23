package com.jdolphin.dmadditions.client.render.entity.layers;

import com.swdteam.client.model.ModelDalekBase;
import com.swdteam.common.entity.dalek.DalekEntity;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DalekHeadLayer extends NoHeadHeadLayer<DalekEntity, ModelDalekBase> {

	public DalekHeadLayer(IEntityRenderer<DalekEntity, ModelDalekBase> renderer) {
		super(renderer);
	}

	@Override
	public ModelRenderer getHead(DalekEntity entity, ModelDalekBase model) {
		return model.ANI_HEAD;
	}

}
