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
package com.peasenet.mods.movement

import com.peasenet.mods.Mod
import com.peasenet.mods.Type

/**
 * @author gt3ch1
 * @version 03-02-2023
 */
class ModSpeed : Mod(Type.SPEED) {
    override fun onTick() {
        if (client.player() == null) return
        val player = client.player()

        if (player!!.isSneaking) return
        if (!player.isOnGround) return
        var velocity = player.velocity
        velocity = velocity.multiply(1.5)
        val currentSpeed = Math.sqrt(Math.pow(velocity.x, 2.0) + Math.pow(velocity.z, 2.0))
        player.velocity = velocity
        if (currentSpeed > 0.5) player.velocity = velocity.multiply(0.5 / currentSpeed)
    }
}