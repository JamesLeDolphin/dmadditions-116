package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMABlocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class SpecimenJarBlockItem extends BlockItem {
	public static final String TAG_ITEM = "Specimen";
	public SpecimenJarBlockItem(Properties properties) {
		super(DMABlocks.SPECIMEN_JAR.get(), properties);
	}

	public ActionResultType place(BlockItemUseContext context) {
		return super.place(context);
		//World world = context.getLevel();
		//BlockPos pos = context.getClickedPos();
		//if (!world.isClientSide()) {
		//	TileEntity tile = world.getBlockEntity(pos);
		//	if (tile instanceof SpecimenJarTileEntity) {
		//		SpecimenJarTileEntity jar = (SpecimenJarTileEntity) tile;
		//		ItemStack stack = new ItemStack(this);
		//		CompoundNBT tag = stack.getOrCreateTag();
		//		if (tag.contains(TAG_ITEM)) jar.setSpecimen(ItemStack.of(tag.getCompound(TAG_ITEM)));
		//		return ActionResultType.SUCCESS;
		//	}
		//}
		//return ActionResultType.FAIL;
	}

	@NotNull
	@ParametersAreNonnullByDefault
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
		//World level = player.level;
		//CompoundNBT tag = stack.getOrCreateTag();
		//if (!level.isClientSide()) {
		//	if (!tag.contains(TAG_ITEM) || ItemStack.of(tag.getCompound("Item")).isEmpty()) {
		//		ItemStack pickStack = entity.getPickedResult(null);
		//		if (pickStack != null && !pickStack.isEmpty()) {
		//			CompoundNBT nbt = new CompoundNBT();
		//			CompoundNBT itemStack = pickStack.save(nbt);
		//			System.out.println(itemStack);
		//			tag.put(TAG_ITEM, itemStack);
		//			entity.remove();
		//			return ActionResultType.SUCCESS;
		//		}
		//	}
		//}
		return ActionResultType.PASS;
	}
}
