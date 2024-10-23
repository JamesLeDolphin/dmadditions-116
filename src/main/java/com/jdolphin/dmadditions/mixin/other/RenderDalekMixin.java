package com.jdolphin.dmadditions.mixin.other;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.jdolphin.dmadditions.client.render.entity.layers.DalekHeadLayer;
import com.swdteam.client.model.ModelDalekBase;
import com.swdteam.client.render.entity.RenderDalek;
import com.swdteam.common.entity.dalek.DalekEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;

@Mixin(RenderDalek.class)
public abstract class RenderDalekMixin extends LivingRenderer<DalekEntity, ModelDalekBase>{
	public RenderDalekMixin(EntityRendererManager erm, ModelDalekBase model, float yourself) {
		super(erm, model, yourself);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void RenderDalek(EntityRendererManager rendererManager, CallbackInfo ci){
		this.addLayer(new DalekHeadLayer(this));
	}
}
