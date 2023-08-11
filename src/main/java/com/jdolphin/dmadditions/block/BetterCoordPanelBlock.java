package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.DmAdditions;
import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.block.tardis.CoordPanelBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tileentity.tardis.CoordPanelTileEntity;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.SWDMathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

public class BetterCoordPanelBlock extends CoordPanelBlock implements IBetterPanel {
	public static List<CoordPanelButtons> buttons = new ArrayList<>();
	private Boolean didPressButton = false;


	public BetterCoordPanelBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
	}

	protected String formatIncrementMessage(Boolean add, Direction.Axis axis, CoordPanelTileEntity tile, Boolean ntm) {
		if (!ntm) {
			return (add ? "Added " : "Subtracted ") + tile.incrementValue + (add ? " to " : " from ") + axis.toString().toUpperCase() + " (" + TardisFlightPool.getFlightData(DMTardis.getTardisFromInteriorPos(tile.getBlockPos())).getPos(axis) + ")";
	 	} else return "";
    }

	@Override
	@SuppressWarnings("unchecked")
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide && handIn == Hand.MAIN_HAND) {
			worldIn.getBlockTicks().scheduleTick(pos, this, 5);
			if (state.getValue(BUTTON_PRESSED) == 0) {
				Vector3d hitVec = hit.getLocation();
				double mouseX = hitVec.x() - (double) pos.getX();
				double mouseZ = hitVec.z() - (double) pos.getZ();
				double mouseY = hitVec.y() - (double) pos.getY();
				CoordPanelButtons buttonClicked = this.getButton(mouseX, mouseY, mouseZ, state.getValue(RotatableTileEntityBase.FACING), state.getValue(FACE));
				TileEntity te = worldIn.getBlockEntity(pos);
				if (te instanceof CoordPanelTileEntity) {
					CoordPanelTileEntity tet = (CoordPanelTileEntity) te;
					if (worldIn.dimension().equals(DMDimensions.TARDIS)) {
						TardisData data = DMTardis.getTardisFromInteriorPos(pos);
						TardisFlightData flightData = null;

						if (data != null) {
							flightData = TardisFlightPool.getFlightData(data);
						}

						if (flightData != null) {
							switch (buttonClicked) {
								case ADD_X:
									flightData.incrementPos(tet.incrementValue, Direction.Axis.X);
									ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(true, Direction.Axis.X, tet, false), ChatUtil.MessageType.STATUS_BAR);
									break;
								case ADD_Y:
									flightData.incrementPos(tet.incrementValue, Direction.Axis.Y);
									ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(true, Direction.Axis.Y, tet, false), ChatUtil.MessageType.STATUS_BAR);
									break;
								case ADD_Z:
									flightData.incrementPos(tet.incrementValue, Direction.Axis.Z);
									ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(true, Direction.Axis.Z, tet, false), ChatUtil.MessageType.STATUS_BAR);
									break;
								case SUB_X:
									flightData.incrementPos(-tet.incrementValue, Direction.Axis.X);
									ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(false, Direction.Axis.X, tet, false), ChatUtil.MessageType.STATUS_BAR);
									break;
								case SUB_Y:
									flightData.incrementPos(-tet.incrementValue, Direction.Axis.Y);
									ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(false, Direction.Axis.Y, tet, false), ChatUtil.MessageType.STATUS_BAR);
									break;
								case SUB_Z:
									flightData.incrementPos(-tet.incrementValue, Direction.Axis.Z);
									ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(false, Direction.Axis.Z, tet, false), ChatUtil.MessageType.STATUS_BAR);
									break;
								case INCREMENT:
									if (player.isShiftKeyDown()) {
										if (tet.incrementValue == 1) {
											tet.incrementValue = 10000;
										} else {
											tet.incrementValue /= 10;
										}
									} else if (tet.incrementValue == 10000) {
										tet.incrementValue = 1;
									} else {
										tet.incrementValue *= 10;
									}

									ChatUtil.sendMessageToPlayer(player, "Coordinate Increment: " + tet.incrementValue, ChatUtil.MessageType.STATUS_BAR);
									break;
								case ROTATE:
									if (player.isShiftKeyDown()) {
										flightData.setRotationAngle(flightData.getRotationAngle() - 45.0F);
									} else {
										flightData.setRotationAngle(flightData.getRotationAngle() + 45.0F);
									}

									ChatUtil.sendCompletedMsg(player, DMTranslationKeys.TARDIS_FLIGHT_ROTATION_SET.getString() + SWDMathUtils.rotationToCardinal(flightData.getRotationAngle()), ChatUtil.MessageType.STATUS_BAR);
									TardisFlightPool.sendUpdates(data.getGlobalID());
									break;
								case AUTO_CALCULATE_Y:
									flightData.recalculateLanding(!flightData.shouldRecalculateLanding());
									ChatUtil.sendMessageToPlayer(player, "Auto Height Calculator (" + flightData.shouldRecalculateLanding() + ")", ChatUtil.MessageType.STATUS_BAR);
								case EMPTY:
							}

							if (buttonClicked != CoordPanelButtons.EMPTY && buttonClicked != CoordPanelButtons.AUTO_CALCULATE_Y && flightData != null) {
								flightData.setWaypoints(null);
								TardisFlightPool.sendUpdates(data.getGlobalID());
							}
						}
					}
