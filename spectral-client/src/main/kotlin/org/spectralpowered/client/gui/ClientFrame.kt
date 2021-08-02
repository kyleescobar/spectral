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

import org.spectralpowered.runescape.api.osrs
import org.spectralpowered.runescape.api.osrs_window
import org.tinylog.kotlin.Logger
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.ImageIcon
import javax.swing.JFrame

class ClientFrame : JFrame("Spectral") {

    private val nativeCanvas = NativeCanvas(osrs_window)

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT)
        preferredSize = size
        minimumSize = size
        iconImages = ICONS

        initNativeCanvas()

        /*
         * Register a frame close hook.
         */
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                /*
                 * Kill the task for the OSRS Steam client
                 */
                Runtime.getRuntime().exec("taskkill /F /PID ${osrs.id}")

                e.window.dispose()

                /*
                 * Release the native canvase.
                 */
                nativeCanvas.release()
            }
        })
    }

    private fun initNativeCanvas() {
        add(nativeCanvas)
    }

    fun open() {
        Logger.info("Opening the Spectral client frame.")

        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    fun close() {
        Logger.info("Closing the Spectral client frame.")

        isVisible = false
    }

    companion object {
        private const val DEFAULT_WIDTH = 800
        private const val DEFAULT_HEIGHT = 600

        private val ICONS = listOf(
            "/images/icons/icon16.png",
            "/images/icons/icon32.png",
            "/images/icons/icon64.png",
            "/images/icons/icon128.png",
            "/images/icons/icon256.png",
            "/images/icons/icon512.png",
        ).map { ClientFrame::class.java.getResource(it) }.map { ImageIcon(it).image }
    }
}