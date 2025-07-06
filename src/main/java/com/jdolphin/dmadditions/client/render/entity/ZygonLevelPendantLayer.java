package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.common.entity.ZygonEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZygonLevelPendantLayer<T extends ZygonEntity & IVillagerDataHolder, M extends EntityModel<T> & IHeadToggle>
		extends VillagerLevelPendantLayer<T, M> {

	public ZygonLevelPendantLayer(IEntityRenderer<T, M> renderer,
			IReloadableResourceManager rrm, String path) {
		super(renderer, rrm, path);
	}

	@Override
	public void render(MatrixStack stack, IRenderTypeBuffer buffer, int p_225628_3_, T entity,
			float f1, float f2, float f3,
			float f4, float f5, float f6) {
		if (!entity.isDisguised())
			return;
		
		super.render(stack, buffer, p_225628_3_, entity, f1, f2, f3, f4, f5, f6);
	}
}
