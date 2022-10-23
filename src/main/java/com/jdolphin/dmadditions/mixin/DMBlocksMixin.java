package com.jdolphin.dmadditions.mixin;

import com.swdteam.common.RegistryHandler;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.item.BaseBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

import static com.jdolphin.dmadditions.init.DMABlocks.MIXIN_BLOCKS;

@Mixin({DMBlocks.class})
public abstract class DMBlocksMixin {

	@Inject(method = "registerBlock(Ljava/util/function/Supplier;Ljava/lang/String;Lnet/minecraft/item/Item$Properties;Z)Lnet/minecraftforge/fml/RegistryObject;",
			at = @At("HEAD"),
			cancellable = true)
	private static <B extends Block> void registerBlock(Supplier<B> block, String name, Item.Properties properties, boolean needsItem, CallbackInfoReturnable<RegistryObject<Block>> cir) {
//		System.out.printf("Block is being registered: %s!%n", name);
		if (MIXIN_BLOCKS.containsKey(name)) {
			System.out.printf("Mi want chanj dis bluk: %s!%n", name);

			RegistryObject<Block> blockObj = RegistryHandler.BLOCKS.register(name, MIXIN_BLOCKS.get(name));
			if (needsItem) {
				RegistryHandler.ITEMS.register(name,
						() -> new BaseBlockItem(blockObj.get(), properties));
			}
			cir.setReturnValue(blockObj);
			cir.cancel();
		}
	}

}
