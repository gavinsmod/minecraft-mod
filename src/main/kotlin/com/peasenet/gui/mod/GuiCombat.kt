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
package com.peasenet.gui.mod

import com.peasenet.gavui.math.PointF
import com.peasenet.mods.ModCategory
import net.minecraft.text.Text

/**
 * @author gt3ch1
 * @version 03-02-2023
 * Creates a new gui for combat mods as a dropdown.
 */
class GuiCombat
    (
    position: PointF = PointF(100f, 20f),
    width: Int = 75,
    height: Int = 10,
    text: Text = Text.translatable("gavinsmod.gui.combat"),
    category: ModCategory = ModCategory.COMBAT,
    maxChildren: Int = 4
) : GuiMod(
    position, width, height, text, category, maxChildren
)