package com.jdolphin.dmadditions.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.jdolphin.dmadditions.init.DMATabs.*;

@Mixin({ItemGroup.class})
public abstract class ItemGroupMixin {

	@Shadow
	public abstract int getId();

	@Inject(method = "getIconItem", at = @At("HEAD"), cancellable = true)
	public void getIconItem(CallbackInfoReturnable<ItemStack> cir) {
		if (!isMixinIconsReady()) initMixinIcons();

		if (MIXIN_ICONS.containsKey(this.getId())) {
			cir.setReturnValue(new ItemStack(MIXIN_ICONS.get(this.getId())));
			cir.cancel();
		}
	}
}
