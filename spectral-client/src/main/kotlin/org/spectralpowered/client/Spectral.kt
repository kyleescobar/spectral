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

package org.spectralpowered.client

import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser.SW_HIDE
import org.spectralpowered.client.ui.SpectralTheme
import org.spectralpowered.client.ui.SpectralWindow
import org.spectralpowered.common.SteamUtil
import org.spectralpowered.common.inject
import kotlin.system.exitProcess

class Spectral {

    /**
     * The Spectral client window frame.
     */
    private val window: SpectralWindow by inject()

    /**
     * Starts the Spectral client.
     */
    fun start() {
        Logger.info("Starting the Spectral client...")

        /*
         * Install the Spectral LAF Theme
         */
        SpectralTheme.install()

        /*
         * Open the spectral client ui.
         */
        this.openWindow()
    }

    /**
     * Stops the Spectral client.
     */
    fun stop() {
        Logger.info("Stopping the Spectral client...")

        /*
         * Stop the Steam client process if it is still running.
         */
        SteamUtil.stopProcess()

        exitProcess(0)
    }

    private fun openWindow() {
        val hwnd = waitForWindow(SteamUtil.OSRS_WINDOW_TITLE)
        window.open(hwnd)
    }

    /**
     * Waits for a windows window with a given title to be present. Has a timeout of 60 seconds.
     */
    @Suppress("SameParameterValue")
    private fun waitForWindow(title: String): WinDef.HWND {
        Logger.info("Waiting for Old School RuneScape window to become present.")

        val startTime = System.currentTimeMillis()

        while(true) {
            val found = User32.INSTANCE.FindWindow(null, title)
            if(found != null) {
                Logger.info("Found the Old School RuneScape window.")
                /*
                 * Hide the window for now.
                 */
                User32.INSTANCE.ShowWindow(found, SW_HIDE)
                return found
            }

            /*
             * Check if the timeout period has elapsed.
             */
            val deltaTime = System.currentTimeMillis() - startTime
            if(deltaTime >= STEAM_WINDOW_TIMEOUT) {
                Logger.error("Failed to find the Old School RuneScape client window within the timeout period of " +
                        "${STEAM_WINDOW_TIMEOUT / 1000} seconds. Make sure that the client runs normally from steam.")
                exitProcess(0)
            }
            Thread.sleep(1L)
        }
    }

    companion object {

        /**
         * The number of milliseconds to wait for the OSRS Steam client window to be present before exiting the process.
         */
        private const val STEAM_WINDOW_TIMEOUT = 60 * 1000L

    }
}