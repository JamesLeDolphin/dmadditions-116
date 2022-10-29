package com.jdolphin.dmadditions.block;

import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.block.tardis.SonicInterfaceBlock;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem;
import com.swdteam.common.tileentity.tardis.SonicInterfaceTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.network.packets.PacketXPSync;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BetterSonicInterfaceBlock extends SonicInterfaceBlock implements IBetterPanel, IBetterBlockTooltip {
	protected static String TXT_INSERT_SONIC = "insert_sonic";
	protected static String TXT_REMOVE_SONIC = "remove_sonic";
	protected static String TXT_OPEN_INTERFACE = "open_interface";

	public BetterSonicInterfaceBlock(Properties properties) {
		super(properties);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide) {
			boolean insertSonic = false;
			Vector3d hitVec = hit.getLocation();
			float mouseX = (float)((int)(100.0F * (float)(hitVec.x() - (double)pos.getX()))) / 100.0F;
			float mouseZ = (float)((int)(100.0F * (float)(hitVec.z() - (double)pos.getZ()))) / 100.0F;

			AttachFace face = state.getValue(BlockStateProperties.ATTACH_FACE);
			Direction direction = state.getValue(AbstractRotateableWaterLoggableBlock.FACING);

			if (face.equals(AttachFace.WALL)) {
				switch (direction) {
					case EAST:
					default:
						mouseX = 1 - ((int) (100.0F * (float) (hitVec.y() - (double) pos.getY()))) / 100.0F;
						break;
					case WEST:
						mouseX = ((int) (100.0F * (float) (hitVec.y() - (double) pos.getY()))) / 100.0F;
						break;
					case NORTH:
						mouseZ = ((int) (100.0F * (float) (hitVec.y() - (double) pos.getY()))) / 100.0F;
						break;

					case SOUTH:
						mouseZ = 1 - ((int) (100.0F * (float) (hitVec.y() - (double) pos.getY()))) / 100.0F;
						break;
				}
			}

			switch(direction) {
				case EAST:
					if (mouseX <= 0.97F && mouseX >= 0.79F && mouseZ >= 0.4F && mouseZ <= 0.59F) {
						insertSonic = true;
						break;
					}

					insertSonic = false;
					break;
				case SOUTH:
					if (mouseX <= 0.59F && mouseX >= 0.4F && mouseZ >= 0.79F && mouseZ <= 0.97F) {
						insertSonic = true;
						break;
					}

					insertSonic = false;
					break;
				case WEST:
					if (mouseX >= 0.03F && mouseX <= 0.21F && mouseZ >= 0.4F && mouseZ <= 0.59F) {
						insertSonic = true;
						break;
					}

					insertSonic = false;
					break;
				default:
					insertSonic = mouseX <= 0.59F && mouseX >= 0.4F && mouseZ >= 0.03F && mouseZ <= 0.21F;
			}

			TileEntity te = worldIn.getBlockEntity(pos);
			if (te instanceof SonicInterfaceTileEntity) {
				SonicInterfaceTileEntity sonic = (SonicInterfaceTileEntity)te;
				if (insertSonic) {
					if (sonic.getScrewdriver() != null && (sonic.getScrewdriver() == null || sonic.getScrewdriver().getItem() instanceof SonicScrewdriverCustomizedItem)) {
						if (sonic.getScrewdriver() != null && player.getItemInHand(handIn).isEmpty()) {
							player.setItemInHand(handIn, sonic.getScrewdriver());
							sonic.setScrewdriver(ItemStack.EMPTY);
							sonic.sendUpdates();
							worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMSoundEvents.TARDIS_SONIC_WORKBENCH_REMOVE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
						}
					} else if (player.getItemInHand(handIn).getItem() instanceof SonicScrewdriverCustomizedItem) {
						sonic.setScrewdriver(player.getItemInHand(handIn));
						((SonicScrewdriverCustomizedItem)sonic.getScrewdriver().getItem()).checkIsSetup(sonic.getScrewdriver());
						player.setItemInHand(handIn, ItemStack.EMPTY);
						sonic.sendUpdates();
						worldIn.playSound(null, pos.getX(), pos.getY(), pos.getZ(), DMSoundEvents.TARDIS_SONIC_WORKBENCH_INSERT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
						return ActionResultType.CONSUME;
					}
				} else if (sonic.getScrewdriver() != null && sonic.getScrewdriver().getItem() instanceof SonicScrewdriverCustomizedItem) {
					NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketXPSync(player.totalExperience, false));
					NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 4));
				}
			}
		}

		return ActionResultType.CONSUME;
	}

	public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) {
		return IBetterPanel.super.getName(state, pos, hitVec, player);
	}

	@Override
	public String getTooltipTranslationKey(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) {
		String txt = null;
		float mouseX = ((int) (100.0F * (float) (hitVec.x() - (double) pos.getX()))) / 100.0F;
		float mouseZ = ((int) (100.0F * (float) (hitVec.z() - (double) pos.getZ()))) / 100.0F;

		AttachFace face = state.getValue(BlockStateProperties.ATTACH_FACE);
		Direction direction = state.getValue(AbstractRotateableWaterLoggableBlock.FACING);

		if (face.equals(AttachFace.WALL)) {
			switch (direction) {
				case EAST:
				default:
					mouseX = 1 - ((int) (100.0F * (float) (hitVec.y() - (double) pos.getY()))) / 100.0F;
					break;
				case WEST:
					mouseX = ((int) (100.0F * (float) (hitVec.y() - (double) pos.getY()))) / 100.0F;
					break;
				case NORTH:
					mouseZ = ((int) (100.0F * (float) (hitVec.y() - (double) pos.getY()))) / 100.0F;
					break;

				case SOUTH:
					mouseZ = 1 - ((int) (100.0F * (float) (hitVec.y() - (double) pos.getY()))) / 100.0F;
					break;
			}
		}

		switch (direction) {
			case EAST:
				if (mouseX <= 0.97F && mouseX >= 0.79F && mouseZ >= 0.4F && mouseZ <= 0.59F) {
					txt = TXT_INSERT_SONIC;
				} else {
					txt = TXT_OPEN_INTERFACE;
				}
				break;
			case SOUTH:
				if (mouseX <= 0.59F && mouseX >= 0.4F && mouseZ >= 0.79F && mouseZ <= 0.97F) {
					txt = TXT_INSERT_SONIC;
				} else {
					txt = TXT_OPEN_INTERFACE;
				}
				break;
			case WEST:
				if (mouseX >= 0.03F && mouseX <= 0.21F && mouseZ >= 0.4F && mouseZ <= 0.59F) {
					txt = TXT_INSERT_SONIC;
				} else {
					txt = TXT_OPEN_INTERFACE;
				}
				break;
			default:
				if (mouseX <= 0.59F && mouseX >= 0.4F && mouseZ >= 0.03F && mouseZ <= 0.21F) {
					txt = TXT_INSERT_SONIC;
				} else {
					txt = TXT_OPEN_INTERFACE;
				}
		}

		SonicInterfaceTileEntity te = (SonicInterfaceTileEntity) player.getCommandSenderWorld().getBlockEntity(pos);
		if (te.getScrewdriver() != null && te.getScrewdriver().getItem() instanceof SonicScrewdriverCustomizedItem && txt.equals(TXT_INSERT_SONIC)) {
			txt = TXT_REMOVE_SONIC;
		}

		return txt;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
		super.createBlockStateDefinition(state);
		state.add(FACE);
	}

	@Override
	public BlockState updateShape(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
		return IBetterPanel.super.updateShape(state1, dir, state2, world, pos1, pos2);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		return IBetterPanel.super.canSurvive(state, reader, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return IBetterPanel.super.getStateForPlacement(context);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getShape(state, worldIn, pos, context);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getCollisionShape(state, worldIn, pos, context);
	}
}
