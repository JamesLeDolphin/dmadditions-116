package com.jdolphin.dmadditions.block;

import com.swdteam.common.init.*;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.network.packets.PacketXPSync;
import com.swdteam.util.ChatUtil;
import jdk.nashorn.internal.ir.Statement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class PlayerLocatorBlock extends Block implements IBetterPanel {

	public PlayerLocatorBlock(Properties properties) {
		super(properties);
	}

	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide && worldIn.dimension().equals(DMDimensions.TARDIS)) {
			return ActionResultType.CONSUME;
		} else {
			return ActionResultType.CONSUME;
		}
	}


	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return IBetterPanel.super.getStateForPlacement(context, defaultBlockState());
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
		return false;
	}

	@Override
	public ToolType getHarvestTool(BlockState state) {
		return ToolType.PICKAXE;
	}

	@Override
	public int getHarvestLevel(BlockState state) {
		return 0;
	}
}
