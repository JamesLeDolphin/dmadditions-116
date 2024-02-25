package com.jdolphin.dmadditions.mixin.comp.ntm;

import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(value = TardisActionList.class, priority = 1200)
public abstract class TardisActionListMixin {

	@Unique
	private static Location affectDMTardis(World world, net.tardis.mod.tileentities.machines.TransductionBarrierTile barrierTile, PlayerEntity player, TardisData data) {
		if (!world.isClientSide()) {
			if (barrierTile.getBlockState().hasProperty(net.tardis.mod.blocks.TransductionBarrierBlock.ACTIVATED) && barrierTile.getBlockState().getValue(net.tardis.mod.blocks.TransductionBarrierBlock.ACTIVATED)) {
				Random rand = world.random;
				BlockPos pos = data.getCurrentLocation().getBlockPosition();

				BlockPos landSpot = TardisActionList.getLandingPosition((ServerWorld) world,
					new BlockPos(pos.getY() + rand.nextInt(100), pos.getZ() + rand.nextInt(100),
						pos.getX() + rand.nextInt(100)), data.getCurrentLocation().getFacing());

				barrierTile.onBlockedTARDIS(null);
				ChatUtil.sendCompletedMsg(player, new TranslationTextComponent("text.tardis.transduction.line3"), ChatUtil.MessageType.STATUS_BAR);

				return new Location(landSpot, world.dimension());
			}
			return new Location(data.getCurrentLocation().getBlockPosition(), data.getCurrentLocation().dimensionWorldKey());
		}
		return null;
	}

	@Inject(method = "remat(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lcom/swdteam/common/tardis/TardisData;)Z", at = @At("HEAD"),
		remap = false, cancellable = true)
	private static void remat(PlayerEntity player, World world, TardisData data, CallbackInfoReturnable<Boolean> cir) {
		if (!world.isClientSide()) {
			BlockPos pos = data.getCurrentLocation().getBlockPosition();
			List<TileEntity> possibleHazards = net.tardis.mod.helper.WorldHelper.getTEsInChunks((ServerWorld) world, new ChunkPos(pos), 3);
			possibleHazards.removeIf((tex) -> !(tex instanceof net.tardis.mod.tileentities.IAffectTARDISLanding));

            for (TileEntity te : possibleHazards) {
				net.tardis.mod.tileentities.IAffectTARDISLanding affect = (net.tardis.mod.tileentities.IAffectTARDISLanding) te;
                if (pos.closerThan(te.getBlockPos(), affect.getEffectiveRange())) {
                    if (te instanceof net.tardis.mod.tileentities.machines.TransductionBarrierTile) {
                        Location coord = affectDMTardis(world,
                                (net.tardis.mod.tileentities.machines.TransductionBarrierTile) te, player, data);
                        //data.setCurrentLocation(coord.getBlockPosition(), coord.dimensionWorldKey());
                        cir.setReturnValue(false);
                    }
                }
                cir.setReturnValue(false);
            }
		}
	}
}
