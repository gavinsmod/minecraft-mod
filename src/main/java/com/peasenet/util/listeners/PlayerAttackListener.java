/*
 * Copyright (c) 2022. Gavin Pease and contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
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
package com.peasenet.util.listeners;

import com.peasenet.packets.OutputPacket;
import com.peasenet.util.event.Event;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

/**
 * A listener for packets being sent.
 *
 * @author GT3CH1
 * @version 12/22/2022
 */
public interface PlayerAttackListener extends Listener {
    /**
     * Called when a packet is sent.
     *
     * @see OutputPacket
     */
    void onAttackEntity();

    /**
     * An event for when a packet is sent.
     *
     * @author GT3CH1
     * @version 12/22/2022
     */
    class PlayerAttackEvent extends Event<PlayerAttackListener> {
        Entity entity;

        /**
         * Creates a new PacketSendEvent.
         *
         * @param packet - The packet being sent.
         */
        public PlayerAttackEvent() {
        }

        @Override
        public void fire(ArrayList<PlayerAttackListener> listeners) {
            for (PlayerAttackListener listener : listeners) {
                listener.onAttackEntity();
            }
        }

        @Override
        public Class<PlayerAttackListener> getEvent() {
            return PlayerAttackListener.class;
        }
    }
}