/*
					if (DmAdditions.hasNTM()) {
						try {
*/
/*
							Class<?> WorldHelper = Class.forName("net.tardis.mod.helper.WorldHelper");
							Class<?> TDimensions = Class.forName("net.tardis.mod.world.dimensions.TDimensions");
							Class<?> TardisHelper = Class.forName("net.tardis.mod.helper.TardisHelper");
*//*


							if (net.tardis.mod.helper.WorldHelper.areDimensionTypesSame(worldIn, net.tardis.mod.world.dimensions.TDimensions.DimensionTypes.TARDIS_TYPE)) {
								net.tardis.mod.helper.TardisHelper.getConsole(worldIn.getServer(), worldIn).ifPresent(tile -> {
									switch (buttonClicked) {
										case ADD_X:
											tile.setDestination(tile.getDestinationDimension(),
												new BlockPos(tile.getDestinationPosition().getX() + tet.incrementValue,
													tile.getDestinationPosition().getY(), tile.getDestinationPosition().getZ()));
											ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(true, Direction.Axis.X, tet, true), ChatUtil.MessageType.STATUS_BAR);
											break;
										case ADD_Y:
											tile.setDestination(tile.getDestinationDimension(),
												new BlockPos(tile.getDestinationPosition().getX(),
													tile.getDestinationPosition().getY() + tet.incrementValue, tile.getDestinationPosition().getZ()));
											ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(true, Direction.Axis.Y, tet, true), ChatUtil.MessageType.STATUS_BAR);
											break;
										case ADD_Z:
											tile.setDestination(tile.getDestinationDimension(),
												new BlockPos(tile.getDestinationPosition().getX(),
													tile.getDestinationPosition().getY(), tile.getDestinationPosition().getZ() + tet.incrementValue));
											ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(true, Direction.Axis.Z, tet, true), ChatUtil.MessageType.STATUS_BAR);
											break;
										case SUB_X:
											tile.setDestination(tile.getDestinationDimension(),
												new BlockPos(tile.getDestinationPosition().getX() - tet.incrementValue,
													tile.getDestinationPosition().getY(), tile.getDestinationPosition().getZ()));
											ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(false, Direction.Axis.X, tet, true), ChatUtil.MessageType.STATUS_BAR);
											break;
										case SUB_Y:
											tile.setDestination(tile.getDestinationDimension(),
												new BlockPos(tile.getDestinationPosition().getX(),
													tile.getDestinationPosition().getY() - tet.incrementValue, tile.getDestinationPosition().getZ()));
											ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(false, Direction.Axis.Y, tet, true), ChatUtil.MessageType.STATUS_BAR);
											break;
										case SUB_Z:
											tile.setDestination(tile.getDestinationDimension(),
												new BlockPos(tile.getDestinationPosition().getX(),
													tile.getDestinationPosition().getY(), tile.getDestinationPosition().getZ() - tet.incrementValue));
											ChatUtil.sendMessageToPlayer(player, this.formatIncrementMessage(false, Direction.Axis.Z, tet, true), ChatUtil.MessageType.STATUS_BAR);
											break;
										case INCREMENT:
											if (player.isShiftKeyDown()) {
												if (tet.incrementValue == 1) {
													tet.incrementValue = 10000;
												} else {
													tet.incrementValue /= 10;
												}
											} else if (tet.incrementValue == 10000) {
												tet.incrementValue = 1;
											} else {
												tet.incrementValue *= 10;
											}


											ChatUtil.sendMessageToPlayer(player, "Coordinate Increment: " + tet.incrementValue, ChatUtil.MessageType.STATUS_BAR);
											break;
										case ROTATE:
											if (player.isShiftKeyDown()) {
												tile.rotate(Rotation.CLOCKWISE_90);
											} else {
												tile.rotate(Rotation.COUNTERCLOCKWISE_90);
											}

											ChatUtil.sendMessageToPlayer(player, "Rotated the TARDIS", ChatUtil.MessageType.STATUS_BAR);
											break;
									}

								});
							}
						}catch(Exception e){
							ChatUtil.sendError(player, e.getLocalizedMessage(), ChatUtil.MessageType.CHAT);
						}
					}
*/
				}

				if (buttonClicked == CoordPanelButtons.AUTO_CALCULATE_Y) {

					worldIn.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					worldIn.setBlockAndUpdate(pos, state.cycle(LIT));
				}

				if (buttonClicked != CoordPanelButtons.EMPTY && buttonClicked != CoordPanelButtons.AUTO_CALCULATE_Y) {
					worldIn.setBlockAndUpdate(pos, state.setValue(BUTTON_PRESSED, buttonClicked.ordinal() + 1));
					worldIn.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.didPressButton = true;
				}
			}
		}

		return ActionResultType.CONSUME;
	}

	protected static boolean checkButton(CoordPanelButtons button, Vector2f vec, Direction facing, double mouseX, double mouseZ, boolean isReverseX, boolean isReverseZ) {
		float width = button.width;
		float height = button.height;
		float x = vec.x;
		float z = vec.y;

		double rMouseX = isReverseX ? 1 - mouseX : mouseX;
		double rMouseZ = isReverseZ ? 1 - mouseZ : mouseZ;
		boolean isX, isZ;

		switch (facing) {
			case NORTH:
			default:
				isX = rMouseX <= x && rMouseX >= (x - width);
				isZ = mouseZ >= (z - height) && mouseZ <= z;

				if (isX && isZ)
					return true;
				break;
			case EAST:
				isX = mouseX >= x && mouseX <= (x + height);
				isZ = rMouseZ <= z && rMouseZ >= (z - width);

				if (isX && isZ)
					return true;
				break;
			case SOUTH:
				isX = rMouseX >= x && rMouseX <= (x + width);
				isZ = mouseZ >= z && mouseZ <= (z + height);

				if (isX && isZ)
					return true;
				break;
			case WEST:
				isX = mouseX <= x && mouseX >= (x - height);
				isZ = rMouseZ >= z && rMouseZ <= (z + width);

				if (isX && isZ)
					return true;
		}

		return false;
	}

	public CoordPanelButtons getButton(double mouseX, double mouseY, double mouseZ, Direction facing, AttachFace face) {
		Iterator buttonsIterator = buttons.iterator();

		while (buttonsIterator.hasNext()) {
			CoordPanelButtons button = (CoordPanelButtons) buttonsIterator.next();

			if (button.values.containsKey(facing)) {
				Vector2f vec = button.values.get(facing);
				if (vec == null) continue;

				switch (face) {
					case CEILING:
						if (checkButton(button, vec, facing, mouseX, mouseZ, true, true))
							return button;
						break;

					case WALL:
						switch (facing) {
							case EAST:
							default:
								mouseX = 1 - mouseY;
								break;
							case WEST:
								mouseX = mouseY;
								break;
							case NORTH:
								mouseZ = mouseY;
								break;
							case SOUTH:
								mouseZ = 1 - mouseY;
								break;
						}

					case FLOOR:
					default:
						if (checkButton(button, vec, facing, mouseX, mouseZ, false, false))
							return button;
						break;
				}
			}
		}

		return CoordPanelButtons.EMPTY;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACE);
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
		return IBetterPanel.super.getStateForPlacement(context, defaultBlockState());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getShape(state, worldIn, pos, context);
	}

	@Override
	@Nonnull
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getCollisionShape(state, worldIn, pos, context);
	}

	public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) {
		String ss = "";
		double mouseX = hitVec.x() - (double) pos.getX();
		double mouseZ = hitVec.z() - (double) pos.getZ();
		double mouseY = hitVec.y() - (double) pos.getY();
		return this.getButton(mouseX, mouseY, mouseZ, state.getValue(CoordPanelBlock.FACING), state.getValue(FACE)).displayName;
	}

	public enum CoordPanelButtons {
		SUB_X("Subtract X", 3.0F, 2.0F, 1.0F, 7.0F),
		SUB_Y("Subtract Y", 3.0F, 2.0F, 1.0F, 10.0F),
		SUB_Z("Subtract Z", 3.0F, 2.0F, 1.0F, 13.0F),
		ADD_X("Add X", 3.0F, 2.0F, 12.0F, 7.0F),
		ADD_Y("Add Y", 3.0F, 2.0F, 12.0F, 10.0F),
		ADD_Z("Add Z", 3.0F, 2.0F, 12.0F, 13.0F),
		INCREMENT("Change Increment", 5.0F, 5.0F, 7.0F, 1.0F),
		ROTATE("Change Rotation", 5.0F, 5.0F, 1.0F, 1.0F),
		AUTO_CALCULATE_Y("Toggle Height Calculator", 5.0F, 5.0F, 13.0F, 2.0F),
		EMPTY(null, 0.0F, 0.0F, 0.0F, 0.0F);

		final Map<Direction, Vector2f> values = new HashMap();
		final float width;
		final float height;
		StringTextComponent displayName;

		public CoordPanelBlock.CoordPanelButtons get() {
			return CoordPanelBlock.CoordPanelButtons.valueOf(this.name());
		}

		CoordPanelButtons(String s, float w, float h, float x1, float z1) {
			float f = 0.0625F;
			this.width = w * f;
			this.height = h * f;
			float x2 = 16.0F - x1;
			float z2 = 16.0F - z1;

			this.values.put(Direction.NORTH, new Vector2f(x2 * f, z2 * f));
			this.values.put(Direction.EAST, new Vector2f(z1 * f, x2 * f));
			this.values.put(Direction.SOUTH, new Vector2f(x1 * f, z1 * f));
			this.values.put(Direction.WEST, new Vector2f(z2 * f, x1 * f));

			buttons.add(this);
			if (s != null) {
				this.displayName = new StringTextComponent(s);
			}

		}

	}
}
