
package com.jdolphin.dmadditions.mixin;

import com.swdteam.common.block.AbstractLeverBlock;
import com.swdteam.common.block.tardis.FastReturnLeverBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.AbstractButtonBlock.POWERED;

@Mixin(FastReturnLeverBlock.class)
public abstract class FastReturnMixn extends AbstractLeverBlock {

    public FastReturnMixn(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("HEAD"), method = "use(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/math/BlockRayTraceResult;)Lnet/minecraft/util/ActionResultType;"
    )
    private ActionResultType onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_, CallbackInfoReturnable<ActionResultType> cir) {

        //Cancel the method at it's head, so it basically does nothing
        cir.cancel();
        {
            if (handIn == Hand.MAIN_HAND) {
                worldIn.setBlockAndUpdate(pos, (BlockState) state.setValue(POWERED, !(Boolean) state.getValue(POWERED)));
                worldIn.playSound((PlayerEntity) null, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), (SoundEvent) DMSoundEvents.TARDIS_CONTROLS_LEVER.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (worldIn.dimension().equals(DMDimensions.TARDIS) && !worldIn.isClientSide) {
                    TardisData data = DMTardis.getTardisFromInteriorPos(pos);
                    if (data != null && data.getPreviousLocation() != null) {
                        TardisFlightPool.updateFlight(data, data.getPreviousLocation());
                    }

                    ChatUtil.sendCompletedMsg(player, DMTranslationKeys.TARDIS_FAST_RETURN_SET, ChatUtil.MessageType.STATUS_BAR);
                }
            }

            return ActionResultType.CONSUME;
        }

    }
}
