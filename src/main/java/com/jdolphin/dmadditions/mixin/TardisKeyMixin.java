package com.jdolphin.dmadditions.mixin;

import com.swdteam.common.init.DMTabs;
import com.swdteam.common.item.TardisKeyItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TardisKeyItem.class)
public class TardisKeyMixin {
	@Shadow
	String tardisLocation;

	@Inject(method = "<init>(Lnet/minecraft/item/Item$Properties;Ljava/lang/String;)V", at = @At("TAIL"))

	private void TardisKeyItem(Item.Properties properties, String tardisLocation, CallbackInfo ci) {
		//super(properties.stacksTo(1));
		this.tardisLocation = tardisLocation;
	}
}
