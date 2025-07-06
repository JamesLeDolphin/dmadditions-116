package com.jdolphin.dmadditions.client.render.entity;


import com.jdolphin.dmadditions.client.model.entity.TorchwoodSuvModel;
import com.jdolphin.dmadditions.common.entity.VehicleEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TorchwoodSuvRenderer extends MobRenderer<VehicleEntity, TorchwoodSuvModel> {

	public TorchwoodSuvRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new TorchwoodSuvModel(), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull VehicleEntity torchwoodSuvEntity) {
		return this.model.model.getModelData().getTexture();
	}
}
