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
import org.spectralpowered.client.ClientModule
import org.spectralpowered.client.Spectral
import org.spectralpowered.common.get
import org.spectralpowered.launcher.util.SteamUtil
import org.tinylog.kotlin.Logger
import java.nio.file.Files
import kotlin.system.exitProcess

object Launcher {

    /**
     * The Steam App ID For Old School RuneScape.
     */
    private const val STEAM_APP_ID = 1343370

    private val DI_MODULES = listOf(
        LauncherModule,
        ClientModule
    )

    /**
     * Starts the launch process for the Spectral Client.
     */
    fun launch() {
        Logger.info("Launching Spectral client...")

        /*
         * Check and create default Spectral Data Folder.
         */
        this.checkDirs()

        /*
         * Launch the OSRS Steam Client.
         */
        Logger.info("Launching Old School RuneScape Steam client.")

        if(!SteamUtil.launchGame(STEAM_APP_ID)) {
           Logger.error("Failed to launch Old School RuneScape from Steam. Exiting process.")
           exitProcess(1)
        }

        /*
         * Start the Spectral Client.
         */
        get<Spectral>().start()
    }

    private fun checkDirs() {
        val dirs = listOf(
            "bin",
            "configs",
            "logs",
            "plugins",
            "cache",
        ).map { Spectral.DATA_FOLDER.resolve(it) }

        dirs.forEach { dir ->
            if(!Files.exists(dir)) {
                Logger.info("Creating data sub-directory: '$dir'.")
                Files.createDirectories(dir)
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        /*
         * Start Dependency Injector
         */
        startKoin { modules(DI_MODULES) }

        /*
         * Launch Spectral
         */
        this.launch()
    }

}