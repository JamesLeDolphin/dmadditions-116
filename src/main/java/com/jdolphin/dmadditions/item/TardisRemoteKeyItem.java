package com.jdolphin.dmadditions.item;

import com.swdteam.common.block.tardis.PoliceBoxDoorBlock;
import com.swdteam.common.block.tardis.RoundelDoorBlock;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.item.StattenheimRemoteItem;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.common.tileentity.tardis.DoubleDoorsTileEntity;
import com.swdteam.common.tileentity.tardis.RoundelDoorTileEntity;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class TardisRemoteKeyItem extends StattenheimRemoteItem {
	String tardisLocation;

	public TardisRemoteKeyItem(Properties properties, String tardisLocation) {
		super(properties);
		this.tardisLocation = tardisLocation;
	}

	@Override

	public ActionResultType useOn(ItemUseContext context) {
		if (context.getLevel().isClientSide)
			return super.useOn(context);

		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState blockState = world.getBlockState(pos);
		TileEntity te;
		TardisData data;
		PlayerEntity player = context.getPlayer();
		ItemStack handItem = context.getItemInHand();
		if (player == null) return ActionResultType.FAIL;

		// linking stuff
		if (!(handItem.hasTag() && handItem.getTag().contains(DMNBTKeys.LINKED_ID))) {

			if (player.isShiftKeyDown() && blockState.getBlock() == DMBlocks.TARDIS.get()) {
				te = world.getBlockEntity(pos);
				if (te instanceof TardisTileEntity) {
					data = DMTardis.getTardis(((TardisTileEntity) te).globalID);
					if (data.hasPermission(context.getPlayer(), TardisData.PermissionType.DEFAULT)) {
						CompoundNBT tag = new CompoundNBT();
						tag.putInt(DMNBTKeys.LINKED_ID, data.getGlobalID());
						context.getItemInHand().setTag(tag);
						ChatUtil.sendCompletedMsg(player, "Linked key to TARDIS " + data.getGlobalID(), ChatUtil.MessageType.CHAT);
					}
				}
			}

			return ActionResultType.CONSUME;
		}

		// key stuff

		if (blockState.getBlock() == DMBlocks.TARDIS.get() || blockState.getBlock() instanceof PoliceBoxDoorBlock || blockState.getBlock() instanceof RoundelDoorBlock) {
			te = world.getBlockEntity(pos);
			if (blockState.getBlock() instanceof PoliceBoxDoorBlock && blockState.getValue(PoliceBoxDoorBlock.HALF) == DoubleBlockHalf.UPPER) {
				te = world.getBlockEntity(pos.below());
			}

			if (te instanceof TardisTileEntity) {
				data = DMTardis.getTardis(((TardisTileEntity) te).globalID);
				if (data.getGlobalID() == context.getItemInHand().getTag().getInt(DMNBTKeys.LINKED_ID)) {
					data.setLocked(!data.isLocked());
					data.save();
					if (data.isLocked()) {
						((TardisTileEntity) te).closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);
					}

					world.playSound(null, pos, DMSoundEvents.TARDIS_LOCK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					ChatUtil.sendCompletedMsg(player, "You have " + (data.isLocked() ? "locked" : "unlocked") + " your TARDIS!", ChatUtil.MessageType.STATUS_BAR);

					return ActionResultType.CONSUME;
				} else {
					ChatUtil.sendError(player, "This isn't the key for your TARDIS!", ChatUtil.MessageType.STATUS_BAR);
					return ActionResultType.FAIL;
				}
			}

			if (te instanceof DoubleDoorsTileEntity || te instanceof RoundelDoorTileEntity) {
				data = DMTardis.getTardisFromInteriorPos(pos);

				if (data == null) return ActionResultType.FAIL;
				if (data.getGlobalID() == handItem.getTag().getInt(DMNBTKeys.LINKED_ID)) {
					if (te instanceof RoundelDoorTileEntity) {
						((RoundelDoorTileEntity) te).setMain(true);
					}

					if (te instanceof DoubleDoorsTileEntity) {
						((DoubleDoorsTileEntity) te).setMain(true);
					}

					data.setLocked(!data.isLocked());
					data.save();
					if (data.isLocked()) {
						if (te instanceof DoubleDoorsTileEntity) {
							((DoubleDoorsTileEntity) te).setOpen(TardisDoor.BOTH, false);
						}

						if (te instanceof RoundelDoorTileEntity) {
							RoundelDoorTileEntity door = (RoundelDoorTileEntity) te;
							door.setOpen(false);
							int doorPart = blockState.getValue(RoundelDoorBlock.DOOR_PART);
							RoundelDoorTileEntity otherDoor1;
							RoundelDoorTileEntity otherDoor2;
							switch (doorPart) {
								case 0:
								default:
									otherDoor1 = (RoundelDoorTileEntity) world.getBlockEntity(pos.above());
									otherDoor2 = (RoundelDoorTileEntity) world.getBlockEntity(pos.above().above());
									if (otherDoor1 != null) {
										otherDoor1.setOpen(false);
									}

									if (otherDoor2 != null) {
										otherDoor2.setOpen(false);
									}
									break;
								case 1:
									otherDoor1 = (RoundelDoorTileEntity) world.getBlockEntity(pos.above());
									otherDoor2 = (RoundelDoorTileEntity) world.getBlockEntity(pos.below());
									if (otherDoor1 != null) {
										otherDoor1.setOpen(false);
									}

									if (otherDoor2 != null) {
										otherDoor2.setOpen(false);
									}
									break;
								case 2:
									otherDoor1 = (RoundelDoorTileEntity) world.getBlockEntity(pos.below());
									otherDoor2 = (RoundelDoorTileEntity) world.getBlockEntity(pos.below().below());
									if (otherDoor1 != null) {
										otherDoor1.setOpen(false);
									}

									if (otherDoor2 != null) {
										otherDoor2.setOpen(false);
									}
							}
						}
					}

					world.playSound(null, pos, DMSoundEvents.TARDIS_LOCK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					ChatUtil.sendCompletedMsg(player, "You have " + (data.isLocked() ? "locked" : "unlocked") + " your TARDIS!", ChatUtil.MessageType.STATUS_BAR);
					return ActionResultType.CONSUME;
				} else {
					ChatUtil.sendError(player, "This isn't the key for your TARDIS!", ChatUtil.MessageType.STATUS_BAR);
					return ActionResultType.FAIL;
				}
			}

			return ActionResultType.FAIL;
		}

		// remote stuff
		return super.useOn(context);
	}

}