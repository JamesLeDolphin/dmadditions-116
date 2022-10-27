package com.jdolphin.dmadditions.block;

import com.swdteam.common.block.IBlockTooltip;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tileentity.TardisTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class DoorOpenPanel extends HorizontalBlock implements IBlockTooltip {
	private boolean doorOpenLeft;

	public DoorOpenPanel(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
								BlockRayTraceResult rayTraceResult, CompoundNBT compound, TardisTileEntity tardis) {
		if (worldIn.isClientSide)
			return ActionResultType.PASS;

		if (handIn == Hand.MAIN_HAND) {
			if (ServerLifecycleHooks.getCurrentServer() == null)
				return ActionResultType.CONSUME;
			if (tardis.doorOpenLeft) {
				tardis.closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.INTERIOR);
			}
			if (tardis.doorOpenRight) {
				tardis.closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.INTERIOR);
			}
		}


		return ActionResultType.SUCCESS;
	}

	@Override
	public ITextComponent getName(BlockState blockState, BlockPos blockPos, Vector3d vector3d, PlayerEntity playerEntity) {
		return null;
	}
}
