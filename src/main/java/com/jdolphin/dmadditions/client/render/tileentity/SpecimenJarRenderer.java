package com.jdolphin.dmadditions.client.render.tileentity;

import com.jdolphin.dmadditions.tileentity.SpecimenJarTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import org.jetbrains.annotations.NotNull;

public class SpecimenJarRenderer extends TileEntityRenderer<SpecimenJarTileEntity> {

	public SpecimenJarRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(SpecimenJarTileEntity tile, float v, MatrixStack stack, @NotNull IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
		stack.pushPose();

		ItemStack itemstack = tile.getSpecimen();
		if (!itemstack.isEmpty()) {
			stack.mulPose(Vector3f.ZP.rotationDegrees((float) i * 360.0F / 8.0F));
			stack.translate(0.52, 0.5, 0.5);
			stack.scale(0.5F, 0.5F, 0.5F);
			Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemCameraTransforms.TransformType.FIXED, i, OverlayTexture.NO_OVERLAY, stack, iRenderTypeBuffer);
		}

		stack.popPose();
	}
}
