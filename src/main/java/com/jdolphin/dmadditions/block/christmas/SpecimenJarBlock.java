package com.jdolphin.dmadditions.block.christmas;

import com.jdolphin.dmadditions.DmAdditions;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.jdolphin.dmadditions.tileentity.SpecimenJarTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpecimenJarBlock extends FallingBlock {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final IntegerProperty SPECIMEN = IntegerProperty.create("specimen", 0, 3);
	public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);
	public SpecimenJarBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(SPECIMEN);
		super.createBlockStateDefinition(builder);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState()
			.setValue(FACING, context.getHorizontalDirection().getOpposite())
			.setValue(SPECIMEN, 0);
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
		return SHAPE;
	}

	@Override
	public void onLand(World world, BlockPos pos, BlockState state, BlockState state1, FallingBlockEntity fallingBlock) {
		world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		if(!world.isClientSide){
			world.getServer().getLevel(world.dimension()).sendParticles(ParticleTypes.FIREWORK, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 20, 0.1,0.1,0.1,0.1);
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return DMABlockEntities.TILE_SPECIMEN_JAR.get().create();
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if(world.isClientSide || hand != Hand.MAIN_HAND) return ActionResultType.FAIL;

		TileEntity te = world.getBlockEntity(blockPos);
		if(te instanceof SpecimenJarTileEntity){
			SpecimenJarTileEntity jar = (SpecimenJarTileEntity) te;
			ItemStack held = player.getItemInHand(hand);
			// take
			if(jar.hasSpecimen()){
				if(held.isEmpty()) player.setItemInHand(hand, jar.getSpecimen());
				else if(held.sameItem(jar.getSpecimen()) && ItemStack.tagMatches(held, jar.getSpecimen())) held.grow(1);
				else return ActionResultType.PASS;

				jar.setSpecimen(ItemStack.EMPTY);
				world.setBlockAndUpdate(blockPos, state.setValue(SPECIMEN, 0));
				return ActionResultType.CONSUME;
			}
			// put
			else if(jar.acceptSpecimen(held.getItem())){
				jar.setSpecimen(player.getItemInHand(hand));
				held.shrink(1);
				if(held.getCount() == 0) player.setItemInHand(hand, ItemStack.EMPTY);
				world.setBlockAndUpdate(blockPos, state.setValue(SPECIMEN, jar.getSpecimenIndex()+1));
				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.PASS;
	}
}
