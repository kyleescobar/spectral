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
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser.*
import org.spectralpowered.runescape.api.osrs
import java.awt.Canvas
import java.awt.Graphics
import java.util.concurrent.atomic.AtomicBoolean

class GameContainer(private val hostHandle: WinDef.HWND) : Canvas() {

    private var parentHandle: WinDef.HWND? = null
    private var selfHandle: WinDef.HWND? = null
    private val attached = AtomicBoolean(false)

    fun release() {
        if(attached.get()) {
            if(User32.INSTANCE.SetParent(hostHandle, parentHandle) == null) {
                NativeError.handle("Failure when parenting host to parent handle.", Kernel32.INSTANCE.GetLastError())
            }

            attached.set(false)
            /*
             * Terminate the Steam client process along with the current JVM process.
             */
            Runtime.getRuntime().exec("taskkill /F /PID ${osrs.id}")
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
                if(User32.INSTANCE.SetParent(hostHandle, selfHandle) == null) {
                    NativeError.handle("Failed to parent host handle to the local JFrame handle.", Kernel32.INSTANCE.GetLastError())
                }

                /*
                 * Remove window decorations from the client window handle.
                 */
                User32.INSTANCE.SetWindowLong(hostHandle, GWL_STYLE, User32.INSTANCE.GetWindowLong(hostHandle, GWL_STYLE)
                            and (WS_THICKFRAME or WS_BORDER or WS_DLGFRAME or WS_CAPTION or WS_MINIMIZEBOX or WS_MAXIMIZEBOX or WS_SYSMENU)
                )

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
         * Dock the native game client to this component.
         */
        attach()

        /*
         * Remove window decorations from the client window handle.
         */
        User32.INSTANCE.SetWindowLong(hostHandle, GWL_STYLE, User32.INSTANCE.GetWindowLong(hostHandle, GWL_STYLE)
                and (WS_THICKFRAME or WS_BORDER or WS_DLGFRAME or WS_CAPTION or WS_MINIMIZEBOX or WS_MAXIMIZEBOX or WS_SYSMENU).inv()
        )

        User32.INSTANCE.SetWindowPos(hostHandle, null, 0, 0, width, height, SWP_NOZORDER)
        User32.INSTANCE.SetWindowPos(hostHandle, WinDef.HWND(Pointer.createConstant(-2)), 0, 0, 0, 0, SWP_NOSIZE or SWP_NOMOVE)

    }

    private class NativeError private constructor(message: String): RuntimeException(message) {

        private val HWND_TOPMOST = WinDef.HWND(Pointer.createConstant(-2))

        companion object {

            fun handle(message: String, code: Int) {
                if(code != 0) {
                    throw NativeError(message)
                }
            }
        }
    }
}