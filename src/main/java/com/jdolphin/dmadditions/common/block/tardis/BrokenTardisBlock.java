package com.jdolphin.dmadditions.common.block.tardis;

import com.jdolphin.dmadditions.common.init.DMAItems;
import com.jdolphin.dmadditions.common.init.DMAPackets;
import com.jdolphin.dmadditions.common.network.CBOpenGUIPacket;
import com.jdolphin.dmadditions.common.network.SBGenerateBrokenInteriorPacket;
import com.jdolphin.dmadditions.common.tardis.BrokenTardisData;
import com.jdolphin.dmadditions.common.tardis.DMATardis;
import com.jdolphin.dmadditions.common.tileentity.BrokenTardisTileEntity;
import com.swdteam.common.block.IHaveNoItem;
import com.swdteam.common.block.TileEntityBaseBlock;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

public class BrokenTardisBlock extends TileEntityBaseBlock.WaterLoggable implements IHaveNoItem {
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public BrokenTardisBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state.setValue(FACING, context.getHorizontalDirection());
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	}

	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.box(0.0, 0.0, 0.0, 1, 2.0, 1);
	}

	public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClientSide()) {
			TileEntity tile = world.getBlockEntity(pos);
			if (tile instanceof BrokenTardisTileEntity) {
				BrokenTardisTileEntity tardis = (BrokenTardisTileEntity) tile;
				BrokenTardisData data = tardis.data;
				DMATardis.setAvailable(data, ((ServerWorld) world).getServer());
			}
		}
	}


	public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		if (!world.isClientSide) {
			TileEntity tile = world.getBlockEntity(pos);
			if (tile instanceof BrokenTardisTileEntity) {
				BrokenTardisTileEntity tardis = (BrokenTardisTileEntity) tile;
				tardis.setup();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void onProjectileHit(World world, BlockState blockState, BlockRayTraceResult rayTraceResult, ProjectileEntity projectile) {
		BlockPos pos = rayTraceResult.getBlockPos();
		world.playSound(null, pos, DMSoundEvents.TARDIS_CLOISTER_BELL.get(), SoundCategory.PLAYERS, 1.0f, 1.0f);
		super.onProjectileHit(world, blockState, rayTraceResult, projectile);
	}

	public BlockRenderType getRenderShape(BlockState p_149645_1_) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@SuppressWarnings("deprecation")
	@Override
	@ParametersAreNonnullByDefault
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
		if (!world.isClientSide() && player instanceof ServerPlayerEntity) {
			if (hand.equals(Hand.MAIN_HAND)) {
				ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
				TileEntity tile = world.getBlockEntity(pos);
				if (tile instanceof BrokenTardisTileEntity) {
					BrokenTardisTileEntity tardisTile = (BrokenTardisTileEntity) tile;
					BrokenTardisData data = tardisTile.data;
					if (!tardisTile.isOpen()) {
						ItemStack stack = player.getItemInHand(hand);
						Item item = stack.getItem();
						if (item.equals(DMAItems.TARDIS_LOCKPICK.get())) {
							CBOpenGUIPacket packet = new CBOpenGUIPacket(pos, 1);
							DMAPackets.sendTo(serverPlayer, packet);
							SBGenerateBrokenInteriorPacket interiorPacket = new SBGenerateBrokenInteriorPacket(pos);
							DMAPackets.send(interiorPacket);
						} else {
							((ServerPlayerEntity) player).sendMessage(new TranslationTextComponent("notice.dmadditions.need_lockpick"), ChatType.CHAT, Util.NIL_UUID);
						}
					} else if (!data.has_generated) {
						SBGenerateBrokenInteriorPacket interiorPacket = new SBGenerateBrokenInteriorPacket(pos);
						DMAPackets.send(interiorPacket);
					}
				}
			}
		}

		return super.use(state, world, pos, player, hand, rayTrace);
	}
}
