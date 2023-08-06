package com.jdolphin.dmadditions.block;

import com.jdolphin.dmadditions.DmAdditions;
import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.block.tardis.DimensionSelectorPanelBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tardis.data.TardisLocationRegistry;
import com.swdteam.common.tileentity.tardis.DimensionSelectorTileEntity;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.tardis.mod.flight.DimensionFlightEvent;
import net.tardis.mod.helper.TardisHelper;
import net.tardis.mod.helper.WorldHelper;
import net.tardis.mod.network.Network;
import net.tardis.mod.network.packets.ConsoleUpdateMessage;
import net.tardis.mod.network.packets.console.DataTypes;
import net.tardis.mod.network.packets.console.DimensionData;
import net.tardis.mod.world.dimensions.TDimensions;

import java.util.*;
import java.util.function.Supplier;

public class BetterDimensionSelector extends DimensionSelectorPanelBlock implements IBetterPanel {
	public static List<DimensionPanelButtons> buttons = new ArrayList<>();
	private ArrayList<ServerWorld> dimList = new ArrayList();
	private int index = 0;

	public BetterDimensionSelector(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
		super(tileEntitySupplier, properties);
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
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return IBetterPanel.super.getCollisionShape(state, worldIn, pos, context);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if ((!worldIn.isClientSide) && handIn == Hand.MAIN_HAND && worldIn.getBlockEntity(pos) instanceof DimensionSelectorTileEntity && state.getValue(BUTTON_PRESSED) == 0) {
			Vector3d hitVec = hit.getLocation();
			float mouseX = (float) ((int) (100.0F * (float) (hitVec.x() - (double) pos.getX()))) / 100.0F;
			float mouseZ = (float) ((int) (100.0F * (float) (hitVec.z() - (double) pos.getZ()))) / 100.0F;
			DimensionSelectorTileEntity tet = (DimensionSelectorTileEntity) worldIn.getBlockEntity(pos);
			DimensionPanelButtons buttonClicked = this.getButton(mouseX, mouseZ, state.getValue(AbstractRotateableWaterLoggableBlock.FACING), state.getValue(FACE));

			switch (buttonClicked) {
				case BTN_LEFT:
					worldIn.setBlockAndUpdate(pos, state.setValue(BUTTON_PRESSED, 1));
					break;
				case BTN_SELECT:
					worldIn.setBlockAndUpdate(pos, state.setValue(BUTTON_PRESSED, 2));
					break;
				case BTN_RIGHT:
					worldIn.setBlockAndUpdate(pos, state.setValue(BUTTON_PRESSED, 3));
			}

			if (buttonClicked != DimensionPanelButtons.EMPTY) {
				worldIn.getBlockTicks().scheduleTick(pos, this, 10);
				worldIn.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			if (worldIn.dimension().equals(DMDimensions.TARDIS)) {
				TardisData data = DMTardis.getTardisFromInteriorPos(pos);
				if (data != null) {
					switch (buttonClicked) {
						case BTN_LEFT:
							if (tet.getIndex() - 1 < 0) {
								tet.setIndex(TardisLocationRegistry.getLocationRegistry().size() - 1);
							} else {
								tet.setIndex(tet.getIndex() - 1);
							}
							break;
						case BTN_SELECT:
							TardisFlightData flightData = TardisFlightPool.getFlightData(data);
							if (flightData != null) {
								TardisLocationRegistry.TardisLocation location = TardisLocationRegistry.getLocationRegistryAsList().get(tet.getIndex());
								if (location == null) {
									location = TardisLocationRegistry.getLocationRegistryAsList().get(0);
								} else {
									flightData.setDimensionKey(location.getDimension());
									flightData.setWaypoints(null);
									TardisFlightPool.sendUpdates(data.getGlobalID());
									ChatUtil.sendCompletedMsg(player, "TARDIS set for: " + location.getDimensionName().getString(), ChatUtil.MessageType.STATUS_BAR);
									worldIn.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_DING.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
								}
							}
							break;
						case BTN_RIGHT:
							tet.setIndex((tet.getIndex() + 1) % TardisLocationRegistry.getLocationRegistry().size());
					}

					if (buttonClicked == DimensionPanelButtons.BTN_LEFT || buttonClicked == DimensionPanelButtons.BTN_RIGHT) {
						TardisLocationRegistry.TardisLocation loc = TardisLocationRegistry.getLocationRegistryAsList().get(tet.getIndex());
						if (loc != null) {
							tet.setTexturePath(loc.getDimensionImage());
							ChatUtil.sendCompletedMsg(player, loc.getDimensionName().getString(), ChatUtil.MessageType.STATUS_BAR);
							tet.sendUpdates();
						}
					}
				}
			}
			if (DmAdditions.hasNTM()) {
				if (WorldHelper.areDimensionTypesSame(worldIn, TDimensions.DimensionTypes.TARDIS_TYPE)) {
					TardisHelper.getConsole(worldIn.getServer(), worldIn).ifPresent(tile -> {
						switch (buttonClicked) {
							case BTN_LEFT:
								if (tile.getFlightEvent() == null) {
									this.createDimListIfEmpty();
									if (!this.dimList.isEmpty()) {
										this.modIndex(-1);
										ServerWorld type = (ServerWorld) this.dimList.get(this.index);
										tile.setDestination(type.dimension(), tile.getDestinationPosition());
										player.displayClientMessage((new TranslationTextComponent("message.tardis.control.dimchange"))
											.append((new StringTextComponent(WorldHelper.formatDimName(type.dimension())))
												.withStyle(TextFormatting.LIGHT_PURPLE)), true);
										if (tile != null) {
											Network.sendToTrackingTE(new ConsoleUpdateMessage(DataTypes.DIMENSION_LIST, new DimensionData(this.dimList.size(), this.index)), tile);
										}
									} else {
										this.index = 0;
									}
								} else if (tile.getFlightEvent() instanceof DimensionFlightEvent) {
									tile.getFlightEvent().onComplete(tile);
								}
								break;
							case BTN_RIGHT:
								if (tile.getFlightEvent() == null) {
									this.createDimListIfEmpty();
									if (!tile.getLevel().isClientSide() && tile.getLandTime() <= 0) {
										if (!this.dimList.isEmpty()) {
											this.modIndex(1);
											ServerWorld type = (ServerWorld) this.dimList.get(this.index);
											tile.setDestination(type.dimension(), tile.getDestinationPosition());
											player.displayClientMessage((new TranslationTextComponent("message.tardis.control.dimchange"))
												.append((new StringTextComponent(WorldHelper.formatDimName(type.dimension())))
													.withStyle(TextFormatting.LIGHT_PURPLE)), true);
											if (tile != null) {
												Network.sendToTrackingTE(new ConsoleUpdateMessage(DataTypes.DIMENSION_LIST, new DimensionData(this.dimList.size(), this.index)), tile);
											}
										} else {
											this.index = 0;
										}
									} else if (tile.getFlightEvent() instanceof DimensionFlightEvent) {
										DimensionFlightEvent event = (DimensionFlightEvent) tile.getFlightEvent();
										tile.getFlightEvent().onComplete(tile);
									}
									}
								}
						});
					}
				}
			}

		return ActionResultType.CONSUME;
	}
	private void createDimListIfEmpty(){
		if(this.dimList.isEmpty()){
			ServerLifecycleHooks.getCurrentServer().getAllLevels().forEach(world -> {
				if(WorldHelper.canTravelToDimension(world))
					dimList.add(world);
			});
		}
	}

	private void modIndex(int i) {
		if (this.index + i >= this.dimList.size()) {
			this.index = 0;
		} else if (this.index + i < 0) {
			this.index = this.dimList.size() - 1;
		} else {
			this.index += i;
		}
	}

	public DimensionPanelButtons getButton(double mouseX, double mouseZ, Direction facing, AttachFace face) {
		Iterator<DimensionPanelButtons> buttonsIterator = buttons.iterator();

		while (buttonsIterator.hasNext()) {
			DimensionPanelButtons button = buttonsIterator.next();
			if (button.values.containsKey(facing)) {
				Vector2f vec = button.values.get(facing);
				float width = button.width;
				float height = button.height;
				float x = vec.x;
				float z = vec.y;
				switch (facing) {
					case NORTH:
					default:
						if (mouseX <= (double) x && mouseZ <= (double) z && mouseX >= (double) (x - width) && mouseZ >= (double) (z - height)) {
							return button;
						}
						break;
					case EAST:
						if (mouseX >= (double) x && mouseX <= (double) (x + height) && mouseZ <= (double) z && mouseZ >= (double) (z - width)) {
							return button;
						}
						break;
					case SOUTH:
						if (mouseX >= (double) x && mouseZ >= (double) z && mouseX <= (double) (x + width) && mouseZ <= (double) (z + height)) {
							return button;
						}
						break;
					case WEST:
						if (mouseX <= (double) x && mouseX >= (double) (x - height) && mouseZ >= (double) z && mouseZ <= (double) (z + width)) {
							return button;
						}
				}
			}
		}

		return DimensionPanelButtons.EMPTY;
	}

	public enum DimensionPanelButtons {
		BTN_LEFT("Previous Dimension", 4.0F, 3.0F, 1.0F, 1.0F),
		BTN_SELECT("Select Dimension", 4.0F, 3.0F, 6.0F, 1.0F),
		BTN_RIGHT("Next Dimension", 4.0F, 3.0F, 11.0F, 1.0F),
		EMPTY(null, 0.0F, 0.0F, 0.0F, 0.0F);

		Map<Direction, Vector2f> values = new HashMap();
		float width;
		float height;
		public StringTextComponent displayName;

		DimensionPanelButtons(String s, float w, float h, float x1, float z1) {
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

	public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) {
		String ss = "";
		double mouseX = hitVec.x() - (double) pos.getX();
		double mouseZ = hitVec.z() - (double) pos.getZ();
		StringTextComponent text = this.getButton(mouseX, mouseZ, state.getValue(AbstractRotateableWaterLoggableBlock.FACING), state.getValue(FACE)).displayName;
		return text;
	}
}
