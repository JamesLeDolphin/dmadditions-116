package com.jdolphin.dmadditions.init;

import com.jdolphin.dmadditions.blocks.BetterFastReturnLeverBlock;
import com.jdolphin.dmadditions.blocks.BetterFlightLeverBlock;
import com.jdolphin.dmadditions.blocks.CoralFlightLever;
import com.swdteam.common.init.DMTabs;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.Blocks;
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
				() -> new BetterFastReturnLeverBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)));

		MIXIN_BLOCKS.put("flight_lever",
				() -> new BetterFlightLeverBlock(AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)));

	}
	public static final RegistryObject<Block> CORAL_FLIGHT_LEVER = registerBlock(
			() -> new CoralFlightLever(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
			"coral_flight_lever", DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> COPPER_FLIGHT_LEVER = registerBlock(
		() -> new CoralFlightLever(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
		"copper_flight_lever", DMTabs.DM_TARDIS);

//	public static final RegistryObject<Block> RANDOMISER = registerBlock(
//			() -> new Randomiser(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
//			"randomizer", DMTabs.DM_TARDIS);

}
