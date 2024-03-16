package com.jdolphin.dmadditions.client.render.entity;

import com.jdolphin.dmadditions.client.model.entity.ChristmasCreeperModel;
import com.jdolphin.dmadditions.entity.ChristmasCreeperEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ChristmasCreeperRenderer extends MobRenderer<ChristmasCreeperEntity, ChristmasCreeperModel> {
   private static final ResourceLocation CREEPER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper.png");

   protected void scale(CreeperEntity creeperEntity, MatrixStack matrixStack, float v) {
      float f = creeperEntity.getSwelling(v);
      float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
      f = MathHelper.clamp(f, 0.0F, 1.0F);
      f = f * f;
      f = f * f;
      float f2 = (1.0F + f * 0.4F) * f1;
      float f3 = (1.0F + f * 0.1F) / f1;
      matrixStack.scale(f2, f3, f2);
   }

   protected float getWhiteOverlayProgress(ChristmasCreeperEntity christmasCreeperEntity, float v) {
      float f = christmasCreeperEntity.getSwelling(v);
      return (int)(f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
   }

   public ResourceLocation getTextureLocation(ChristmasCreeperEntity christmasCreeperEntity) {
      return CREEPER_LOCATION;
   }
	public ChristmasCreeperRenderer(EntityRendererManager entityRendererManager) {
		super(entityRendererManager, new ChristmasCreeperModel(), 0.5F);
		this.addLayer(new HeadLayer<>(this));
		//TODO: add charge layer
	}

}
