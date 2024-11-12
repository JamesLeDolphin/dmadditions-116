package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ZygonModel;
import com.jdolphin.dmadditions.entity.ZygonEntity;
import com.jdolphin.dmadditions.util.Helper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class ZygonRenderer extends BipedRenderer<ZygonEntity, ZygonModel> {

	public static final ResourceLocation TEX = Helper.createAdditionsRL("textures/entity/zygon.png");
	private static final ResourceLocation VILLAGER_SKIN = new ResourceLocation("textures/entity/villager/villager.png");

	public ZygonRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new ZygonModel(), 1);
	}

	@Override
	@ParametersAreNonnullByDefault
	public void render(ZygonEntity entity, float f, float f1, MatrixStack stack, IRenderTypeBuffer buffer, int i) {
		EntityModel entityModel;
		if (entity.isDisguised()) {
			entityModel = new VillagerModel<>(0.0f);
		} else {
			entityModel = new ZygonModel();
		}
		stack.pushPose();
		IVertexBuilder builder = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
		entityModel.renderToBuffer(stack, builder, i, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		//super.render(entity, f, f1, stack, buffer, i);
		stack.popPose();
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull ZygonEntity entity) {
		return entity.isDisguised() ? VILLAGER_SKIN : TEX;
	}
}
