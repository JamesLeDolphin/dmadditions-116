package com.jdolphin.dmadditions.mixin;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.init.MixinBlock;
import com.swdteam.common.RegistryHandler;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.item.BaseBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin({DMBlocks.class})
public abstract class DMBlocksMixin {

	@Inject(method = "registerBlock(Ljava/util/function/Supplier;Ljava/lang/String;Lnet/minecraft/item/Item$Properties;Z)Lnet/minecraftforge/fml/RegistryObject;",
		at = @At("HEAD"),
		cancellable = true,
		remap = false)
	private static <B extends Block> void registerBlock(Supplier<B> block, String name, Item.Properties properties, boolean needsItem, CallbackInfoReturnable<RegistryObject<Block>> cir) {
		if (MixinBlock.has(name)) {
			DmAdditions.LOGGER.info(String.format("Changing %s's behavior", name));

			RegistryObject<Block> blockObj = RegistryHandler.BLOCKS.register(name, MixinBlock.get(name).supplier);
			if (needsItem) {
				RegistryHandler.ITEMS.register(name,
					() -> new BaseBlockItem(blockObj.get(), properties));
			}
			cir.setReturnValue(blockObj);
			cir.cancel();
		}
	}

}
