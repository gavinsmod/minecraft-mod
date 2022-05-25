package com.peasenet.mods;

import com.peasenet.util.KeyBindUtils;

/**
 * @author gt3ch1
 * @version 5/21/2022
 * A mod that allows the player to see tracers towards chests.
 */
public class ModChestTracer extends Mod {
    public ModChestTracer() {
        super(Type.CHEST_TRACER, Type.Category.RENDER, KeyBindUtils.registerKeyBindForType(Type.CHEST_TRACER));
    }
}
