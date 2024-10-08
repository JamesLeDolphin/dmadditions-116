package com.jdolphin.dmadditions.block.christmas;

import com.jdolphin.dmadditions.entity.KantrofarriEntity;
import com.jdolphin.dmadditions.init.DMABlockEntities;
import com.jdolphin.dmadditions.init.DMAEntities;
import com.jdolphin.dmadditions.init.DMAItems;
import com.jdolphin.dmadditions.tileentity.SpecimenJarTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
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
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class SpecimenJarBlock extends FallingBlock {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty HAS_SPECIMEN = BooleanProperty.create("has_specimen")
;	public static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 16, 13);
	public SpecimenJarBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}


	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING).add(HAS_SPECIMEN);
		super.createBlockStateDefinition(builder);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState()
			.setValue(FACING, Direction.NORTH)
			.setValue(HAS_SPECIMEN, false);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
		return SHAPE;
	}

	@Override
	public void onLand(World world, BlockPos pos, BlockState state, BlockState state1, FallingBlockEntity fallingBlock) {
		if (!world.isClientSide) {
			CompoundNBT tag = fallingBlock.getPersistentData();
			if (tag.contains("Item")) {
				ItemStack stack = ItemStack.of(tag.getCompound("Item"));
				if (!stack.isEmpty()) {
					TileEntity te = world.getBlockEntity(pos);
					if (te instanceof SpecimenJarTileEntity) {
						if (stack.getItem().equals(DMAItems.KANTROFARRI.get()) && world.random.nextInt(10) == 0) {
							world.addFreshEntity(new KantrofarriEntity(world));
							world.destroyBlock(pos, false);
						} else ((SpecimenJarTileEntity) te).setSpecimen(stack);
					}
				}
			}
			world.destroyBlock(pos, false);
		}
	}

	protected void falling(FallingBlockEntity entity) {
		entity.setHurtsEntities(true);
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState state1, boolean harvest) {
		if(state1.getBlock() != this && world.getBlockState(pos.below()).getBlock() != Blocks.AIR){
			TileEntity te = world.getBlockEntity(pos);
			if (te instanceof SpecimenJarTileEntity) dropSpecimen(((SpecimenJarTileEntity)te).getSpecimen(), pos, world);
		}
		super.onRemove(state, world, pos, state1, harvest);
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
		if (world.isClientSide || hand != Hand.MAIN_HAND) return ActionResultType.FAIL;

		TileEntity te = world.getBlockEntity(blockPos);
		if (te instanceof SpecimenJarTileEntity) {
			SpecimenJarTileEntity jar = (SpecimenJarTileEntity) te;
			ItemStack held = player.getItemInHand(hand);
			// take
			if (jar.hasSpecimen()) {
				if (!held.isEmpty()) return ActionResultType.PASS;
				return giveSpecimen(jar, held, player, blockPos, world, state);
			}
			// put
			else {
				if (jar.acceptSpecimen(held.getItem())) {
					jar.setSpecimen(held);
					if (!player.isCreative()) held.shrink(1);
					if (held.getCount() == 0) player.setItemInHand(hand, ItemStack.EMPTY);
					world.setBlockAndUpdate(blockPos, state.setValue(HAS_SPECIMEN, true));
					return ActionResultType.SUCCESS;
				}
			}
		}

		return ActionResultType.PASS;
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.isEmptyBlock(pos.below()) || isFree(world.getBlockState(pos.below())) && pos.getY() >= 0) {
			FallingBlockEntity fallingblockentity = new FallingBlockEntity(world,
				(double)pos.getX() + 0.5D,
				pos.getY(), (double)pos.getZ() + 0.5D,
				world.getBlockState(pos));

			TileEntity te = world.getBlockEntity(pos);
			if (te instanceof SpecimenJarTileEntity) {
				SpecimenJarTileEntity jar = (SpecimenJarTileEntity) te;
				if (!jar.getSpecimen().isEmpty()) {
					CompoundNBT tag = new CompoundNBT();
					fallingblockentity.getPersistentData().put("Item",
						jar.getSpecimen().save(tag));
				}
			}

			this.falling(fallingblockentity);
			world.addFreshEntity(fallingblockentity);
		}
	}

	private void dropSpecimen(ItemStack specimen, BlockPos pos, World world) {
		if (specimen != null && !specimen.isEmpty()) {
			if (specimen.getItem().equals(DMAItems.KANTROFARRI_SPAWNER.get())) {
				KantrofarriEntity kantrofarri = new KantrofarriEntity(world);
				kantrofarri.moveTo(pos, 0, 0);
				world.addFreshEntity(kantrofarri);
			} else {
				ItemEntity entity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), specimen);
				world.addFreshEntity(entity);
			}
		}
	}

	private ActionResultType giveSpecimen(SpecimenJarTileEntity jar, ItemStack held, PlayerEntity player, BlockPos pos, World world, BlockState state) {
		if (jar != null) {
			ItemStack specimen = jar.getSpecimen();
			if (specimen.getItem().equals(DMAItems.KANTROFARRI_SPAWNER.get())) {
				KantrofarriEntity kantrofarri = new KantrofarriEntity(world);
				kantrofarri.moveTo(player.position());
				world.addFreshEntity(kantrofarri);
				jar.emptySpecimen();
				world.setBlockAndUpdate(pos, state.setValue(HAS_SPECIMEN, false));
				return ActionResultType.SUCCESS;
			} else {
				boolean bl = player.isCreative();
				if (held.isEmpty()) {
					if (!bl) player.setItemInHand(Hand.MAIN_HAND, specimen);
					jar.emptySpecimen();
					world.setBlockAndUpdate(pos, state.setValue(HAS_SPECIMEN, false));
					return ActionResultType.SUCCESS;
				} else if (held.sameItem(specimen) && ItemStack.tagMatches(held, specimen)) {
					if (!bl) held.grow(1);
					jar.emptySpecimen();
					world.setBlockAndUpdate(pos, state.setValue(HAS_SPECIMEN, false));
					return ActionResultType.SUCCESS;
				} else return ActionResultType.PASS;
			}
		}
		return ActionResultType.FAIL;
	}
}
