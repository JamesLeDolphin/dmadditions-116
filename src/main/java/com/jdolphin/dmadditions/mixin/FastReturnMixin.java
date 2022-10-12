package com.jdolphin.dmadditions.mixin;

import static com.swdteam.common.init.DMBlocks.registerBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.jdolphin.dmadditions.blocks.FastReturn;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMTabs;

import net.minecraft.block.LeverBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

@Mixin({DMBlocks.class})
public abstract class FastReturnMixin extends LeverBlock{


    @SuppressWarnings("unused")
	private static Object FAST_RETURN_LEVER = DMBlocks.FAST_RETURN_LEVER;

    public FastReturnMixin(Properties p_i48369_1_) {
		super(p_i48369_1_);
		// TODO Auto-generated constructor stub
	}

	@Inject(
            at = @At(value = "RETURN", ordinal = 239),
            method = {"Lcom/swdteam/common/init/DMBlocks;FAST_RETURN_LEVER:Lnet/minecraftforge/fml/RegistryObject;"}
    )
    private static void onSomething(CallbackInfoReturnable<Object> cir) {
        cir.setReturnValue(
        FAST_RETURN_LEVER = registerBlock(() -> {
            return new FastReturn(Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE));
        }, "fast_return_lever", DMTabs.DM_TARDIS));
    }



}
