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

package org.spectralpowered.common

import org.slf4j.LoggerFactory
import java.awt.Desktop
import java.net.URI
import kotlin.system.exitProcess

object SteamUtil {

    const val OSRS_APP_ID = 1343370
    const val OSRS_PROCESS_NAME = "osclient.exe"
    const val OSRS_WINDOW_TITLE = "Old School RuneScape"
    const val OSRS_WINDOW_CLASS = "JagWindow"

    private val Logger = LoggerFactory.getLogger("Steam")

    fun startProcess() {
        Logger.info("Starting Old School RuneScape Steam client...")

        try {
            val desktop = Desktop.getDesktop()
            val steamProtocol = URI("steam://run/$OSRS_APP_ID")
            desktop.browse(steamProtocol)

            /*
             * Check for 5 seconds for steam.exe to start. If it does not. We throw an error.
             * This is in cause some idiot thinks he can run Spectral without Steam installed.
             */
            var cycles = 0
            while(true) {
                if(cycles >= 5) {
                    Logger.error("Failed to launch Steam. Please make sure Steam and Old School RuneScape is installed on your system.")
                    exitProcess(0)
                }

                if(isProcessRunning("steam.exe")) {
                    Logger.info("Found Steam running on your system. Continuing to start.")
                    break
                }

                cycles++
                Thread.sleep(1L)
            }
        } catch (e : Exception) {
            Logger.error("Failed to start Old School RuneScape Steam client. Please verify you can run the game normally through Steam.", e)
            exitProcess(0)
        }

        Logger.info("Successfully launched Old School RuneScape Steam client.")
    }

    fun stopProcess() {
        Logger.info("Stopping the Old School RuneScape Steam client process.")

        if(!isProcessRunning(OSRS_PROCESS_NAME)) return

        Runtime.getRuntime().exec("taskkill /F /IM $OSRS_PROCESS_NAME")
    }

    private fun isProcessRunning(exeFile: String): Boolean {
        val processBuilder = ProcessBuilder("tasklist.exe")
        val process = processBuilder.start()
        val taskList = process.inputStream.bufferedReader(Charsets.UTF_8).use { reader ->
            return@use reader.readText()
        }
        return taskList.contains(exeFile)
    }

}