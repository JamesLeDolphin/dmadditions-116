package com.jdolphin.dmadditions.blocks;

import com.swdteam.main.DMConfig;
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

public class Randomiser {
    private double xPos;
    private double yPos;
    private double zPos;
    private String dimensionKey;

    public RegistryKey<World> dimensionWorldKey() {
        return RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(this.dimensionKey));
    }
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_, double amt, Direction.Axis axis) {

        if (ServerLifecycleHooks.getCurrentServer() != null) {
            WorldBorder border = ServerLifecycleHooks.getCurrentServer().getLevel(this.dimensionWorldKey()).getWorldBorder();

            double maxX = border.getMaxX();
            double minX = border.getMinX();

            double maxZ = border.getMaxZ();
            double minZ = border.getMinZ();


        switch (axis) {
            case X:
                this.xPos = Math.floor(Math.random()*(maxX-minX+1)+minX);
                break;
            case Y:
                this.yPos = amt;
                break;
            case Z:
                this.zPos = Math.floor(Math.random()*(maxZ-minZ+1)+minZ);
        }
    }
    }
}
