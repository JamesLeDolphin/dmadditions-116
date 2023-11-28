package com.jdolphin.dmadditions.item;

import com.swdteam.common.init.*;
import com.swdteam.common.item.TardisKeyItem;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.WorldUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


public class TardisRemoteKeyItem extends TardisKeyItem {
	public TardisRemoteKeyItem(Properties properties, String tardisLocation) {
		super(properties, tardisLocation);
	}

	@Override

	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();

		if (world.isClientSide)
			return super.useOn(context);

		BlockPos pos = context.getClickedPos();
		BlockState blockState = world.getBlockState(pos);
//        ChatUtil.sendCompletedMsg(context.getPlayer(), String.valueOf(blockState.getBlock().is(DMBlocks.TARDIS.get())) , ChatUtil.MessageType.CHAT);

		if (context.getItemInHand().hasTag() && context.getItemInHand().getTag().contains(DMNBTKeys.LINKED_ID)) {
			if (super.useOn(context).equals(ActionResultType.CONSUME)) return ActionResultType.CONSUME;

			BlockPos posUp = context.getClickedPos().above();
			boolean canContinue = false;
			if (WorldUtils.canPlace(world, pos, false)) {
				posUp = pos;
				canContinue = true;
			} else if (world.isEmptyBlock(posUp)) {
				canContinue = true;
			}

			if (!world.getBlockState(posUp).canBeReplaced(new DirectionalPlaceContext(world, posUp, Direction.NORTH, context.getItemInHand(), Direction.NORTH))) {
				return ActionResultType.CONSUME;
			}

			int tardisID = context.getItemInHand().getTag().getInt(DMNBTKeys.LINKED_ID);
			TardisData data = DMTardis.getTardis(tardisID);
			MinecraftServer server = context.getPlayer().getServer();
			ServerWorld tardisDim = server.getLevel(DMDimensions.TARDIS);
			context.getPlayer().getCooldowns().addCooldown(this, 400);
			context.getItemInHand().hurtAndBreak(1, context.getPlayer(), (player) -> {
				player.broadcastBreakEvent(context.getHand());
			});
			if (tardisDim.isLoaded(data.getInteriorSpawnPosition().toBlockPos())) {
				tardisDim.playSound(null, data.getInteriorSpawnPosition().x(), data.getInteriorSpawnPosition().y(), data.getInteriorSpawnPosition().z(), DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			if (!data.isInFlight()) {
				world.playSound(null, pos, DMSoundEvents.ENTITY_STATTENHEIM_REMOTE_SUMMON.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				BlockPos currentPos = data.getCurrentLocation().getBlockPosition();
				ServerWorld serverWorld = server.getLevel(data.getCurrentLocation().dimensionWorldKey());
				TileEntity te = serverWorld.getBlockEntity(currentPos);
				if (te instanceof TardisTileEntity) {
					if (TardisActionList.doAnimation(serverWorld, currentPos)) {
						((TardisTileEntity) te).setState(TardisState.DEMAT);
					} else {
						serverWorld.setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());
					}
				}
			}

			TardisFlightPool.completeFlight(context.getPlayer().getServer(), data);
			world.setBlockAndUpdate(posUp, DMBlocks.TARDIS.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, world.getBlockState(posUp).getBlock() instanceof FlowingFluidBlock));
			data.setPreviousLocation(data.getCurrentLocation());
			data.setCurrentLocation(posUp, world.dimension());
			TileEntity te = world.getBlockEntity(posUp);
			if (te instanceof TardisTileEntity) {
				TardisTileEntity tardis = (TardisTileEntity) te;
				tardis.globalID = tardisID;
				tardis.closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);
				tardis.rotation = context.getPlayer().getYHeadRot();
				tardis.setState(TardisState.REMAT);
				data.getCurrentLocation().setFacing(tardis.rotation);
			}

			data.save();
		} else if (blockState.getBlock() == DMBlocks.TARDIS.get() && context.getPlayer() != null) {
			TileEntity te = world.getBlockEntity(pos);
			if (te instanceof TardisTileEntity) {
				TardisData data = DMTardis.getTardis(((TardisTileEntity) te).globalID);
				if (data.hasPermission(context.getPlayer(), TardisData.PermissionType.DEFAULT)) {
					world.playSound(null, pos, DMSoundEvents.ENTITY_STATTENHEIM_REMOTE_SYNC.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					CompoundNBT tag = new CompoundNBT();
					tag.putInt(DMNBTKeys.LINKED_ID, data.getGlobalID());
					context.getItemInHand().setTag(tag);
					ChatUtil.sendCompletedMsg(context.getPlayer(), "Remote synced with TARDIS: " + data.getGlobalID(), ChatUtil.MessageType.CHAT);
				} else {
					data.noPermission(context.getPlayer());
				}
			}
		}
		return super.useOn(context);
	}


}
