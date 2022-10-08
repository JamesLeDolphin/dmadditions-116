package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.blocks.FastReturn;
import com.swdteam.common.init.DMTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;

import static com.swdteam.common.init.DMBlocks.registerBlock;

public class DMAdditionsBlocks {
	public static final RegistryObject<Block> FAST_RETURN = registerBlock(
			() -> new FastReturn(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
			"fast_return", DMTabs.DM_TARDIS);
}
