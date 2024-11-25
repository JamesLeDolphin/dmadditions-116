package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.DalekMutantModel;
import com.jdolphin.dmadditions.client.model.entity.IceWarriorModel;
import com.jdolphin.dmadditions.entity.DalekMutantEntity;
import com.jdolphin.dmadditions.entity.IceWarriorEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
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
