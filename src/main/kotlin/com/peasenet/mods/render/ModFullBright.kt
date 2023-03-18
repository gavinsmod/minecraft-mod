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
package com.peasenet.mods.render

import com.peasenet.main.GavinsMod
import com.peasenet.mods.Mod
import com.peasenet.mods.Type
import com.peasenet.settings.SlideSetting
import com.peasenet.settings.SubSetting
import com.peasenet.settings.ToggleSetting
import com.peasenet.util.RenderUtils

/**
 * @author gt3ch1
 * @version 03-02-2023
 * A mod that allows the client to see very clearly in the absence of a light source.
 */
class ModFullBright : Mod(Type.FULL_BRIGHT) {
    init {
        val gammaFade = ToggleSetting("gavinsmod.settings.render.gammafade")
        gammaFade.setCallback { fullbrightConfig.gammaFade = gammaFade.value }
        gammaFade.value = fullbrightConfig.gammaFade
        val autoFullBright = ToggleSetting("gavinsmod.settings.render.autofullbright")
        autoFullBright.setCallback { fullbrightConfig.autoFullBright = autoFullBright.value }
        autoFullBright.value = fullbrightConfig.autoFullBright
        val gamma = SlideSetting("gavinsmod.settings.render.fullbright.gamma")
        gamma.setCallback { fullbrightConfig.gamma = gamma.value }
        gamma.value = fullbrightConfig.gamma
        val subSetting = SubSetting(100, 10, translationKey)
        subSetting.add(gammaFade)
        subSetting.add(autoFullBright)
        subSetting.add(gamma)
        addSetting(subSetting)
    }

    override fun activate() {
        super.activate()
        if (!GavinsMod.isEnabled(Type.XRAY)) RenderUtils.setLastGamma()
        deactivating = false
    }

    override fun onTick() {
        if (isActive && !RenderUtils.isHighGamma) {
            RenderUtils.setHighGamma()
            return
        } else if (!GavinsMod.isEnabled(Type.XRAY) && !RenderUtils.isLastGamma && deactivating) {
            RenderUtils.setLowGamma()
            deactivating = !RenderUtils.isLastGamma
        }
    }

    override fun onDisable() {
        super.onDisable()
        if (!fullbrightConfig.gammaFade) {
            RenderUtils.gamma = RenderUtils.getLastGamma()
            return
        }
        if (RenderUtils.gamma > 16F && !deactivating)
            RenderUtils.gamma = 16.0

    }

    override fun deactivate() {
        deactivating = true
//        RenderUtils.gamma = fullbrightConfig.gamma.toDouble()
        super.deactivate()
    }
}