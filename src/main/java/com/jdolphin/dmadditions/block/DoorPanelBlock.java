package com.jdolphin.dmadditions.block;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class DoorPanelBlock extends HorizontalBlock implements IBetterPanel {
	private boolean doorOpenLeft;

	public DoorPanelBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		if (world.isClientSide || hand != Hand.MAIN_HAND)
			return ActionResultType.PASS;

		world.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1, 1);

		TardisData tardis = DMTardis.getTardisFromInteriorPos(pos);

		if (ServerLifecycleHooks.getCurrentServer() == null)
			return ActionResultType.CONSUME;

		if (tardis.isInFlight()) {
			ChatUtil.sendError(player, new TranslationTextComponent("notice.dalekmod.tardis.in_flight"), ChatUtil.MessageType.STATUS_BAR);
			return ActionResultType.FAIL;
		}

		if (tardis.isLocked()) {
			ChatUtil.sendError(player, new TranslationTextComponent("notice.dalekmod.tardis.is_locked"), ChatUtil.MessageType.STATUS_BAR);
			return ActionResultType.FAIL;
		}

		ServerWorld exteriorWorld = world.getServer().getLevel(tardis.getCurrentLocation().dimensionWorldKey());
		BlockPos exteriorPos = tardis.getCurrentLocation().getBlockPosition();
		BlockState exteriorState = exteriorWorld.getBlockState(exteriorPos);
		TileEntity te = exteriorWorld.getBlockEntity(exteriorPos);

		if (exteriorState.getBlock() != DMBlocks.TARDIS.get() || !(te instanceof TardisTileEntity))
			return ActionResultType.FAIL;

		TardisTileEntity exterior = (TardisTileEntity) te;

		exterior.toggleDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);

		world.playSound(null, pos, DMSoundEvents.TARDIS_CONTROLS_DING.get(), SoundCategory.BLOCKS, 1, 1);

		return ActionResultType.SUCCESS;
	}
}
