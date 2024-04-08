/*
 * MIT License
 *
 * Copyright (c) 2022-2024.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.peasenet.mods.render

import com.peasenet.util.event.data.RenderSubmergedOverlay
import com.peasenet.util.listeners.RenderSubmergedOverlayListener

/**
 * @author gt3ch1
 * @version 03-02-2023
 * A mod that disables the pumpkin overlay.
 */
class ModAntiFire : RenderMod(
    "Anti Fire",
    "gavinsmod.mod.render.antifire",
    "antifire"
), RenderSubmergedOverlayListener {
    override fun onEnable() {
        super.onEnable()
        em.subscribe(RenderSubmergedOverlayListener::class.java, this)
    }

    override fun onDisable() {
        super.onDisable()
        em.unsubscribe(RenderSubmergedOverlayListener::class.java, this)
    }

    override fun onRenderOverlay(overlay: RenderSubmergedOverlay) {
        overlay.cancel()
    }
}