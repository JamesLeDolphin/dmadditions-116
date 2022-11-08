package com.jdolphin.dmadditions.mixin;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Item.Properties.class})
public abstract class ItemPropertiesMixin {

	@Inject(method = "stacksTo(I)Lnet/minecraft/item/Item$Properties;", at = @At("HEAD"))
	public void stacksTo(int p_200917_1_, CallbackInfoReturnable<Item.Properties> cir) {
		if (p_200917_1_ <= 1) {
			cir.setReturnValue((Item.Properties) (Object) this);
			cir.cancel();
		}
	}
}
