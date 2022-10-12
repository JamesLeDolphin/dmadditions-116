
package com.jdolphin.dmadditions.mixin;

import com.swdteam.common.block.AbstractLeverBlock;
import com.swdteam.common.block.tardis.FastReturnLeverBlock;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FastReturnLeverBlock.class)
public abstract class FastReturnMixin<FACE> extends AbstractLeverBlock {

	private static final VoxelShape NORTH_AABB = Block.box(5.0D, 4.0D, 10.0D, 11.0D, 12.0D, 16.0D);
	private static final VoxelShape SOUTH_AABB = Block.box(5.0D, 4.0D, 0.0D, 11.0D, 12.0D, 6.0D);
	private static final VoxelShape WEST_AABB = Block.box(10.0D, 4.0D, 5.0D, 16.0D, 12.0D, 11.0D);
	private static final VoxelShape EAST_AABB = Block.box(0.0D, 4.0D, 5.0D, 6.0D, 12.0D, 11.0D);
	private static final VoxelShape UP_AABB_Z = Block.box(5.0D, 0.0D, 4.0D, 11.0D, 6.0D, 12.0D);
	private static final VoxelShape UP_AABB_X = Block.box(4.0D, 0.0D, 5.0D, 12.0D, 6.0D, 11.0D);
	private static final VoxelShape DOWN_AABB_Z = Block.box(5.0D, 10.0D, 4.0D, 11.0D, 16.0D, 12.0D);
	private static final VoxelShape DOWN_AABB_X = Block.box(4.0D, 10.0D, 5.0D, 12.0D, 16.0D, 11.0D);
	private static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

	public FastReturnMixin(Properties properties) {
		super(properties);
	}

	@Inject(at = @At("HEAD"), method = "use(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/math/BlockRayTraceResult;)Lnet/minecraft/util/ActionResultType;"
	)
	private void onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_, CallbackInfoReturnable<ActionResultType> cir) {
		if (worldIn.isClientSide()) ChatUtil.sendMessageToPlayer(player, "a", ChatUtil.MessageType.CHAT);
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> blockBlockStateBuilder, CallbackInfo cir) {
		System.out.println("Creating block state definition!!");
		cir.cancel();
		blockBlockStateBuilder.add(FACE, FACING);
	}

	@Inject(at = @At("HEAD"), method = "getShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true)
	public void getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, ISelectionContext selectionContext, CallbackInfoReturnable<VoxelShape> cir) {
		cir.cancel();
		switch ((AttachFace) blockState.getValue(FACE)) {
			case FLOOR:
				switch (blockState.getValue(FACING).getAxis()) {
					case X:
						cir.setReturnValue(UP_AABB_X);
						return;
					case Z:
					default:
						cir.setReturnValue(UP_AABB_Z);
						return;
				}
			case WALL:
				switch ((Direction) blockState.getValue(FACING)) {
					case EAST:
						cir.setReturnValue(EAST_AABB);
						return;
					case WEST:
						cir.setReturnValue(WEST_AABB);
						return;
					case SOUTH:
						cir.setReturnValue(SOUTH_AABB);
						return;
					case NORTH:
					default:
						cir.setReturnValue(NORTH_AABB);
						return;
				}
			case CEILING:
			default:
				switch (blockState.getValue(FACING).getAxis()) {
					case X:
						cir.setReturnValue(DOWN_AABB_X);
						return;
					case Z:
					default:
						cir.setReturnValue(DOWN_AABB_Z);
				}
		}
	}
}
