package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.EmptyChildModel;
import com.jdolphin.dmadditions.client.model.entity.ZygonModel;
import com.jdolphin.dmadditions.entity.EmptyChildEntity;
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

public class EmptyChildRenderer extends BipedRenderer<EmptyChildEntity, EmptyChildModel> {

	public static final ResourceLocation TEX = Helper.createAdditionsRL("textures/entity/empty_child.png");

	public EmptyChildRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new EmptyChildModel(), 1);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull EmptyChildEntity entity) {
		return TEX;
	}
}
