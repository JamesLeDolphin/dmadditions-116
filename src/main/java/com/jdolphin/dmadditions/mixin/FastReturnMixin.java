
package com.jdolphin.dmadditions.mixin;

import com.swdteam.common.init.DMBlocks;
import net.minecraft.block.LeverBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(DMBlocks.class)
public abstract class FastReturnMixin {


    @Inject(at = @At("RETURN"), method = "Lcom/swdteam/common/init/DMBlocks;FAST_RETURN_LEVER:Lnet/minecraftforge/fml/RegistryObject;"
    )
    private void onRegister(CallbackInfoReturnable<Something> cir) {

        //Cancel the method at it's head, so it basically does nothing
        cir.cancel();

    }
}
