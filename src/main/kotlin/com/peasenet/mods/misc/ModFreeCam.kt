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
package com.peasenet.mods.misc

import com.peasenet.main.GavinsMod
import com.peasenet.mods.Mod
import com.peasenet.mods.Type
import com.peasenet.util.FakePlayer
import com.peasenet.util.PlayerUtils
import com.peasenet.util.RenderUtils
import com.peasenet.util.event.AirStrafeEvent
import com.peasenet.util.event.data.OutputPacket
import com.peasenet.util.listeners.AirStrafeListener
import com.peasenet.util.listeners.PacketSendListener
import com.peasenet.util.listeners.WorldRenderListener
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.world.ClientWorld
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket
import net.minecraft.util.math.Vec3d

/**
 * A mod that allows the camera to be moved freely.
 *
 * @author GT3CH1
 * @version 03-13-2023
 */
class ModFreeCam : Mod(Type.FREECAM), PacketSendListener, WorldRenderListener, AirStrafeListener {
    private var fake: FakePlayer? = null
    override fun activate() {
        super.activate()
        fake = FakePlayer()
        em.subscribe(PacketSendListener::class.java, this)
        em.subscribe(WorldRenderListener::class.java, this)
        em.subscribe(AirStrafeListener::class.java, this)
    }

    override fun onTick() {
        super.onTick()
        if (!isActive) return
        val player = client.player()

        player.velocity = Vec3d.ZERO
        player.isOnGround = false
        player.abilities.flying = true
        if (player.input.sneaking) player.velocity = player.velocity.add(0.0, -1.0, 0.0)
        if (player.input.jumping) player.velocity = player.velocity.add(0.0, 1.0, 0.0)
    }

    override fun onDisable() {
        super.onDisable()
        fake!!.remove()
        em.unsubscribe(PacketSendListener::class.java, this)
        em.unsubscribe(WorldRenderListener::class.java, this)
        em.unsubscribe(AirStrafeListener::class.java, this)
    }

    override fun onWorldRender(level: ClientWorld, stack: MatrixStack, bufferBuilder: BufferBuilder, delta: Float) {
        val camera = MinecraftClient.getInstance().gameRenderer.camera
        val playerPos = PlayerUtils.getNewPlayerPosition(delta, camera)
        val aabb = RenderUtils.getEntityBox(delta, fake!!)
        RenderUtils.renderSingleLine(stack, bufferBuilder, playerPos, aabb.center, GavinsMod.tracerConfig!!.playerColor)
        RenderUtils.drawBox(stack, bufferBuilder, aabb, GavinsMod.espConfig!!.playerColor)
    }

    override fun onPacketSend(packet: OutputPacket) {
        if (packet.packet is PlayerMoveC2SPacket) packet.cancel()
    }

    override fun onAirStrafe(event: AirStrafeEvent) {
        val speed = 1.0f
        event.speed = speed
    }
}