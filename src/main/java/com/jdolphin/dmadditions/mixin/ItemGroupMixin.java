package com.jdolphin.dmadditions.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.jdolphin.dmadditions.init.DMATabs.*;

@Mixin({ItemGroup.class})
public abstract class ItemGroupMixin {

	@Inject(method = "getIconItem()Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
	public void getIconItem(CallbackInfoReturnable<ItemStack> cir) {
		if (!isMixinIconsReady()) initMixinIcons();

		int id = ((ItemGroup) (Object) this).getId();

		if (MIXIN_ICONS.containsKey(id)) {
			cir.setReturnValue(new ItemStack(MIXIN_ICONS.get(id)));
			cir.cancel();
		}
	}
}
