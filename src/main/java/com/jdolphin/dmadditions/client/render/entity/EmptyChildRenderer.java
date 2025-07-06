package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.EmptyChildModel;
import com.jdolphin.dmadditions.common.entity.EmptyChildEntity;
import com.jdolphin.dmadditions.common.util.Helper;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

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
