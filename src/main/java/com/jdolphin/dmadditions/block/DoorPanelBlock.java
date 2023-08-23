// Copyright 2023 Bug1312 (bug@bug1312.com)
// Edited 2023 JamesLeDolphin
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.tileentity.DoorPanelTileEntity;
import com.swdteam.common.block.IBlockTooltip;
import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DoorPanelBlock extends RotatableTileEntityBase.WaterLoggable implements IBlockTooltip {

	protected static final VoxelShape SHAPE_N = VoxelShapes.or(VoxelShapes.box(0, 0, 0, 1, 2/16D, 1), VoxelShapes.box(5.5/16D, 2/16D, 9/16D, 10.5/16D, 3/16D, 13/16D), VoxelShapes.box(6.5/16D, 3/16D, 9/16D, 9.5/16D, 7/16D, 12/16D), VoxelShapes.box(2/16D, 2/16D, 3/16D, 14/16D, 3/16D, 7/16D));
	protected static final VoxelShape SHAPE_E = VoxelShapes.or(VoxelShapes.box(0, 0, 0, 1, 2/16D, 1), VoxelShapes.box(3/16D, 2/16D, 5.5/16D, 7/16D, 3/16D, 10.5/16D), VoxelShapes.box(4/16D, 3/16D, 6.5/16D, 7/16D, 7/16D, 9.5/16D), VoxelShapes.box(9/16D, 2/16D, 2/16D, 13/16D, 3/16D, 14/16D));
	protected static final VoxelShape SHAPE_S = VoxelShapes.or(VoxelShapes.box(0, 0, 0, 1, 2/16D, 1), VoxelShapes.box(5.5/16D, 2/16D, 3/16D, 10.5/16D, 3/16D, 7/16D), VoxelShapes.box(6.5/16D, 3/16D, 4/16D, 9.5/16D, 7/16D, 7/16D), VoxelShapes.box(2/16D, 2/16D, 9/16D, 14/16D, 3/16D, 13/16D));
	protected static final VoxelShape SHAPE_W = VoxelShapes.or(VoxelShapes.box(0, 0, 0, 1, 2/16D, 1), VoxelShapes.box(9/16D, 2/16D, 5.5/16D, 13/16D, 3/16D, 10.5/16D), VoxelShapes.box(9/16D, 3/16D, 6.5/16D, 12/16D, 7/16D, 9.5/16D), VoxelShapes.box(3/16D, 2/16D, 2/16D, 7/16D, 3/16D, 14/16D));

	public static final BooleanProperty LOCKED = BooleanProperty.create("locked");
	public static final BooleanProperty OPENED = BooleanProperty.create("open");

	public static List<DoorPanelButtons> buttons = new ArrayList<DoorPanelButtons>();

	public DoorPanelBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
		this.registerDefaultState(super.defaultBlockState().setValue(LOCKED, false).setValue(OPENED, false));
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LOCKED).add(OPENED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		Direction facing = state.getValue(FACING);
		switch (facing) {
			case NORTH: default: return SHAPE_N;
			case EAST: return SHAPE_E;
			case SOUTH: return SHAPE_S;
			case WEST: return SHAPE_W;
		}
	}

	@Override
	public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
		return direction == Direction.DOWN && !this.canSurvive(state2, world, pos1) ? Blocks.AIR.defaultBlockState() : super.updateShape(state1, direction, state2, world, pos1, pos2);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		return canSupportCenter(world, pos.below(), Direction.UP);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if (!world.isClientSide && hand == Hand.MAIN_HAND) {
			if (world.dimension().equals(DMDimensions.TARDIS)) {
				Vector3d mouseVector = rayTraceResult.getLocation();
				double mouseX = mouseVector.x() - pos.getX();
				double mouseZ = mouseVector.z() - pos.getZ();
				DoorPanelButtons button = getButtonFromMouse(mouseX, mouseZ, state.getValue(FACING));

				if (button != null) {
					TileEntity tile = world.getBlockEntity(pos);
					if (tile instanceof DoorPanelTileEntity) {
						DoorPanelTileEntity panel = ((DoorPanelTileEntity) tile);

						switch (button) {
							case DOOR:
								boolean open = panel.toggleDoors(player);
								if (state.getValue(OPENED) != open) {
									world.setBlockAndUpdate(pos, state.setValue(OPENED, open));
									world.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1, 1);
								}
								break;
							case LOCK:
								boolean lock = panel.toggleLocks(player);
								if (state.getValue(LOCKED) != lock) {
									world.setBlockAndUpdate(pos, state.setValue(LOCKED, lock));
									world.playSound(null, pos, DMSoundEvents.TARDIS_LOCK.get(), SoundCategory.BLOCKS, 1, 1);
								}
								if (lock) {
									world.setBlockAndUpdate(pos, state.setValue(LOCKED, true).setValue(OPENED, false));
									world.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1, 1);
								}
								break;
						}
					}
				}
			}
			if (DmAdditions.hasNTM()) {
				if (net.tardis.mod.helper.WorldHelper.areDimensionTypesSame(world, net.tardis.mod.world.dimensions.TDimensions.DimensionTypes.TARDIS_TYPE)) {
					Vector3d mouseVector = rayTraceResult.getLocation();
					double mouseX = mouseVector.x() - pos.getX();
					double mouseZ = mouseVector.z() - pos.getZ();
					DoorPanelButtons button = getButtonFromMouse(mouseX, mouseZ, state.getValue(FACING));
					net.tardis.mod.helper.TardisHelper.getConsole(world.getServer(), world).ifPresent(tile -> {
							for(net.tardis.mod.entity.DoorEntity ent : tile.getLevel().getEntitiesOfClass(net.tardis.mod.entity.DoorEntity.class, new AxisAlignedBB(tile.getBlockPos()).inflate(25))) {
					if (button != null) {

							switch (button) {
								case DOOR:
									boolean closed = ent.getOpenState() == net.tardis.mod.enums.EnumDoorState.CLOSED;
									world.setBlockAndUpdate(pos, state.setValue(OPENED, closed));
									if (ent.isLocked()) {
										player.displayClientMessage(net.tardis.mod.blocks.exteriors.ExteriorBlock.LOCKED, true);
									}
									if	(!ent.isLocked() && ent.getOpenState() == net.tardis.mod.enums.EnumDoorState.CLOSED) ent.setOpenState(net.tardis.mod.enums.EnumDoorState.BOTH);
									else if (!ent.isLocked()) ent.setOpenState(net.tardis.mod.enums.EnumDoorState.CLOSED);
									ent.playSound(ent.getOpenState() == net.tardis.mod.enums.EnumDoorState.CLOSED ? net.tardis.mod.sounds.TSounds.DOOR_CLOSE.get() : net.tardis.mod.sounds.TSounds.DOOR_OPEN.get(), 1F, 1F);
									break;
								case LOCK:
									boolean locked = ent.isLocked();
									world.setBlockAndUpdate(pos, state.setValue(LOCKED, locked));
									ent.setLocked(!locked);
									ent.playSound(locked ? net.tardis.mod.sounds.TSounds.DOOR_UNLOCK.get() : net.tardis.mod.sounds.TSounds.DOOR_LOCK.get(), 1F, 1F);
									break;
							}
						}
					}
				});
				}
			}
		}
		return ActionResultType.CONSUME;

	}

	@Override
	public ITextComponent getName(BlockState state, BlockPos pos, Vector3d vector, PlayerEntity player) {
		double mouseX = vector.x() - pos.getX();
		double mouseZ = vector.z() - pos.getZ();

		DoorPanelButtons button = getButtonFromMouse(mouseX, mouseZ, state.getValue(FACING));
		if (button == null) return null;

		switch (button) {
			case DOOR: return state.getValue(OPENED) ? new TranslationTextComponent("tooltip.dm_door_panel.close") : new TranslationTextComponent("tooltip.dm_door_panel.open");
			case LOCK: return state.getValue(LOCKED) ? new TranslationTextComponent("tooltip.dm_door_panel.unlock") : new TranslationTextComponent("tooltip.dm_door_panel.lock");
			default: return null;
		}

	}

	@Nullable
	private DoorPanelButtons getButtonFromMouse(double mouseX, double mouseZ, Direction facing) {
		for (DoorPanelButtons button : DoorPanelButtons.values()) {
			if (button.areas.containsKey(facing)) {
				Vector3d vector = button.areas.get(facing);

				float width = button.width;
				float height = button.height;
				double x = vector.x;
				double z = vector.z;

				if (
					(facing == Direction.NORTH && mouseX <= x && mouseZ <= z && mouseX >= x - width && mouseZ >= z - height) ||
						(facing == Direction.SOUTH && mouseX >= x && mouseZ >= z && mouseX <= x + width && mouseZ <= z + height) ||
						(facing == Direction.EAST && mouseX >= x && mouseX <= x + height && mouseZ <= z && mouseZ >= z - width) ||
						(facing == Direction.WEST && mouseX <= x && mouseX >= x - height && mouseZ >= z && mouseZ <= z + width)
				) return button;
			}
		}

		return null;
	}

	private static enum DoorPanelButtons {
		DOOR(12, 4, 2, 9),
		LOCK(3, 3, 6.5f, 4);

		Map<Direction, Vector3d> areas = new HashMap<Direction, Vector3d>();
		float width, height;

		DoorPanelButtons(int width, int height, float left, float top) {
			float f = 1.0F / 16.0F;

			float invertLeft = 16 - left;
			float invertTop = 16 - top;

			this.width = width * f;
			this.height = height * f;

			areas.put(Direction.NORTH, new Vector3d(invertLeft * f, 0, invertTop  * f));
			areas.put(Direction.EAST,  new Vector3d(top        * f, 0, invertLeft * f));
			areas.put(Direction.SOUTH, new Vector3d(left       * f, 0, top        * f));
			areas.put(Direction.WEST,  new Vector3d(invertTop  * f, 0, left       * f));

			buttons.add(this);
		}
	}

}