package com.jdolphin.dmadditions.item;

import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.item.gun.GunItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SonicBlasterItem extends GunItem {
	private static Map<BlockPos, BlockState> removedBlocks = new HashMap<>();
	private static BlockPos lastRemovedPos = BlockPos.ZERO;
	private static Long lastRemovedTime = 0L;
	private static boolean isRestoreMode = false;

	protected RegistryObject<SoundEvent> chargeSound;
	protected RegistryObject<SoundEvent> shootSound;
	protected final DMProjectiles.Laser laserType;
	protected final IItemTier tier;
	public float requiredChargeTime;
	public float attackDamage;
	private List<RegistryObject<Item>> validBullets = new ArrayList();

	public SonicBlasterItem(IItemTier tier, float chargeInSeconds, float attackDamage, DMProjectiles.Laser laserType, RegistryObject<SoundEvent> lasergunChargeSound, RegistryObject<SoundEvent> lasergunShootSound, Properties properties, RegistryObject<Item>... bullets) {
		super(tier, chargeInSeconds, attackDamage, laserType, lasergunChargeSound, lasergunShootSound, properties, bullets);
		this.laserType = laserType;
		this.tier = tier;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		// Check if the player is not in the process of breaking blocks
		ItemStack stack = new ItemStack(this);
		if (!isBreakingBlocks(player)) {
			// Get the block in front of the player
			RayTraceResult result = getPlayerLookBlock(world, player, 10);
			BlockPos targetPos = new BlockPos(result.getLocation());

			CompoundNBT tag = stack.getOrCreateTag(); //Use this to save stuff

			// Remove the block chunk
			removeBlockChunk(world, targetPos, player);

			// Store the last removed position and time
			lastRemovedPos = targetPos;
			lastRemovedTime = System.currentTimeMillis();
		}/* else {
				// Toggle restore mode
				toggleRestoreMode(player);
				player.displayClientMessage(new StringTextComponent("Mode changed to: Restore Mode").withStyle(TextFormatting.GREEN), true);
			}
			*/

		// Return the item stack
		return new ActionResult<>(ActionResultType.SUCCESS, player.getItemInHand(hand));
	}

	private boolean isBreakingBlocks(PlayerEntity player) {
		return lastRemovedTime - System.currentTimeMillis() < 1000; // 1 second cooldown
	}

	private RayTraceResult getPlayerLookBlock(World world, PlayerEntity player, int distance) {
		Vector3d vec3d = player.getEyePosition(1.0F);
		Vector3d vec3d1 = player.getViewVector(1.0F);
		Vector3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
		return world.clip(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
	}

	private void removeBlockChunk(World world, BlockPos pos, PlayerEntity player) {
		// Iterate over the blocks in the chunk
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					BlockPos blockPos = pos.offset(x, y, z);
					// Check if the block can be broken by the player
					if (player.isCreative() || player.getMainHandItem().getItem().canHarvestBlock(player.getMainHandItem(), world.getBlockState(blockPos))) {
						// Store the block state
						removedBlocks.put(blockPos, world.getBlockState(blockPos));
						// Remove the block
						world.destroyBlock(blockPos, false);
					}
				}
			}
		}
	}

	public static void restoreBlockChunk(World world, BlockPos pos) {
		if (pos == null)
			return;
		// Iterate over the blocks in the chunk
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					BlockPos blockPos = pos.offset(x, y, z);
					// Check if the block can be restored
					if (removedBlocks.containsKey(blockPos)) {
						// Restore the block
						world.setBlock(blockPos, removedBlocks.get(blockPos), 3);
						removedBlocks.remove(blockPos);
					}
				}
			}
		}
	}

	public static void toggleRestoreMode(PlayerEntity player) {
		isRestoreMode = !isRestoreMode;
		player.displayClientMessage(new StringTextComponent("Mode changed to: " + (isRestoreMode ? "Restore Mode" : "Break Mode")).withStyle(TextFormatting.GREEN), true);
	}
}