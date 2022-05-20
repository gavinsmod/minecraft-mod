package com.peasenet.mods;

import com.peasenet.gui.MainScreen;
import com.peasenet.main.GavinsModClient;
import com.peasenet.util.KeyBindUtils;

public class ModGui extends Mod{
    public ModGui() {
        super(Mods.MOD_GUI, ModCategory.RENDER, KeyBindUtils.registerKeyBindForType(Mods.MOD_GUI));
    }

    @Override
    public void onEnable() {
        GavinsModClient.getMinecraftClient().setScreen(new MainScreen(null));
    }
    @Override
    public void onDisable() {
        GavinsModClient.getMinecraftClient().setScreen(new MainScreen(null));
    }
}