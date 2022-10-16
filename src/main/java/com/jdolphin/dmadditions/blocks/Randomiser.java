package com.jdolphin.dmadditions.blocks;

import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.main.DMConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraft.block.LeverBlock;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.block.tardis.FastReturnLeverBlock;

import java.util.Random;

import static net.minecraft.block.AbstractButtonBlock.POWERED;

public class Randomiser extends Block {

    public Randomiser(AbstractBlock.Properties properties) {
        super(properties);
    }

    private double xPos;
    private double yPos;
    private double zPos;
    private String dimensionKey;

    public RegistryKey<World> dimensionWorldKey() {
        return RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(this.dimensionKey));
    }

    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity p_225533_4_, Hand handIn, BlockRayTraceResult p_225533_6_,
                                double amt, Direction.Axis axis, TardisData data, RegistryKey<World> key) {

        if (handIn == Hand.MAIN_HAND) {
            worldIn.setBlockAndUpdate(pos, (BlockState) state.setValue(POWERED, !(Boolean) state.getValue(POWERED)));

            if (ServerLifecycleHooks.getCurrentServer() != null) {


                WorldBorder border = ServerLifecycleHooks.getCurrentServer().getLevel(this.dimensionWorldKey()).getWorldBorder();


                double maxX = border.getMaxX();
                double minX = border.getMinX();

                double maxZ = border.getMaxZ();
                double minZ = border.getMinZ();


                switch (axis) {
                    case X:
                        this.xPos = Math.floor(Math.random() * (maxX - minX + 1) + minX);
                        break;
                    case Y:
                        this.yPos = amt;
                        break;
                    case Z:
                        this.zPos = Math.floor(Math.random() * (maxZ - minZ + 1) + minZ);
                }
                TardisFlightPool.updateFlight(data, xPos, yPos, zPos, key);
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.SUCCESS;
    }
}
