/*
 * Copyright (c) 2022. Gavin Pease and contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
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

package com.peasenet.mods.esp;

import com.peasenet.main.GavinsMod;
import com.peasenet.mods.Mod;
import com.peasenet.mods.Type;
import com.peasenet.settings.ColorSetting;
import com.peasenet.settings.ToggleSetting;

/**
 * @author gt3ch1
 * @version 6/27/2022
 * A mod that allows the client to see boxes around mobs.
 */
public class ModMobEsp extends Mod {
    public ModMobEsp() {
        super(Type.MOB_ESP);
        ColorSetting hostileEspColor = new ColorSetting("gavinsmod.settings.esp.mob.hostile.color");
        hostileEspColor.setCallback(() -> {
            GavinsMod.espConfig.setHostileMobColor(hostileEspColor.getColor());
        });
        hostileEspColor.setColor(GavinsMod.espConfig.getHostileMobColor());

        ColorSetting peacefulEspColor = new ColorSetting("gavinsmod.settings.esp.mob.peaceful.color");
        peacefulEspColor.setCallback(() -> {
            GavinsMod.espConfig.setPeacefulMobColor(peacefulEspColor.getColor());
        });
        peacefulEspColor.setColor(GavinsMod.espConfig.getPeacefulMobColor());

        ToggleSetting hostileEspToggle = new ToggleSetting("gavinsmod.settings.esp.mob.hostile");
        hostileEspToggle.setCallback(() -> {
            GavinsMod.espConfig.setShowHostileMobs(hostileEspToggle.getValue());
        });
        hostileEspToggle.setValue(GavinsMod.espConfig.isShowHostileMobs());

        ToggleSetting peacefulEspToggle = new ToggleSetting("gavinsmod.settings.esp.mob.peaceful");
        peacefulEspToggle.setCallback(() -> {
            GavinsMod.espConfig.setShowPeacefulMobs(peacefulEspToggle.getValue());
        });
        peacefulEspToggle.setValue(GavinsMod.espConfig.isShowPeacefulMobs());

        addSetting(hostileEspColor);
        addSetting(peacefulEspColor);
        addSetting(hostileEspToggle);
        addSetting(peacefulEspToggle);
    }
}
