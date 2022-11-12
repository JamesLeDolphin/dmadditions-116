package com.jdolphin.dmadditions.mixin;

import com.swdteam.common.init.DMTabs;
import com.swdteam.common.item.TardisKeyItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TardisKeyItem.class)
abstract class TardisKeyMixin {
	/*@Shadow
	String tardisLocation;

	@Redirect(method = "<init>(Lnet/minecraft/item/Item$Properties;Ljava/lang/String;)V",
		at = @At(value = "INVOKE_STRING",
			args = "ldc=tick"))
	private void TardisKeyItem(Item.Properties properties, String tardisLocation) {
		return properties.tab(DMTabs.DM_TARDIS);
		this.tardisLocation = tardisLocation;
	}*/
}
