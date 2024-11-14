package com.jdolphin.dmadditions.client.render.entity;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.NotNull;

import com.jdolphin.dmadditions.client.model.entity.ZygonModel;
import com.jdolphin.dmadditions.entity.ZygonEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

public class ZygonRenderer extends MobRenderer<MobEntity, EntityModel<MobEntity>> {

	@SuppressWarnings("unchecked")
	public ZygonRenderer(EntityRendererManager erm) {
		super(erm, (EntityModel<MobEntity>) (Object) new ZygonModel(), 0);
		zygonModel = model;
		villagerModel = new VillagerModel<>(0);
	}

	public static final ResourceLocation ZYGON_SKIN = Helper.createAdditionsRL("textures/entity/zygon.png");
	private static final ResourceLocation VILLAGER_SKIN = new ResourceLocation("textures/entity/villager/villager.png");

	protected VillagerModel<ZygonEntity> villagerModel;
	protected EntityModel<MobEntity> zygonModel;

	@SuppressWarnings("unchecked")
	@Override
	@ParametersAreNonnullByDefault
	public void render(MobEntity entity, float f, float f1, MatrixStack stack, IRenderTypeBuffer buffer, int i) {
		if (!(entity instanceof ZygonEntity)) {
			super.render(entity, f, f1, stack, buffer, i);
			return;
		}

		ZygonEntity zygonEntity = (ZygonEntity) entity;
		EntityModel<? extends Entity> entityModel;
		if (zygonEntity.isDisguised()) {
			entityModel = villagerModel;
		} else {
			entityModel = zygonModel;
		}

		model = (EntityModel<MobEntity>) entityModel;
		super.render(entity, f, f1, stack, buffer, i);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull MobEntity entity) {
		if (!(entity instanceof ZygonEntity))
			return ZYGON_SKIN;

		ZygonEntity zygonEntity = (ZygonEntity) entity;
		return zygonEntity.isDisguised() ? VILLAGER_SKIN : ZYGON_SKIN;
	}

}
