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
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.ImageIcon
import javax.swing.JFrame

class ClientWindow : JFrame("Spectral") {

    val gameContainer = GameContainer(osrs_window)

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()
        iconImages = APP_ICONS

        initGameContainer()

        /*
         * Register close listener
         */
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                Logger.info("Shutting down Spectral client.")

                /*
                 * Terminate the Steam client process along with the current JVM process.
                 */
                Runtime.getRuntime().exec("taskkill /F /PID ${osrs.id}")
                e.window.dispose()
            }
        })
    }

    private fun initGameContainer() {
        gameContainer.size = Dimension(MIN_WIDTH, MIN_HEIGHT)
        gameContainer.preferredSize = gameContainer.size
        gameContainer.isVisible = true
        contentPane.add(gameContainer, BorderLayout.CENTER)
    }

    fun open() {
        Logger.info("Opening Spectral client window.")

        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    fun close() {
        Logger.info("Closing Spectral client window.")

        isVisible = false
    }

    companion object {

        private const val MIN_WIDTH = 800
        private const val MIN_HEIGHT = 600

        /**
         * A list of various sized application icons to be used for the Spectral client.
         */
        private val APP_ICONS = listOf(
            "/images/icons/icon16.png",
            "/images/icons/icon32.png",
            "/images/icons/icon64.png",
            "/images/icons/icon128.png",
            "/images/icons/icon256.png",
            "/images/icons/icon512.png",
            "/images/icons/icon1024.png",
        ).map { ClientWindow::class.java.getResource(it) }.map { ImageIcon(it).image }
    }
}