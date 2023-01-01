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

package com.peasenet.gui;

import com.peasenet.gavui.Gui;
import com.peasenet.gavui.GuiClick;
import com.peasenet.gavui.GuiScroll;
import com.peasenet.gavui.color.Colors;
import com.peasenet.gavui.math.PointF;
import com.peasenet.main.GavinsMod;
import com.peasenet.main.Mods;
import com.peasenet.mods.Mod;
import com.peasenet.mods.Type;
import com.peasenet.settings.Setting;
import com.peasenet.settings.SlideSetting;
import net.minecraft.text.Text;

import java.util.ArrayList;

/**
 * @author gt3ch1
 * @version 6/28/2022
 * A settings gui to control certain features of the mod.
 */
public class GuiSettings extends GuiElement {

    /**
     * The tracer dropdown
     */
    private static GuiScroll tracerDropdown;

    /**
     * The esp dropdown
     */
    private static GuiScroll espDropdown;

    /**
     * The render dropdown
     */
    private static GuiScroll renderDropdown;

    /**
     * The miscellaneous dropdown
     */
    private static GuiScroll miscDropdown;

    /**
     * The dropdown containing gui settings.
     */
    private static GuiScroll guiDropdown;

    private static GuiClick resetButton;

    private static float resetWidth = 0;
    private static PointF resetPos;

    /**
     * Creates a new GUI settings screen.
     */
    public GuiSettings() {
        super(Text.translatable("gavinsmod.gui.settings"));
        renderDropdown = new GuiScroll(new PointF(10, 20), 100, 10, Text.translatable("gavinsmod.settings.render"));
        miscDropdown = new GuiScroll(new PointF(115, 20), 100, 10, Text.translatable("gavinsmod.settings.misc"));
        guiDropdown = new GuiScroll(new PointF(220, 20), 100, 10, Text.translatable("gavinsmod.settings.gui"));
        espDropdown = new GuiScroll(new PointF(10, 130), 110, 10, Text.translatable("gavinsmod.settings.esp"));
        tracerDropdown = new GuiScroll(new PointF(125, 130), 115, 10, Text.translatable("gavinsmod.settings.tracer"));
        resetButton = new GuiClick(new PointF(0, 1), 4, 10, Text.translatable("gavinsmod.settings.reset"));
        reloadGui();
    }

    /**
     * Sets up miscellaneous settings that don't really have a proper
     * home.
     */
    private static void miscSettings() {
        var espAlpha = new SlideSetting("gavinsmod.settings.alpha");
        espAlpha.setCallback(() -> GavinsMod.espConfig.setAlpha(espAlpha.getValue()));
        espAlpha.setValue(GavinsMod.espConfig.getAlpha());

        var tracerAlpha = new SlideSetting("gavinsmod.settings.alpha");
        tracerAlpha.setCallback(() -> GavinsMod.tracerConfig.setAlpha(tracerAlpha.getValue()));
        tracerAlpha.setValue(GavinsMod.tracerConfig.getAlpha());

        espDropdown.addElement(espAlpha.getGui());
        tracerDropdown.addElement(tracerAlpha.getGui());
    }

    @Override
    public void init() {
        super.init();
        var titleW = textRenderer.getWidth(Text.translatable("gavinsmod.gui.settings")) + 16;
        var resetText = Text.translatable("gavinsmod.settings.reset");
        var width = textRenderer.getWidth(resetText);
        if (resetPos == null)
            resetPos = new PointF(titleW, 1);
        resetButton.setTitle(resetText);
        if (resetWidth == 0.0)
            resetWidth = width + 4;
        resetButton.setWidth(resetWidth);
        resetButton.setPosition(resetPos);
        resetButton.setDefaultPosition(resetButton.getBox());
        resetButton.setBackground(Colors.DARK_RED);
        resetButton.setCallback(() -> {
            GavinsMod.gui.reset();
            GavinsMod.guiSettings.reset();
        });
    }

    @Override
    public void close() {
        GavinsMod.setEnabled(Type.SETTINGS, false);
        super.close();
    }

    /**
     * Reloads this gui by clearing all children, and recreating them.
     */
    public void reloadGui() {
        guis.forEach(Gui::clearChildren);
        guis.clear();
        // Create a plain gui in the top right corner

        miscSettings();

        addSettings(tracerDropdown, Type.Category.TRACERS);
        addSettings(espDropdown, Type.Category.ESPS);
        addSettings(renderDropdown, Type.Category.RENDER);
        addSettings(miscDropdown, Type.Category.MISC);
        addSettings(guiDropdown, Type.Category.GUI);
        guis.add(tracerDropdown);
        guis.add(espDropdown);
        guis.add(renderDropdown);
        guis.add(miscDropdown);
        guis.add(guiDropdown);
        guis.forEach(g -> g.setParent(true));
        guis.add(resetButton);
    }

    /**
     * Creates the esp settings scroll gui.
     *
     * @param parent   - The parent gui.
     * @param category - The category of mod types to add to the parent gui.
     */
    private void addSettings(Gui parent, Type.Category category) {
        var modList = new ArrayList<Mod>();
        // get all mods in esp category and have settings then add them to espDropdown
        Mods.getMods().stream().filter(m -> m.getCategory() == category && m.hasSettings()).forEach(modList::add);
        for (Mod m : modList) {
            var modSettings = m.getSettings();
            for (Setting s : modSettings) {
                s.getGui().setShrunkForScrollbar(false);
                parent.addElement(s.getGui());
            }
        }
    }
}
