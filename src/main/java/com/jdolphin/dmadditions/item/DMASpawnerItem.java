package com.jdolphin.dmadditions.item;

import com.jdolphin.dmadditions.init.DMAEntities;
import com.swdteam.common.entity.IEntityVariant;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMEntities;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
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
import net.minecraftforge.common.ForgeSpawnEggItem;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class DMASpawnerItem<T extends Entity> extends Item {

	private String entityType;
	private List<String> variants;

	public DMASpawnerItem(final String entityType, final List<String> keys, Item.Properties properties) {
		super(properties);
		this.entityType = entityType;
		this.variants = keys;
		DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior() {
			public ItemStack execute(IBlockSource dispenser, ItemStack spawnerStack) {
				Direction direction = (Direction)dispenser.getBlockState().getValue(DispenserBlock.FACING);
				EntityType<?> entitytype = DMAEntities.getEntityTypeFromString(entityType);
				Entity e = entitytype.spawn(dispenser.getLevel(), spawnerStack, (PlayerEntity)null, dispenser.getPos().relative(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
				if (e instanceof DalekEntity) {
					((DalekEntity)e).setID((String)keys.get(e.level.random.nextInt(keys.size())));
					System.out.println("askdhalkjsdhaljshds");
				}

				spawnerStack.shrink(1);
				return spawnerStack;
			}
		});
	}

	public DMASpawnerItem(String entityType, String key, Item.Properties properties) {
		this(entityType, Arrays.asList(key), properties);
	}

	public DMASpawnerItem(String entityType, Item.Properties properties) {
		this(entityType, Arrays.asList(), properties);
	}

	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			ItemStack itemstack = context.getItemInHand();
			BlockPos blockpos = context.getClickedPos();
			Direction direction = context.getClickedFace();
			BlockState blockstate = world.getBlockState(blockpos);
			Block block = blockstate.getBlock();
			if (block == Blocks.SPAWNER) {
				TileEntity tileentity = world.getBlockEntity(blockpos);
				if (tileentity instanceof MobSpawnerTileEntity) {
					AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileentity).getSpawner();
					EntityType<?> entitytype1 = DMAEntities.getEntityTypeFromString(this.entityType);
					abstractspawner.setEntityId(entitytype1);
					tileentity.setChanged();
					world.sendBlockUpdated(blockpos, blockstate, blockstate, 3);
					itemstack.shrink(1);
					return ActionResultType.SUCCESS;
				}
			}

			BlockPos blockpos1;
			if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
				blockpos1 = blockpos;
			} else {
				blockpos1 = blockpos.relative(direction);
			}

			EntityType<?> entitytype = DMAEntities.getEntityTypeFromString(this.entityType);
			Entity entity = entitytype.spawn((ServerWorld)world, itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
			itemstack.shrink(1);
			if (entity != null && this.variants != null && entity instanceof IEntityVariant) {
				String variant = (String)this.variants.get((new Random()).nextInt(this.variants.size()));
				((IEntityVariant)entity).setID(variant);
			}

			return ActionResultType.SUCCESS;
		}
	}

	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
		if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
			return ActionResult.pass(itemstack);
		} else if (worldIn.isClientSide) {
			return ActionResult.success(itemstack);
		} else {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
			BlockPos blockpos = blockraytraceresult.getBlockPos();
			if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
				return ActionResult.pass(itemstack);
			} else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, blockraytraceresult.getDirection(), itemstack)) {
				EntityType<?> entitytype = DMAEntities.getEntityTypeFromString(this.entityType);
				Entity entity;
				if ((entity = entitytype.spawn((ServerWorld)worldIn, itemstack, playerIn, blockpos, SpawnReason.SPAWN_EGG, false, false)) == null) {
					return ActionResult.pass(itemstack);
				} else {
					if (!playerIn.abilities.instabuild) {
						itemstack.shrink(1);
					}

					itemstack.shrink(1);
					if (entity != null && this.variants != null && entity instanceof IEntityVariant) {
						String variant = (String)this.variants.get((new Random()).nextInt(this.variants.size()));
						((IEntityVariant)entity).setID(variant);
					}

					playerIn.awardStat(Stats.ITEM_USED.get(this));
					return ActionResult.success(itemstack);
				}
			} else {
				return ActionResult.fail(itemstack);
			}
		}
	}

	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (this.entityType != null && worldIn != null && this.entityType == "dalek") {
			((IDalek) DMDalekRegistry.getDaleks().get(this.variants.get(0))).getTooltips(tooltip);
		}

	}
}
