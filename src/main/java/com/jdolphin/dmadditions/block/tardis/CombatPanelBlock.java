package com.jdolphin.dmadditions.block.tardis;

import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.common.tileentity.tardis.FlightPanelTileEntity;
import com.swdteam.common.tileentity.tardis.PanelHealthUpgrade;
import com.swdteam.common.tileentity.tardis.TardisPanelTileEntity;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.ChatUtil.MessageType;
import com.swdteam.util.TeleportUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class CombatPanelBlock extends AbstractRotateableWaterLoggableBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);

	public CombatPanelBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Nullable
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new FlightPanelTileEntity();
	}

	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		if (handIn == Hand.MAIN_HAND && !worldIn.isClientSide && worldIn.dimension().equals(DMDimensions.TARDIS)) {
			TardisData data = DMTardis.getTardisFromInteriorPos(pos);
			if (data != null) {
				// Button 1: Flight Mode
				if (p_225533_6_.getBlockPos().getX() == 0) {
					if (!data.isInFlight() && data.getCurrentLocation() != null && !DMFlightMode.isInFlight(data.getGlobalID())) {
						ServerWorld world = worldIn.getServer().getLevel(data.getCurrentLocation().dimensionWorldKey());
						TileEntity te = world.getBlockEntity(data.getCurrentLocation().getBlockPosition());
						if (te instanceof TardisTileEntity) {
							((TardisTileEntity) te).removeTARDIS();
						}

						DMFlightMode.addFlight(player, new DMFlightMode.FlightModeData(data.getGlobalID(), player.getX(), player.getY(), player.getZ()));
						TeleportUtil.teleportPlayer(player, data.getCurrentLocation().dimensionWorldKey(), new Vector3d(data.getCurrentLocation().getPosition().x(), data.getCurrentLocation().getPosition().y(), data.getCurrentLocation().getPosition().z()), 0.0F);
						player.abilities.mayBuild = false;
						if (data.getFuel() > 0.0) {
							player.abilities.flying = true;
							player.abilities.mayfly = true;
						} else {
							player.abilities.flying = false;
							player.abilities.mayfly = false;
						}

						player.onUpdateAbilities();
					} else if (data.isInFlight()) {
						ChatUtil.sendError(player, "TARDIS is currently in flight", MessageType.CHAT);
					}
				} else if (p_225533_6_.getBlockPos().getX() == 4) {
					// Custom logic for Button 2
					// TO DO: Implement custom logic here
				} else if (p_225533_6_.getBlockPos().getX() == 8) {
					// Custom logic for Button 3
					// TO DO: Implement custom logic here
				}
			}
		}

		return ActionResultType.CONSUME;
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		return canSupportCenter(reader, pos.below(), Direction.UP);
	}

	public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
		if (stack.hasTag()) {
			CompoundNBT nbt = stack.getOrCreateTag();
			TileEntity te = world.getBlockEntity(pos);
			if (te instanceof TardisPanelTileEntity) {
				TardisPanelTileEntity tardisPanelTileEntity = (TardisPanelTileEntity) te;
				if (nbt.contains("PanelDamage")) {
					tardisPanelTileEntity.setDamage(nbt.getInt("PanelDamage"));
				}

				if (nbt.contains("PanelDurability")) {
					tardisPanelTileEntity.setDurability(nbt.getInt("PanelDurability"));
				}

				if (nbt.contains("PanelName")) {
					tardisPanelTileEntity.setName(nbt.getString("PanelName"));
				}

				if (nbt.contains("PanelCircuit")) {
					String id = nbt.getString("PanelCircuit");
					PanelHealthUpgrade[] var10 = PanelHealthUpgrade.values();
					int var11 = var10.length;

					for (int var12 = 0; var12 < var11; ++var12) {
						PanelHealthUpgrade value = var10[var12];
						if (value.id().equalsIgnoreCase(id)) {
							tardisPanelTileEntity.setHealthUpgrade(value);
							break;
						}
					}
				}
			}
		}
	}
}