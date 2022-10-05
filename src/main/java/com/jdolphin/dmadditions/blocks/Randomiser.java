package com.jdolphin.dmadditions.blocks;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import com.swdteam.common.block.tardis.FastReturnLeverBlock;

public class Randomiser {
    private double xPos;
    private double yPos;
    private double zPos;
    private String dimensionKey;

    public RegistryKey<World> dimensionWorldKey() {
        return RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(this.dimensionKey));
    }

    private void limitPos() {
        if (ServerLifecycleHooks.getCurrentServer() != null) {
            WorldBorder border = ServerLifecycleHooks.getCurrentServer().getLevel(this.dimensionWorldKey()).getWorldBorder();
            if (this.xPos >= border.getMaxX()) {
                this.xPos = border.getMaxX() - 1.0;
            }

            if (this.zPos >= border.getMaxZ()) {
                this.zPos = border.getMaxZ() - 1.0;
            }

            if (this.xPos <= border.getMinX()) {
                this.xPos = border.getMinX() + 1.0;
            }

            if (this.zPos <= border.getMinZ()) {
                this.zPos = border.getMinZ() + 1.0;
            }
        }
    }
}
