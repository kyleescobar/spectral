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

import org.koin.core.context.startKoin
import org.spectralpowered.client.gui.ClientWindow
import org.spectralpowered.client.gui.theme.SpectralTheme
import org.spectralpowered.common.get
import org.spectralpowered.runescape.api.OSRS
import org.tinylog.kotlin.Logger
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.JDialog
import javax.swing.JFrame

class Spectral {

    lateinit var window: ClientWindow private set

    fun start() {
        Logger.info("Attaching JVM to the Old School RuneScape Steam client process.")

        /*
         * Attach to the Old School RuneScape Steam client process.
         */
        OSRS.attachClientProcess()

        /*
         * Enable decorated windows.
         */
        JFrame.setDefaultLookAndFeelDecorated(true)
        JDialog.setDefaultLookAndFeelDecorated(true)

        /*
         * Open the Spectral client window.
         */
        SpectralTheme.install()

        window = ClientWindow()
        window.open()
    }

    companion object {

        /**
         * The data folder that Spectral stores components on your system.
         */
        val DATA_FOLDER: Path = Paths.get(System.getProperty("user.home")).resolve("spectral")

        private val DI_MODULES = listOf(
            ClientModule
        )

        @JvmStatic
        fun main(args: Array<String>) {
            /*
             * Start Dependency Injector
             */
            startKoin { modules(DI_MODULES) }

            /*
             * Start the Spectral Client.
             */
            get<Spectral>().start()
        }
    }
}