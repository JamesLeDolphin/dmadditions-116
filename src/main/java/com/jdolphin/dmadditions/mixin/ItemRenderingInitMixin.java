package com.jdolphin.dmadditions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.jdolphin.dmadditions.client.init.DMAItemRenderingInit;
import com.swdteam.client.init.ItemRenderingInit;

@Mixin(ItemRenderingInit.class)
public class ItemRenderingInitMixin{
	@Inject(method = "addRegistries", at=@At("TAIL"), remap=false)
	private static void addRegistries(CallbackInfo ci){
		DMAItemRenderingInit.addRegisties();
	}
}
