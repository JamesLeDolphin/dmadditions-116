package com.jdolphin.dmadditions.mixin.common;

import com.swdteam.common.item.TardisKeyItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TardisKeyItem.class)
abstract class TardisKeyMixin {


	@Redirect(method = "<init>(Lnet/minecraft/item/Item$Properties;Ljava/lang/String;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item$Properties;stacksTo(I)Lnet/minecraft/item/Item$Properties;"))

	private static Item.Properties TardisKeyItem(Item.Properties properties, int x) {
		return properties;
	}
}
