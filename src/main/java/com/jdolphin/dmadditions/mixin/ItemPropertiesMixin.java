package com.jdolphin.dmadditions.mixin;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Item.Properties.class})
public abstract class ItemPropertiesMixin {
	@Shadow
	private int maxDamage;

	@Shadow
	public abstract Item.Properties durability(int p_200918_1_);

	@Inject(method = "stacksTo", at = @At("HEAD"), cancellable = true)
	public void stacksTo(int p_200917_1_, CallbackInfoReturnable<Item.Properties> cir) {
		if (this.maxDamage > 0) {
			// print the error, but don't throw it
			(new RuntimeException("Unable to have damage AND stack.")).printStackTrace();

			cir.setReturnValue(this.durability(this.maxDamage));
			cir.cancel();
		}
	}
}
