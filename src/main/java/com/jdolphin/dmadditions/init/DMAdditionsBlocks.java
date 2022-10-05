package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.blocks.FastReturn;
import com.swdteam.common.block.tardis.FastReturnLeverBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import static com.swdteam.common.RegistryHandler.BLOCKS;
import static com.swdteam.common.init.DMBlocks.FAST_RETURN_LEVER;

public class DMAdditionsBlocks {
    public static final RegistryObject<Block> FAST_RETURN = BLOCKS.register("fast_return",
            () -> new FastReturn(Block.Properties.copy(FAST_RETURN_LEVER.get())));


//    static {
////        FAST_RETURN = registerBlock(() -> {
////            return new FastReturn(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE));
////        }, "fast_return", DMTabs.DM_TARDIS);
//    }
}
