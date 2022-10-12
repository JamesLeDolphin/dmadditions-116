package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.blocks.FastReturn;
import com.jdolphin.dmadditions.blocks.Randomiser;
import com.swdteam.common.init.DMTabs;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.swdteam.common.init.DMBlocks.registerBlock;

public class DMAdditionsBlocks {
	public static final Map<String, Supplier<Block>> MIXIN_BLOCKS;

	static {
		MIXIN_BLOCKS = new HashMap<>();

		MIXIN_BLOCKS.put("fast_return_lever",
				() -> new FastReturn(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)));

	}

//	public static final RegistryObject<Block> RANDOMISER = registerBlock(
//			() -> new Randomiser(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
//			"randomizer", DMTabs.DM_TARDIS);

}
