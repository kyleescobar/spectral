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
import com.sun.jna.platform.win32.WinUser.*
import java.awt.Canvas
import java.awt.Graphics
import java.util.concurrent.atomic.AtomicBoolean

class NativeCanvas(val hostHandle: WinDef.HWND) : Canvas() {

    private var parentHandle: WinDef.HWND? = null
    private var selfHandle: WinDef.HWND? = null

    private val docked = AtomicBoolean(false)

    fun release() {
        if(docked.get()) {
            User32.INSTANCE.SetParent(hostHandle, parentHandle)
            docked.set(false)
        }
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        dockNativeWindow()

        User32.INSTANCE.MoveWindow(hostHandle, 0, 0, width, height, true)
        User32.INSTANCE.SetWindowLong(hostHandle, GWL_STYLE, WS_EX_TRANSPARENT or WS_EX_LAYERED)
    }

    private fun dockNativeWindow() {
        if(!docked.get()) {
            selfHandle = WinDef.HWND(Native.getComponentPointer(this))

            if(selfHandle != null) {
                parentHandle = User32.INSTANCE.GetAncestor(hostHandle, GA_PARENT)

                User32.INSTANCE.SetParent(hostHandle, selfHandle)
                User32.INSTANCE.SetWindowLong(hostHandle, GWL_STYLE, WS_EX_TRANSPARENT or WS_EX_LAYERED)
                User32.INSTANCE.ShowWindow(hostHandle, SW_SHOW)

                docked.set(true)

                repaint()
            }
        }
    }
}