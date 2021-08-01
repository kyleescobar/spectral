/*
 * Copyright (C) 2021 Spectral Powered <Kyle Escobar>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.spectralpowered.client.gui

import com.sun.jna.Native
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser.GWL_STYLE
import java.awt.Canvas
import java.awt.Graphics
import java.util.concurrent.atomic.AtomicBoolean

class GameContainer(private val hostHandle: WinDef.HWND) : Canvas() {

    private var parentHandle: WinDef.HWND? = null
    private var selfHandle: WinDef.HWND? = null
    private val attached = AtomicBoolean(false)

    fun release() {
        if(attached.get()) {
            User32.INSTANCE.SetParent(hostHandle, parentHandle)
            attached.set(false)
        }
    }

    private fun attach() {
        if(!attached.get()) {
            selfHandle = WinDef.HWND(Native.getComponentPointer(this))

            if(selfHandle != null) {
                parentHandle = User32.INSTANCE.GetAncestor(hostHandle, User32.GA_PARENT)

                /*
                 * Parent / dock the windows into the JFrame (selfHandle)
                 */
                User32.INSTANCE.SetParent(hostHandle, selfHandle)

                /*
                 * Remove window decorations from the client window handle.
                 */
                User32.INSTANCE.SetWindowLong(hostHandle, GWL_STYLE, 0)

                /*
                 * Make sure we Show the window
                 */
                User32.INSTANCE.ShowWindow(hostHandle, User32.SW_SHOW)

                attached.set(true)

                repaint()
            }
        }
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        /*
         * Handle window movement before we redraw the window.
         */
        User32.INSTANCE.MoveWindow(hostHandle, 0, 0, width, height, true)

        /*
         * Dock the native game client to this component.
         */
        attach()
    }
}