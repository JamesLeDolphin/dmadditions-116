package com.jdolphin.dmadditions.mixin.other;

import com.swdteam.client.model.ModelCyberman;
import com.swdteam.client.render.entity.RenderCyberman;
import com.swdteam.common.entity.CybermanEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderCyberman.class)
public abstract class RenderCybermanMixin extends LivingRenderer<CybermanEntity, ModelCyberman> {
	public RenderCybermanMixin(EntityRendererManager erm, ModelCyberman model, float yourself) {
		super(erm, model, yourself);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void RenderCyberman(EntityRendererManager rendererManager, CallbackInfo ci) {
		this.addLayer(new HeadLayer<>(this));
	}
}
