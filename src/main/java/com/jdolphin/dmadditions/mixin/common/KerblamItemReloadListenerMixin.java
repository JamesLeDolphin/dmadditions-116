package com.jdolphin.dmadditions.mixin.common;

import com.google.gson.JsonElement;
import com.jdolphin.dmadditions.kerblam.IKerblamItemMixin;
import com.swdteam.common.init.DMKerblamStock;
import com.swdteam.common.kerblam.KerblamItem;
import com.swdteam.common.kerblam.KerblamItemReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KerblamItemReloadListener.class)
public class KerblamItemReloadListenerMixin {
	@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resources/IResourceManager;Lnet/minecraft/profiler/IProfiler;)V",
		remap = false, at = @At("TAIL"))
	void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn, CallbackInfo ci) {

		Map<ResourceLocation, KerblamItem> stock = DMKerblamStock.getItems();

		stock.values().removeIf(kerblamItem -> !((IKerblamItemMixin) kerblamItem).isUnlocked());
	}
}
