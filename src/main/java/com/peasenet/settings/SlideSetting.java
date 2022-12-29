/*
 * Copyright (c) 2022-2022. Gavin Pease and contributors.
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

package com.peasenet.settings;

import com.peasenet.gavui.Gui;
import com.peasenet.gavui.GuiSlider;
import com.peasenet.gavui.math.PointD;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

/**
 * @author gt3ch1
 * @version 7/1/2022
 * A setting that can be clicked. This is purely dependant on the given callback.
 */
public class SlideSetting extends Setting {

    /**
     * The gui used to display the setting.
     */
    private final GuiSlider gui;

    /**
     * Creates a new click setting with the given name (?) and translation key.
     *
     * @param name           - The name of the setting.
     * @param translationKey - The translation key of the setting.
     */
    public SlideSetting(String name, String translationKey) {
        super(name, translationKey);
        gui = new GuiSlider(new PointD(0, 0), 100, 10, Text.translatable(translationKey));
        gui.setCallback(this::onClick);
    }

    public SlideSetting(String name) {
        this(name, I18n.translate(name));
    }

    public float getValue() {
        return gui.getValue();
    }

    @Override
    public Gui getGui() {
        return gui;
    }

    public void setValue(float alpha) {
        gui.setValue(alpha);
    }
}
