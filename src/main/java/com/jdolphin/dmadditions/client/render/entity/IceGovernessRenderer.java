package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.IceGovernessModel;
import com.jdolphin.dmadditions.entity.IceGovernessEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class IceGovernessRenderer extends BipedRenderer<IceGovernessEntity, IceGovernessModel> {

	public IceGovernessRenderer(EntityRendererManager entityRendererManager, IceGovernessModel iceGovernessModel, float v) {
		super(entityRendererManager, iceGovernessModel, v);
	}

	public IceGovernessRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new IceGovernessModel(1), 1f);
	}

	@Override
	public ResourceLocation getTextureLocation(IceGovernessEntity iceGovernessEntity) {
		return model.getTexture();
	}

}
