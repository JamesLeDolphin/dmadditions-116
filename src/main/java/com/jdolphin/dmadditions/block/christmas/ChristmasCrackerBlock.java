package com.jdolphin.dmadditions.block.christmas;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.ChristmasHatItem;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;

public class ChristmasCrackerBlock extends HorizontalBlock {
	public static final VoxelShape SHAPE_NORTH = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 3.0D, 10.0D);
	public static final VoxelShape SHAPE_EAST = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 3.0D, 16.0D);
	public ChristmasCrackerBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos,
			ISelectionContext context) {
		switch (state.getValue(HorizontalBlock.FACING)) {
			case NORTH:
			case SOUTH:
				return SHAPE_NORTH;

			case EAST:
			case WEST:
			default:
				return SHAPE_EAST;
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
		return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite());
	}

	@Override
	public boolean canSurvive(BlockState blockstate, IWorldReader worldReader, BlockPos pos) {
		return worldReader.getBlockState(pos.below()).isFaceSturdy(worldReader, pos, Direction.UP);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(HorizontalBlock.FACING);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		if(hand == Hand.MAIN_HAND && player.getItemInHand(hand).isEmpty() && !world.isClientSide()){
			openCracker(world, pos.getX()+.5,pos.getY(),pos.getZ()+.5);
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}

	// Spawns a hat and explosion at the location x,y,z
	public static void openCracker(World world, double x, double y, double z){
		ItemStack item = DMAItems.CHRISTMAS_HAT.get().getDefaultInstance();
		int color = ChristmasHatItem.colors[world.random.nextInt(ChristmasHatItem.colors.length)];
		item.getOrCreateTagElement("display").putInt("color", color);
		ItemEntity surprise = new ItemEntity(world, x, y, z, item);
		world.addFreshEntity(surprise);
		world.getServer().getLevel(world.dimension()).sendParticles(ParticleTypes.EXPLOSION, x,y,z,1,0.1,0.1,0.1,0);
		BlockPos soundPoint = new BlockPos(Math.floor(x),Math.floor(y),Math.floor(z));
		world.playSound(null, soundPoint, SoundEvents.GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1,1);
	}

}
