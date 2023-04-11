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
package com.peasenet.mods.tracer

import com.peasenet.main.GavinsMod
import com.peasenet.mods.Type
import com.peasenet.settings.ColorSetting
import com.peasenet.util.RenderUtils
import com.peasenet.util.event.data.BlockEntityRender
import com.peasenet.util.event.data.EntityRender
import net.minecraft.entity.player.PlayerEntity

/**
 * @author gt3ch1
 * @version 04-11-2023
 * A mod that allows the player to see a tracer to other players.
 */
class ModEntityPlayerTracer : ModTracer(Type.ENTITY_PLAYER_TRACER) {
    init {
        val colorSetting = ColorSetting(
                "gavinsmod.settings.tracer.player.color",
                GavinsMod.tracerConfig.playerColor
        )
        colorSetting.setCallback { tracerConfig.playerColor = colorSetting.color }
        colorSetting.color = GavinsMod.tracerConfig.playerColor
        addSetting(colorSetting)
    }

    override fun onEntityRender(er: EntityRender) {
        if (er.buffer == null) return
        if (er.entity !is PlayerEntity) return
        RenderUtils.renderSingleLine(
                er.stack,
                er.buffer!!,
                er.playerPos!!,
                er.center!!,
                tracerConfig.playerColor,
                tracerConfig.alpha
        )
    }

    override fun onRenderBlockEntity(er: BlockEntityRender) {
       
    }
}