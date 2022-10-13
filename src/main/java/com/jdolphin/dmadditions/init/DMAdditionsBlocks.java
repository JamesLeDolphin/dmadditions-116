package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.blocks.BetterFastReturnLeverBlock;
import com.jdolphin.dmadditions.blocks.BetterFlightLeverBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DMAdditionsBlocks {
	public static final Map<String, Supplier<Block>> MIXIN_BLOCKS;

	static {
		MIXIN_BLOCKS = new HashMap<>();

		MIXIN_BLOCKS.put("fast_return_lever",
				() -> new BetterFastReturnLeverBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)));

		MIXIN_BLOCKS.put("flight_lever",
				() -> new BetterFlightLeverBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)));

	}

//	public static final RegistryObject<Block> RANDOMISER = registerBlock(
//			() -> new Randomiser(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
//			"randomizer", DMTabs.DM_TARDIS);

}
