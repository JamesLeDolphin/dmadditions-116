package com.jdolphin.dmadditions.init;

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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.swdteam.common.init.DMBlocks.registerBlock;

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
	public static final RegistryObject<Block> RANDOMIZER = registerBlock(
		() -> new RandomizerBlock(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak()
			.noOcclusion().sound(SoundType.STONE)),
		"randomizer", DMTabs.DM_TARDIS);
	public static final RegistryObject<Block> TIMEKEEPER_CONSOLE = registerBlock(
		() -> new BetterFlightLeverBlock(net.minecraft.block.AbstractBlock.Properties.of(Material.STONE).instabreak().noOcclusion().sound(SoundType.STONE)),
		"timekeeper_console", DMTabs.DM_TARDIS);

	public static final RegistryObject<Block>
		WHITE_PLASTIC_ROUNDEL_CONTAINER,
		CLAY_ROUNDEL_CONTAINER,
		ORANGE_PLASTIC_ROUNDEL_CONTAINER,
		MAGENTA_PLASTIC_ROUNDEL_CONTAINER,
		LIGHT_BLUE_PLASTIC_ROUNDEL_CONTAINER,
		YELLOW_PLASTIC_ROUNDEL_CONTAINER,
		LIME_PLASTIC_ROUNDEL_CONTAINER,
		PINK_PLASTIC_ROUNDEL_CONTAINER,
		GRAY_PLASTIC_ROUNDEL_CONTAINER,
		LIGHT_GRAY_PLASTIC_ROUNDEL_CONTAINER,
		CYAN_PLASTIC_ROUNDEL_CONTAINER,
		PURPLE_PLASTIC_ROUNDEL_CONTAINER,
		BLUE_PLASTIC_ROUNDEL_CONTAINER,
		BROWN_PLASTIC_ROUNDEL_CONTAINER,
		GREEN_PLASTIC_ROUNDEL_CONTAINER,
		RED_PLASTIC_ROUNDEL_CONTAINER,
		BLACK_PLASTIC_ROUNDEL_CONTAINER;

	static {

		CLAY_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.CLAY).strength(0.6F, 0.6F).sound(SoundType.GRAVEL)), "clay_roundel_container", DMTabs.DM_TARDIS);
		WHITE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD).requiresCorrectToolForDrops()), "white_plastic_roundel_container", DMTabs.DM_TARDIS);
		ORANGE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "orange_plastic_roundel_container", DMTabs.DM_TARDIS);
		MAGENTA_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "magenta_plastic_roundel_container", DMTabs.DM_TARDIS);
		LIGHT_BLUE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "light_blue_plastic_roundel_container", DMTabs.DM_TARDIS);
		YELLOW_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "yellow_plastic_roundel_container", DMTabs.DM_TARDIS);
		LIME_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "lime_plastic_roundel_container", DMTabs.DM_TARDIS);
		PINK_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "pink_plastic_roundel_container", DMTabs.DM_TARDIS);
		GRAY_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "gray_plastic_roundel_container", DMTabs.DM_TARDIS);
		LIGHT_GRAY_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "light_gray_plastic_roundel_container", DMTabs.DM_TARDIS);
		CYAN_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "cyan_plastic_roundel_container", DMTabs.DM_TARDIS);
		PURPLE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "purple_plastic_roundel_container", DMTabs.DM_TARDIS);
		BLUE_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "blue_plastic_roundel_container", DMTabs.DM_TARDIS);
		BROWN_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "brown_plastic_roundel_container", DMTabs.DM_TARDIS);
		GREEN_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "green_plastic_roundel_container", DMTabs.DM_TARDIS);
		RED_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "red_plastic_roundel_container", DMTabs.DM_TARDIS);
		BLACK_PLASTIC_ROUNDEL_CONTAINER = registerBlock(() -> new RoundelContainerBlock(AbstractBlock.Properties.of(Material.WOOD).strength(2.0F, 2.5F).sound(SoundType.WOOD)), "black_plastic_roundel_container", DMTabs.DM_TARDIS);
	}
}

