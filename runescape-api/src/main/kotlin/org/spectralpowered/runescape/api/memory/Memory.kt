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

package org.spectralpowered.runescape.api.memory

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.IntByReference
import org.jire.kna.attach.Attach
import org.jire.kna.attach.windows.WindowsAttachAccess
import org.jire.kna.attach.windows.WindowsAttachedModule
import org.jire.kna.attach.windows.WindowsAttachedProcess
import org.spectralpowered.runescape.api.Client
import org.spectralpowered.runescape.api.client
import org.spectralpowered.runescape.api.util.every
import org.tinylog.kotlin.Logger
import retry

private const val PROCESS_NAME = "osclient.exe"

private const val WINDOW_TITLE = "Old School RuneScape"

internal lateinit var osrs: WindowsAttachedProcess private set
private lateinit var moduleBase: WindowsAttachedModule private set

lateinit var osrsHwnd: WinDef.HWND private set

var osrsPid: Int = -1
    private set

fun attachOSRSProcess() {
    Logger.info("Waiting for Old School RuneScape process to start...")

    retry(0L) {
        osrs = Attach.byName(PROCESS_NAME, WindowsAttachAccess.All) {} as WindowsAttachedProcess
        osrsHwnd = User32.INSTANCE.FindWindow(null, WINDOW_TITLE)
        osrsPid = getProcessIdFromHwnd(osrsHwnd)
    }

    /*
     * Find the OSRS base module.
     */
    retry(0L) {
        val modules = osrs.modules(attach = true)
        moduleBase = modules.byName(PROCESS_NAME) as WindowsAttachedModule
        client = Client(moduleBase.address)
    }

    every(0L) {
        if(client.gameState == 10 && client.loginState == 0) {
            client.loginState = 12
        }
    }

    Logger.info("Successfully attached to Old School RuneScape memory.")
}

private fun getProcessIdFromHwnd(hwnd: WinDef.HWND): Int {
    val processId = IntByReference(-1)
    User32.INSTANCE.GetWindowThreadProcessId(hwnd, processId)
    return processId.value
}