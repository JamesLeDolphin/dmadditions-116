package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.IceGovernessModel;
import com.jdolphin.dmadditions.entity.IceGovernessEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class IceGovernessRenderer extends BipedRenderer<IceGovernessEntity, IceGovernessModel>{

	public IceGovernessRenderer(EntityRendererManager p_i46168_1_, IceGovernessModel p_i46168_2_, float p_i46168_3_) {
		super(p_i46168_1_, p_i46168_2_, p_i46168_3_);
	}

	public IceGovernessRenderer(EntityRendererManager p_i50961_1_) {
		super(p_i50961_1_, new IceGovernessModel(1), 1f);
	}

	@Override
	public ResourceLocation getTextureLocation(IceGovernessEntity p_110775_1_) {
		return model.getTexture();
	}

}
