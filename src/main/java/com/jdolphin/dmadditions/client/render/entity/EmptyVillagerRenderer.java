package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ZygonModel;
import com.jdolphin.dmadditions.entity.EmptyVillagerEntity;
import com.jdolphin.dmadditions.entity.HerobrineEntity;
import com.jdolphin.dmadditions.entity.ZygonEntity;
import com.jdolphin.dmadditions.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

public class EmptyVillagerRenderer extends MobRenderer<EmptyVillagerEntity, EntityModel<EmptyVillagerEntity>> {
	private static final ResourceLocation VILLAGER_SKIN = new ResourceLocation("textures/entity/villager/villager.png");
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public EmptyVillagerRenderer(EntityRendererManager erm) {
		super(erm, new VillagerModel<>(0), 0);
		IReloadableResourceManager rrm = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
		this.addLayer(new HeadLayer(this));
		this.addLayer(new VillagerLevelPendantLayer(this, rrm, "villager"));
	}


	@Override
	public ResourceLocation getTextureLocation(EmptyVillagerEntity entity) {
		return VILLAGER_SKIN;
	}

}
