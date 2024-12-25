package com.jdolphin.dmadditions.client.render.entity.cyber;

import com.jdolphin.dmadditions.client.model.entity.cyber.NetheriteCybermanModel;
import com.jdolphin.dmadditions.entity.cyber.NetheriteCybermanEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;

public class NetheriteCybermanRenderer extends LivingRenderer<NetheriteCybermanEntity, NetheriteCybermanModel> {
	protected static final ResourceLocation CYBERMAN = Helper.createAdditionsRL("textures/entity/cyber/netherite_cyberman.png");

	public NetheriteCybermanRenderer(EntityRendererManager renderManager) {
		super(renderManager, new NetheriteCybermanModel(1.0F), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(NetheriteCybermanEntity entity) {
		return this.model.model.getModelData().getTexture();
	}

	protected boolean shouldShowName(NetheriteCybermanEntity entity) {
		return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
	}
}
