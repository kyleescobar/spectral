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

import org.spectralpowered.client.gui.ClientFrame
import org.spectralpowered.client.gui.theme.SpectralTheme
import org.spectralpowered.common.inject
import org.spectralpowered.runescape.api.attachOSRSProcess
import org.tinylog.kotlin.Logger
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.JDialog
import javax.swing.JFrame

class Spectral {

    private val frame: ClientFrame by inject()

    fun start() {
        Logger.info("Attaching JVM to the Old School RuneScape Steam client process.")

        /*
         * Attach to the Old School RuneScape Steam client process.
         */
        attachOSRSProcess()

        /*
         * Open the Spectral GUI.
         */
        this.openGui()
    }

    private fun openGui() {
        /*
         * Enable decorated windows.
         */
        JFrame.setDefaultLookAndFeelDecorated(true)
        JDialog.setDefaultLookAndFeelDecorated(true)

        /*
         * Install the Spectral LAF Theme
         */
        SpectralTheme.install()

        /*
         * Open the client frame.
         */
        frame.open()
    }

    companion object {

        /**
         * The data folder that Spectral stores components on your system.
         */
        val DATA_FOLDER: Path = Paths.get(System.getProperty("user.home")).resolve("spectral")
    }
}