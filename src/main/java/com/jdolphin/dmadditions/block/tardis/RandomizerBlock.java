package com.jdolphin.dmadditions.block.tardis;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.block.IBetterPanel;
import com.jdolphin.dmadditions.config.DMACommonConfig;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.ChatUtil.MessageType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class RandomizerBlock extends HorizontalBlock implements IBetterPanel {
	private String dimensionKey;

	public static final DirectionProperty FACING = IBetterPanel.FACING;

	protected static final VoxelShape SHAPE_N = VoxelShapes.or(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 8.0D), Block.box(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D));
	protected static final VoxelShape SHAPE_E = VoxelShapes.or(Block.box(8.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D));
	protected static final VoxelShape SHAPE_S = VoxelShapes.or(Block.box(0.0D, 0.0D, 8.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D));
	protected static final VoxelShape SHAPE_W = VoxelShapes.or(Block.box(0.0D, 0.0D, 0.0D, 8.0D, 2.0D, 16.0D), Block.box(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D));

	public RandomizerBlock(AbstractBlock.Properties properties) {
		super(properties);

		this.registerDefaultState(this.stateDefinition.any()
			.setValue(FACING, Direction.NORTH)
			.setValue(FACE, AttachFace.FLOOR)
		);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
		return IBetterPanel.super.getStateForPlacement(blockItemUseContext, defaultBlockState());
	}

	@Override
	public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos,
			ISelectionContext iSelectionContext) {

		return this.getShape(blockState, iBlockReader, blockPos, iSelectionContext);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
		Direction facing = state.getValue(FACING);
		if(!state.getValue(FACE).equals(AttachFace.FLOOR)) return IBetterPanel.super.getShape(state, reader, pos, selectionContext);

		switch (facing) {
			case NORTH:
			default:
				return SHAPE_N;
			case EAST:
				return SHAPE_E;
			case SOUTH:
				return SHAPE_S;
			case WEST:
				return SHAPE_W;
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, FACE);
	}

	public RegistryKey<World> dimensionWorldKey() {
		return RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(this.dimensionKey));
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
	                            BlockRayTraceResult rayTraceResult) {
		if (worldIn.isClientSide)
			return ActionResultType.PASS;

		if (handIn == Hand.MAIN_HAND) {
			if (ServerLifecycleHooks.getCurrentServer() == null)
				return ActionResultType.CONSUME;
			if (worldIn.dimension().equals(DMDimensions.TARDIS)) {
				ServerWorld level = (ServerWorld) worldIn;
				WorldBorder border = level.getWorldBorder();
				TardisData tardis = DMTardis.getTardisFromInteriorPos(pos);

				Location currentLocation = tardis.getCurrentLocation();
				BlockPos currentPos = currentLocation.getBlockPosition();

				double maxDistance = DMACommonConfig.randomizer_max.get();

				double maxX = Math.min(border.getMaxX(), currentPos.getX() + maxDistance);
				double minX = Math.max(border.getMinX(), currentPos.getX() - maxDistance);

				double maxZ = Math.min(border.getMaxZ(), currentPos.getZ() + maxDistance);
				double minZ = Math.max(border.getMinZ(), currentPos.getZ() - maxDistance);

				double xPos = Math.floor(Math.random() * (maxX - minX + 1) + minX);
				double zPos = Math.floor(Math.random() * (maxZ - minZ + 1) + minZ);
				double yPos = currentPos.getY();

				BlockPos newPos = new BlockPos(xPos, yPos, zPos);

				TardisFlightData flight = TardisFlightPool.getFlightData(tardis);

				flight.setPos(newPos);
				flight.recalculateLanding(true);
				TardisFlightPool.updateFlight(tardis, new Location(newPos, flight.dimensionWorldKey()));

				ChatUtil.sendCompletedMsg(player,
					new TranslationTextComponent("notice.dmadditions.tardis.randomizer_set", newPos.getX(), newPos.getZ()),
					MessageType.STATUS_BAR);

				worldIn.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1, 1);
			}


			if (DmAdditions.hasNTM()) {
				if (net.tardis.mod.helper.WorldHelper.areDimensionTypesSame(worldIn, net.tardis.mod.world.dimensions.TDimensions.DimensionTypes.TARDIS_TYPE)) {
					Random rand = new Random();
					net.tardis.mod.helper.TardisHelper.getConsole(worldIn.getServer(), worldIn).ifPresent(tile -> {
						if(!player.level.isClientSide() && tile.getLandTime() <= 0) {
							int rad = 5 * tile.coordIncr;
							BlockPos dest = tile.getDestinationPosition().offset(rad - rand.nextInt(rad * 2), 0, rad - rand.nextInt(rad * 2));
							tile.setDestination(tile.getDestinationDimension(), dest);
					}});
				}
			}


		}
		return ActionResultType.SUCCESS;
	}
}
