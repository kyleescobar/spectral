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

package org.spectralpowered.client.ui

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser.*
import java.awt.Canvas
import java.awt.Graphics
import java.util.concurrent.atomic.AtomicBoolean

/**
 * This class is the component which the Steam client's window get parented to using
 * Java Native Access and the Windows win32 API.
 *
 * The reason it extends the [Canvas] class is because the component is a heavyweight element. This
 * means that the win32 api can actually parent its native components to it.
 */
class NativeGameShell(val steamClientWindow: WinDef.HWND) : Canvas() {

    /**
     * The window handle for the Spectral client JFrame
     */
    private var spectralClientWindow: WinDef.HWND? = null

    /**
     * The window handle for this canvas component.
     */
    private var selfClientWindow: WinDef.HWND? = null

    /**
     * Whether the steam client window has been parented to this component or not.
     */
    private var attached = AtomicBoolean(false)

    fun release() {
        if(attached.get()) {
            User32.INSTANCE.ShowWindow(steamClientWindow, SW_HIDE)
            attached.set(false)
        }
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        attach()

        if(attached.get()) {
            User32.INSTANCE.MoveWindow(steamClientWindow, 0, 0, width, height, false)
            steamClientWindow.hideBorders()
        }
    }

    private fun attach() {
        if(!attached.get()) {

            selfClientWindow = WinDef.HWND(Native.getComponentPointer(this))

            if(selfClientWindow != null) {

                spectralClientWindow = User32.INSTANCE.GetAncestor(steamClientWindow, GA_PARENT)

                steamClientWindow.hideBorders()
                User32.INSTANCE.SetParent(steamClientWindow, selfClientWindow)
                User32.INSTANCE.ShowWindow(steamClientWindow, SW_SHOW)

                attached.set(true)

                /*
                 * Repaint the canvas.
                 */
                repaint()
            }
        }
    }

    private fun WinDef.HWND.hideBorders() {
        val style = User32.INSTANCE.GetWindowLong(this, GWL_STYLE)
        User32.INSTANCE.SetWindowLong(this, GWL_STYLE, (style and (WS_CAPTION or WS_SIZEBOX).inv()))
    }
}