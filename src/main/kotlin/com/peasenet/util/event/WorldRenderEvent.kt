/*
 * MIT License
 *
 * Copyright (c) 2022-2024, Gavin C. Pease
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
package com.peasenet.util.event

import com.peasenet.util.listeners.WorldRenderListener
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.world.ClientWorld

/**
 * The event for the world render event.
 *
 * @author GT3CH1
 * @version 03-02-2023
 */
class WorldRenderEvent
/**
 * Creates a new world render event.
 *
 * @param level  - The world being rendered.
 * @param stack  - The matrix stack.
 * @param buffer - The buffer builder.
 * @param delta  - The delta.
 */(var level: ClientWorld, var stack: MatrixStack, var buffer: BufferBuilder, var delta: Float) :
    Event<WorldRenderListener>() {
    override fun fire(listeners: ArrayList<WorldRenderListener>) {
        for (listener in listeners) {
            listener.onWorldRender(level, stack, buffer, delta)
        }
    }

    override val event: Class<WorldRenderListener>
        get() = WorldRenderListener::class.java
}