package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.DalekMutantModel;
import com.jdolphin.dmadditions.common.entity.DalekMutantEntity;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class DalekMutantRenderer extends MobRenderer<DalekMutantEntity, DalekMutantModel> {
	public final DalekMutantModel model = new DalekMutantModel();
	public static final ResourceLocation TEXTURE_LOCATION = Helper.createAdditionsRL("textures/entity/dalek_mutant.png");

	public DalekMutantRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new DalekMutantModel(), 0.25f);
	}

	@Override
	public ResourceLocation getTextureLocation(DalekMutantEntity entity) {
		return TEXTURE_LOCATION;
	}
}
