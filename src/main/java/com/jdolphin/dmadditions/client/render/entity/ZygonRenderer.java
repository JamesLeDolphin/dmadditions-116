package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ZygonModel;
import com.jdolphin.dmadditions.entity.ZygonEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class ZygonRenderer extends MobRenderer<ZygonEntity, EntityModel<ZygonEntity>> {

	@SuppressWarnings({ "unchecked", "rawtypes"})
	public ZygonRenderer(EntityRendererManager erm) {
		super(erm, new ZygonModel(), 0);
		IReloadableResourceManager rrm = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
		zygonModel = model;
		villagerModel = new VillagerModel<>(0);
		this.addLayer(new HeadLayer(this));
		this.addLayer(new ZygonLevelPendantLayer(this, rrm, "villager"));
	}

	public static final ResourceLocation ZYGON_SKIN = Helper.createAdditionsRL("textures/entity/zygon.png");
	private static final ResourceLocation VILLAGER_SKIN = new ResourceLocation("textures/entity/villager/villager.png");

	protected VillagerModel<ZygonEntity> villagerModel;
	protected EntityModel<ZygonEntity> zygonModel;

	@Override
	@ParametersAreNonnullByDefault
	public void render(ZygonEntity entity, float f, float f1, MatrixStack stack, IRenderTypeBuffer buffer, int i) {
		EntityModel<ZygonEntity> entityModel;
		if (entity.isDisguised()) {
			entityModel = villagerModel;
		} else {
			entityModel = zygonModel;
		}

		model = entityModel;
		super.render(entity, f, f1, stack, buffer, i);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull ZygonEntity entity) {
		return entity.isDisguised() ? VILLAGER_SKIN : ZYGON_SKIN;
	}

}
