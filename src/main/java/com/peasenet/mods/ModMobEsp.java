package com.peasenet.mods;

import com.peasenet.util.KeyBindUtils;

/**
 * @author gt3ch1
 * A mod that allows the client to see boxes around mobs.
 */
public class ModMobEsp extends Mod {
    public ModMobEsp() {
        super(Mods.MOB_ESP, ModCategory.RENDER, KeyBindUtils.registerKeyBindForType(Mods.MOB_ESP));
    }
}
