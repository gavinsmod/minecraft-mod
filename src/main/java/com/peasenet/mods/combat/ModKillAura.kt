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
package com.peasenet.mods.combat

import com.peasenet.main.GavinsModClient
import com.peasenet.mods.Mod
import com.peasenet.mods.Type
import com.peasenet.util.PlayerUtils
import com.peasenet.util.math.MathUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.mob.MobEntity
import java.util.stream.StreamSupport

/**
 * @author gt3ch1
 * @version 03-01-2023
 * A mod that makes the player face and attack the nearest mob.
 */
class ModKillAura : Mod(Type.KILL_AURA) {
    override fun onTick() {
        if (GavinsModClient.getMinecraftClient().world != null && isActive) {
            val stream = StreamSupport.stream(GavinsModClient.getMinecraftClient().world.entities.spliterator(), false)
                .filter { e: Entity? -> e is MobEntity }
                .filter { obj: Entity -> obj.isAlive }
                .filter { e: Entity? -> PlayerUtils.distanceToEntity(e) <= 16 }
                .sorted { e1: Entity?, e2: Entity? ->
                    (PlayerUtils.distanceToEntity(e1).toInt() - PlayerUtils.distanceToEntity(e2)).toInt()
                }
                .map { e: Entity? -> e as MobEntity? }
            stream.forEach { entity: MobEntity? ->
                entity?.let { MathUtils.getRotationToEntity(it) }?.let { PlayerUtils.setRotation(it) }
                PlayerUtils.attackEntity(entity)
            }
        }
    }
}