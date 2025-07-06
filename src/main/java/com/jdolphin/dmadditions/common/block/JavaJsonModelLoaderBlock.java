package com.jdolphin.dmadditions.common.block;

import com.jdolphin.dmadditions.common.init.DMAPackets;
import com.jdolphin.dmadditions.common.network.CBOpenGUIPacket;
import com.swdteam.common.block.TileEntityBaseBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

public class JavaJsonModelLoaderBlock extends TileEntityBaseBlock {

	public JavaJsonModelLoaderBlock(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
	}

	@Override
	@SuppressWarnings("deprecation")
	@ParametersAreNonnullByDefault
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		if (!world.isClientSide && player instanceof ServerPlayerEntity) {
			CBOpenGUIPacket packet = new CBOpenGUIPacket(pos, 2);
			DMAPackets.sendTo(((ServerPlayerEntity) player), packet);
			return ActionResultType.SUCCESS;
		}
		return super.use(state, world, pos, player, hand, result);
	}
}
