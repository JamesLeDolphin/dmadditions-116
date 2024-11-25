package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.IceWarriorModel;
import com.jdolphin.dmadditions.entity.DalekMutantEntity;
import com.jdolphin.dmadditions.entity.IceWarriorEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DalekMutantRenderer extends EntityRenderer<DalekMutantEntity> {

	public static final ResourceLocation TEXTURE_LOCATION = Helper.createAdditionsRL("textures/entity/dalek_mutant.png");

	public DalekMutantRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager);
	}

	@Override
	public ResourceLocation getTextureLocation(DalekMutantEntity entity) {
		return TEXTURE_LOCATION;
	}
}
