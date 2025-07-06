package com.jdolphin.dmadditions.mixin.common;

import com.jdolphin.dmadditions.common.init.DMACreativeTabs;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemGroup.class})
public abstract class ItemGroupMixin {

	@Inject(method = "getIconItem()Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
	public void getIconItem(CallbackInfoReturnable<ItemStack> cir) {
		if (!DMACreativeTabs.isMixinIconsReady()) DMACreativeTabs.initMixinIcons();

		int id = ((ItemGroup) (Object) this).getId();

		if (DMACreativeTabs.MIXIN_ICONS.containsKey(id)) {
			cir.setReturnValue(new ItemStack(DMACreativeTabs.MIXIN_ICONS.get(id)));
			cir.cancel();
		}
	}
}
