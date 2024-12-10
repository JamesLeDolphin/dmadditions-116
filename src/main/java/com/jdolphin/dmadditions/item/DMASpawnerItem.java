package com.jdolphin.dmadditions.item;

import com.swdteam.common.entity.IEntityVariant;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMDalekRegistry;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DMASpawnerItem<T extends Entity> extends Item {

	private final RegistryObject<EntityType<T>> entityType;
	private List<String> variants;

	public DMASpawnerItem(final RegistryObject<EntityType<T>> entityType, final List<String> keys, Item.Properties properties) {
		super(properties);
		this.entityType = entityType;
		this.variants = keys;
		DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior() {
			public ItemStack execute(IBlockSource dispenser, ItemStack spawnerStack) {
				Direction direction = dispenser.getBlockState().getValue(DispenserBlock.FACING);
				if (entityType.isPresent()) {
					Entity e = entityType.get().spawn(dispenser.getLevel(), spawnerStack, null, dispenser.getPos().relative(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
					if (e instanceof DalekEntity) {
						((DalekEntity) e).setID(keys.get(e.level.random.nextInt(keys.size())));
						System.out.println("askdhalkjsdhaljshds");
					}
				}

				spawnerStack.shrink(1);
				return spawnerStack;
			}
		});
	}

	public DMASpawnerItem(RegistryObject<EntityType<T>> entityType, String key, Item.Properties properties) {
		this(entityType, Arrays.asList(key), properties);
	}

	public DMASpawnerItem(RegistryObject<EntityType<T>> entityType, Item.Properties properties) {
		this(entityType, Arrays.asList(), properties);
	}

	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		if (!world.isClientSide) {
			ItemStack itemstack = context.getItemInHand();
			BlockPos blockpos = context.getClickedPos();
			Direction direction = context.getClickedFace();
			BlockState blockstate = world.getBlockState(blockpos);
			Block block = blockstate.getBlock();
			if (block == Blocks.SPAWNER) {
				TileEntity tileentity = world.getBlockEntity(blockpos);
				if (tileentity instanceof MobSpawnerTileEntity) {
					if (entityType.isPresent()) {
						AbstractSpawner abstractspawner = ((MobSpawnerTileEntity) tileentity).getSpawner();
						abstractspawner.setEntityId(this.entityType.get());
						tileentity.setChanged();
						world.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
						itemstack.shrink(1);
						return ActionResultType.SUCCESS;
					}
				}
			}

			BlockPos blockpos1;
			if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
				blockpos1 = blockpos;
			} else {
				blockpos1 = blockpos.relative(direction);
			}
			if (entityType.isPresent()) {
				Entity entity = this.entityType.get().spawn((ServerWorld) world, itemstack, context.getPlayer(), blockpos1,
					SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
				itemstack.shrink(1);
				if (this.variants != null && entity instanceof IEntityVariant) {
					String variant = this.variants.get((new Random()).nextInt(this.variants.size()));
					((IEntityVariant) entity).setID(variant);
				}
			}
		}
		return ActionResultType.SUCCESS;
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		BlockRayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
		if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
			return ActionResult.pass(itemstack);
		}
		if (!worldIn.isClientSide) {
			BlockPos blockpos = raytraceresult.getBlockPos();
			if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
				return ActionResult.pass(itemstack);
			} else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, raytraceresult.getDirection(), itemstack)) {
				Entity entity;
				if (entityType.isPresent()) {
					if ((entity = this.entityType.get().spawn((ServerWorld)worldIn, itemstack, playerIn, blockpos, SpawnReason.SPAWN_EGG, false, false)) == null) {
						return ActionResult.pass(itemstack);
					} else {
						if (!playerIn.abilities.instabuild) {
							itemstack.shrink(1);
						}
					}
					itemstack.shrink(1);
					if (this.variants != null && entity instanceof IEntityVariant) {
						String variant = this.variants.get((new Random()).nextInt(this.variants.size()));
						((IEntityVariant)entity).setID(variant);
					}

					playerIn.awardStat(Stats.ITEM_USED.get(this));
					return ActionResult.success(itemstack);
				}
			} else {
				return ActionResult.fail(itemstack);
			}
		}
		return ActionResult.success(itemstack);
	}

	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (this.entityType != null && worldIn != null && this.entityType.getId().toString().equals("dalekmod:dalek")) {
			DMDalekRegistry.getDaleks().get(this.variants.get(0)).getTooltips(tooltip);
		}

	}
}