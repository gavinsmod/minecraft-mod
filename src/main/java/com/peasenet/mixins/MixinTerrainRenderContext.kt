/*
 * Copyright (c) 2022-2023. Gavin Pease and contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package com.peasenet.mixins

import com.peasenet.main.GavinsMod
import com.peasenet.util.event.TessellateBlockEvent
import com.peasenet.util.event.data.TessellateBlock
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.render.model.BakedModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.BlockPos
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * Mixin to rendering terrain, used to cancel rendering of some blocks for xray.
 *
 * @author gt3ch1
 * @version 03-02-2023
 */
@Mixin(TerrainRenderContext::class)
class MixinTerrainRenderContext {
    @Inject(at = [At("HEAD")], method = ["tessellateBlock"], cancellable = true)
    private fun tessellateBlock(
        blockState: BlockState,
        blockPos: BlockPos,
        model: BakedModel,
        matrixStack: MatrixStack,
        ci: CallbackInfo
    ) {
        val tb = TessellateBlock(blockState, blockPos, model, matrixStack)
        val evt = TessellateBlockEvent(tb)
        GavinsMod.eventManager!!.call(evt)
        if (evt.isCancelled) {
            ci.cancel()
        }
    }
}