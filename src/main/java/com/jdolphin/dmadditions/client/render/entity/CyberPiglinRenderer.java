package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.CyberPiglinModel;
import com.jdolphin.dmadditions.entity.cyber.CyberPiglinEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CyberPiglinRenderer extends BipedRenderer<CyberPiglinEntity, CyberPiglinModel> {
	protected static final ResourceLocation CYBER_TEXTURE = Helper.createAdditionsRL("textures/entity/cyber/cyber_piglin.png");

	public CyberPiglinRenderer(EntityRendererManager renderManager) {
		super(renderManager, new CyberPiglinModel(), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(CyberPiglinEntity woodenCybermanEntity) {
		return CYBER_TEXTURE;
	}

	protected boolean shouldShowName(CyberPiglinEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || (entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity));
	}
}
