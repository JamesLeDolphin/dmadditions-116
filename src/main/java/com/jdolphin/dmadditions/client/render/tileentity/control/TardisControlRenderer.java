package com.jdolphin.dmadditions.client.render.tileentity.control;

import com.jdolphin.dmadditions.client.model.entity.control.TardisControlModel;
import com.jdolphin.dmadditions.entity.control.TardisControl;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

public class TardisControlRenderer extends EntityRenderer<TardisControl> {
	private final TardisControlModel model;
	private static final ResourceLocation LOCATION = new ResourceLocation("textures/entity/control/base.png");

	public TardisControlRenderer(EntityRendererManager manager) {
		super(manager);
		this.model = new TardisControlModel();
	}

	public void render(TardisControl control, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, @NotNull IRenderTypeBuffer pBuffer, int pPackedLight) {
		super.render(control, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
	}

	@Override
	protected boolean shouldShowName(TardisControl control) {
		return control == this.entityRenderDispatcher.crosshairPickEntity;
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull TardisControl p_110775_1_) {
		return LOCATION;
	}
}