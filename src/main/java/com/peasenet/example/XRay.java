package com.peasenet.example;

import com.peasenet.mods.Mods;
import com.peasenet.mods.XrayMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author gt3ch1
 * @version 5/15/2022
 */
@Mixin(Block.class)
public class XRay {
    @Inject(at = @At("HEAD"), method = "shouldDrawSide(" + "Lnet/minecraft/block/BlockState;" + // state
            "Lnet/minecraft/world/BlockView;" + // reader
            "Lnet/minecraft/util/math/BlockPos;" + // pos
            "Lnet/minecraft/util/math/Direction;" + // face
            "Lnet/minecraft/util/math/BlockPos;" + // blockPosaaa
            ")Z", // ci
            cancellable = true)
    private static boolean xray(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> cir) {
        if (Mods.xrayEnabled) {
            boolean blockVisible = XrayMod.isBlockVisible(state);
            cir.setReturnValue(blockVisible);
            return blockVisible;
        }
        cir.setReturnValue(true);
        return true;
    }
}
