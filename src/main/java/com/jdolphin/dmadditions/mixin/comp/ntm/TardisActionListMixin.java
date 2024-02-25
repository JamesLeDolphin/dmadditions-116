package com.jdolphin.dmadditions.mixin.comp.ntm;

import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.tardis.mod.blocks.TransductionBarrierBlock;
import net.tardis.mod.controls.LandingTypeControl;
import net.tardis.mod.entity.TardisDisplayEntity;
import net.tardis.mod.helper.LandingSystem;
import net.tardis.mod.helper.WorldHelper;
import net.tardis.mod.misc.SpaceTimeCoord;
import net.tardis.mod.sounds.TSounds;
import net.tardis.mod.tileentities.IAffectTARDISLanding;
import net.tardis.mod.tileentities.console.misc.AlarmType;
import net.tardis.mod.tileentities.console.misc.MonitorOverride;
import net.tardis.mod.tileentities.exteriors.ExteriorTile;
import net.tardis.mod.tileentities.machines.TransductionBarrierTile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mixin(value = TardisActionList.class, priority = 1200)
public abstract class TardisActionListMixin {

	@Unique
	private static Location affectDMTardis(World world, TransductionBarrierTile barrierTile, PlayerEntity player, TardisData data) {
		if (!world.isClientSide()) {
			if (barrierTile.getBlockState().hasProperty(TransductionBarrierBlock.ACTIVATED) && barrierTile.getBlockState().getValue(TransductionBarrierBlock.ACTIVATED)) {
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
			List<TileEntity> possibleHazards = WorldHelper.getTEsInChunks((ServerWorld) world, new ChunkPos(pos), 3);
			possibleHazards.removeIf((tex) -> !(tex instanceof IAffectTARDISLanding));

            for (TileEntity te : possibleHazards) {
                IAffectTARDISLanding affect = (IAffectTARDISLanding) te;
                if (pos.closerThan(te.getBlockPos(), affect.getEffectiveRange())) {
                    if (te instanceof TransductionBarrierTile) {
                        Location coord = affectDMTardis(world,
                                (TransductionBarrierTile) te, player, data);
                        //data.setCurrentLocation(coord.getBlockPosition(), coord.dimensionWorldKey());
                        cir.setReturnValue(false);
                    }
                }
                cir.setReturnValue(false);
            }
		}
	}
}
