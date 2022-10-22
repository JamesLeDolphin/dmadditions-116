package com.jdolphin.dmadditions.init;

import static com.swdteam.common.init.DMBlocks.registerBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.jdolphin.dmadditions.blocks.BetterFastReturnLeverBlock;
import com.jdolphin.dmadditions.blocks.BetterFlightLeverBlock;
import com.jdolphin.dmadditions.blocks.RandomizerBlock;
import com.jdolphin.dmadditions.blocks.RoundelContainerBlock;
import com.swdteam.common.init.DMTabs;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;

public class DMAdditionsBlocks {
	public static final Map<String, Supplier<Block>> MIXIN_BLOCKS;

	static {
		MIXIN_BLOCKS = new HashMap<>();

		MIXIN_BLOCKS.put("fast_return_lever",
				() -> new BetterFastReturnLeverBlock(
						AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)));

		MIXIN_BLOCKS.put("flight_lever",
				() -> new BetterFlightLeverBlock(
						AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)));

	}

	public static final RegistryObject<Block> CORAL_FLIGHT_LEVER = registerBlock(
			() -> new BetterFlightLeverBlock(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE)
					.instabreak().noOcclusion().sound(SoundType.STONE)),
			"coral_flight_lever", DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> COPPER_FLIGHT_LEVER = registerBlock(
			() -> new BetterFlightLeverBlock(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE)
					.instabreak().noOcclusion().sound(SoundType.STONE)),
			"copper_flight_lever", DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> RANDOMISER = registerBlock(
			() -> new RandomizerBlock(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak()
					.noOcclusion().sound(SoundType.STONE)),
			"randomizer", DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> TIMEKEEPER_CONSOLE = registerBlock(
		() -> new BetterFlightLeverBlock(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
		"timekeeper_console", DMTabs.DM_TARDIS);

	public static final RegistryObject<Block> CYAN_PLASTIC_ROUNDEL_CONTAINER = registerBlock(
		() -> new RoundelContainerBlock(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
		"cyan_plastic_roundel_container", DMTabs.DM_TARDIS);
}
