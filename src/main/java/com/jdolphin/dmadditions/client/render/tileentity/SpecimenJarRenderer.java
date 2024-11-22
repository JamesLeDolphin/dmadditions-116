package com.jdolphin.dmadditions.client.render.tileentity;

import com.jdolphin.dmadditions.block.SpecimenJarBlock;
import com.jdolphin.dmadditions.tileentity.SpecimenJarTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.math.vector.Vector3f;
import org.jetbrains.annotations.NotNull;

public class SpecimenJarRenderer extends TileEntityRenderer<SpecimenJarTileEntity> {

	public SpecimenJarRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(SpecimenJarTileEntity tile, float v, MatrixStack stack, @NotNull IRenderTypeBuffer buffer, int i, int i1) {
		stack.pushPose();

		ItemStack itemstack = tile.getSpecimen();
		if (!itemstack.isEmpty()) {
			if (itemstack.getItem() instanceof SpawnEggItem) {
				SpawnEggItem spawner = (SpawnEggItem) itemstack.getItem();
				EntityType<?> type = spawner.getType(itemstack.getOrCreateTag());
				ClientWorld clientWorld = Minecraft.getInstance().level;
				if (clientWorld != null) {
					Entity entity = type.create(clientWorld);
					EntityRendererManager manager = Minecraft.getInstance().getEntityRenderDispatcher();
					if (entity instanceof LivingEntity) {
						LivingEntity living = (LivingEntity) entity;
						LivingRenderer<?, ?> livingRenderer = (LivingRenderer<?, ?>) manager.getRenderer(living);
						EntityModel<?> model = livingRenderer.getModel();
						IVertexBuilder builder = buffer.getBuffer(model.renderType(manager.getRenderer(living).getTextureLocation(living)));

						EntitySize size = living.getDimensions(living.getPose());
						float scale = 0.3f;
						if (size.width > 1.0f) {
							float f = size.width / 0.4f;
							scale = size.width / f;
						}


						stack.scale(scale, scale, scale);

						stack.translate(1.65, 2, 1.65);

						stack.mulPose(Vector3f.YN.rotationDegrees(tile.getBlockState().getValue(SpecimenJarBlock.FACING).toYRot()));
						stack.mulPose(Vector3f.XN.rotationDegrees(180));
						model.renderToBuffer(stack, builder, 15728640, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
					}
				}
			} else {
				renderItemStack(stack,tile, itemstack, buffer, i);
			}
		}

		stack.popPose();
	}

	private void renderItemStack(MatrixStack stack, SpecimenJarTileEntity tile, ItemStack itemStack, IRenderTypeBuffer buffer, int i) {
		stack.translate(0.5, 0.5, 0.5);
		stack.scale(0.4F, 0.4F, 0.4F);
		stack.mulPose(Vector3f.YN.rotationDegrees(tile.getBlockState().getValue(SpecimenJarBlock.FACING).toYRot()));
		Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemCameraTransforms.TransformType.NONE, i, OverlayTexture.NO_OVERLAY, stack, buffer);

	}
}