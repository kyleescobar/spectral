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

package org.spectralpowered.launcher

import org.koin.core.context.startKoin
import org.spectralpowered.client.Spectral
import org.spectralpowered.common.SPECTRAL_DATA_DIR
import org.spectralpowered.common.SteamUtil
import org.spectralpowered.common.get
import org.spectralpowered.runescape.api.hookSteamProcMemory

object Launcher {

    /**
     * Launches the Spectral client.
     */
    private fun launch() {
        Logger.info("Launching Spectral client.")

        /*
         * Start Dependency injector.
         */
        startKoin { modules(DI_MODULES) }

        /*
         * Start the OSRS Steam Client.
         */
        SteamUtil.startProcess()

        /*
         * Hook the Steam client memory.
         */
        hookSteamProcMemory()

        /*
         * Start the Spectral Client.
         */
        get<Spectral>().start()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        /*
         * Spectral pre-launch tasks.
         */
        this.checkDirs()

        /*
         * Set the required logger system properties.
         */
        System.setProperty("spectral.logs.dir", SPECTRAL_DATA_DIR.resolve("logs").toString())
        System.setProperty("spectral.logs.debug.level", "debug")
        System.setProperty("spectral.logs.level", "info")

        /*
         * Log an initialization message to verify the logger is working.
         */
        Logger.info("Initializing...")

        /*
         * Start the launch process.
         */
        launch()
    }

    private fun checkDirs() {
        listOf(
            "bin/",
            "cache/",
            "configs/",
            "logs/",
            "plugins/",
        ).map { SPECTRAL_DATA_DIR.resolve(it).toFile() }.forEach { dir ->
            if(!dir.exists()) {
                dir.mkdirs()
            }
        }
    }

}