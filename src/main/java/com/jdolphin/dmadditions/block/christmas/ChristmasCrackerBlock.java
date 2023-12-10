package com.jdolphin.dmadditions.block.christmas;

import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.item.ChristmasHatItem;
import com.jdolphin.dmadditions.item.JokeItem;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
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
			openCracker(world, new Vector3d(pos.getX()+.5,pos.getY(),pos.getZ()+.5));
			world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}

	// Spawns contents at the location x,y,z with particle and sound
	public static void openCracker(World world, Vector3d position){
		if(world.isClientSide) return;
		for (ItemStack item : generateContents(world)) {
			ItemEntity surprise = new ItemEntity(world, position.x, position.y, position.z, item);
			world.addFreshEntity(surprise);
		}
		world.getServer().getLevel(world.dimension()).sendParticles(ParticleTypes.FIREWORK, position.x,position.y,position.z,30,0.1,0.1,0.1,0.1);
		BlockPos soundPoint = new BlockPos(Math.floor(position.x),Math.floor(position.y),Math.floor(position.z));
		world.playSound(null, soundPoint, SoundEvents.FIREWORK_ROCKET_BLAST, SoundCategory.NEUTRAL, 2,1);
	}

	// Generates the items a cracker will drop when it is opened
	public static List<ItemStack> generateContents(World world){
		List<ItemStack> contents = new ArrayList<ItemStack>();

		// christmas_hat
		ItemStack hat = DMAItems.CHRISTMAS_HAT.get().getDefaultInstance();
		int color = ChristmasHatItem.colors[world.random.nextInt(ChristmasHatItem.colors.length)];
		hat.getOrCreateTagElement("display").putInt("color", color);
		contents.add(hat);

		// joke
		ItemStack joke = DMAItems.JOKE.get().getDefaultInstance();
		JokeItem.setJoke(joke, JokeItem.randomJoke());
		contents.add(joke);

		return contents;
	}

}
