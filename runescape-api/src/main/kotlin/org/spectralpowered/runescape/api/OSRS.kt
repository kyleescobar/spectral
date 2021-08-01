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

package org.spectralpowered.runescape.api

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName
import org.spectralpowered.runescape.api.util.retry
import org.tinylog.kotlin.Logger


/**
 * The exe file name of the OSRS client process.
 */
const val OSRS_PROCESS_NAME = "osclient.exe"

/**
 * The title of the OSRS client window.
 */
const val OSRS_WINDOW_TITLE = "Old School RuneScape"

/**
 * The attached Old School RuneScape client process that is running.
 */
lateinit var osrs: Process private set

/**
 * The Window handle of the OSRS game client.
 */
lateinit var osrs_window: WinDef.HWND private set

object OSRS {

    /**
     * Attaches this JVM to the Old School RuneScape client process. This enables the JVM to
     * read and write memory values to the client.
     */
    fun attachClientProcess() {
        Logger.info("Waiting for the Old School RuneScape Steam client...")

        /*
         * Keep retrying every 100ms to attach to the client process.
         */
        retry(0L) {
            osrs = processByName(OSRS_PROCESS_NAME)!!
            User32.INSTANCE.FindWindow(null, OSRS_WINDOW_TITLE).also { osrs_window = it }
        }

        Logger.info("Successfully attached JVM to the Old School RuneScape Steam client.")
    }

}