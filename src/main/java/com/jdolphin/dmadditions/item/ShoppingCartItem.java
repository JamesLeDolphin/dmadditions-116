package com.jdolphin.dmadditions.item;

import java.util.List;
import java.util.Objects;

import com.jdolphin.dmadditions.entity.ShoppingCartEntity;
import com.jdolphin.dmadditions.init.DMAEntities;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShoppingCartItem extends Item {

	public static final String HAS_ENGINE = "HasEngine";
	public static final String FLYABLE = "Flyable";

	public ShoppingCartItem(Properties properties) {
		super(properties);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list,
			ITooltipFlag flag) {
		CompoundNBT tag = stack.getTag();
		if (tag == null)
			return;

		if (tag.contains(HAS_ENGINE)) {
			list.add(new TranslationTextComponent(String.format("tooltip.%s.%s", getDescriptionId(),
				tag.getBoolean(HAS_ENGINE) ? "has_engine" : "no_engine")));
		}

		if (tag.getBoolean(FLYABLE)) {
			list.add(new TranslationTextComponent(String.format("tooltip.%s.flyable", getDescriptionId())));
		}
		super.appendHoverText(stack, world, list, flag);
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		if (!(world instanceof ServerWorld)) {
			return ActionResultType.SUCCESS;
		}

		ItemStack itemstack = context.getItemInHand();
		BlockPos blockpos = context.getClickedPos();
		Direction direction = context.getClickedFace();
		BlockState blockstate = world.getBlockState(blockpos);

		BlockPos blockpos1;
		if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
			blockpos1 = blockpos;
		} else {
			blockpos1 = blockpos.relative(direction);
		}

		EntityType<?> entitytype = DMAEntities.SHOPPING_CART.get();
		PlayerEntity player = context.getPlayer();
		Entity entity = entitytype.spawn((ServerWorld) world, itemstack, player, blockpos1, SpawnReason.SPAWN_EGG,
				true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);

		if (entity != null) {
			if (!player.abilities.instabuild)
				itemstack.shrink(1);

			if (!(entity instanceof ShoppingCartEntity))
				return ActionResultType.SUCCESS;

			ShoppingCartEntity shoppingCart = (ShoppingCartEntity) entity;

			CompoundNBT tag = itemstack.getTag();
			if (tag.contains(HAS_ENGINE)) {
				shoppingCart.setHasEngine(tag.getBoolean(HAS_ENGINE));
			}
			if (tag.contains(FLYABLE)) {
				shoppingCart.setFlyable(tag.getBoolean(FLYABLE));
			}
		}

		return super.useOn(context);
	}

}
