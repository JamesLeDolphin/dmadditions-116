package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.blocks.FastReturn;
import com.jdolphin.dmadditions.items.TardisGoldKeyItem;
import com.swdteam.common.RegistryHandler;
import com.swdteam.common.block.tardis.FastReturnLeverBlock;
import com.swdteam.common.init.DMTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import static com.swdteam.common.init.DMBlocks.registerBlock;

public class DMAdditionsBlocks {
   /* public static RegistryObject<Block> FAST_RETURN = RegistryHandler.ITEMS.register("tardis_gold_key", ()
            -> new FastReturn(Block.getDrops().tab(DMTabs.DM_TARDIS), ""));*/
   public static RegistryObject<Block> FAST_RETURN;

    static {
        FAST_RETURN = registerBlock(() -> {
            return new FastReturn(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE));
        }, "fast_return", DMTabs.DM_TARDIS);
    }
}
