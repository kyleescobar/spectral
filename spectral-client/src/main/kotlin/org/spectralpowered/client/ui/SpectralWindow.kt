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

package org.spectralpowered.client.ui

import com.sun.jna.platform.win32.WinDef
import org.spectralpowered.client.Spectral
import org.spectralpowered.common.inject
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.ImageIcon
import javax.swing.JFrame

class SpectralWindow : JFrame("Spectral") {

    private val spectral: Spectral by inject()

    private lateinit var nativeGameShell: NativeGameShell

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(INIT_WIDTH, INIT_HEIGHT)
        preferredSize = size
        minimumSize = Dimension(MIN_WIDTH, MIN_HEIGHT)
        iconImages = APP_ICONS
        layout = BorderLayout()
        setLocationRelativeTo(null)
    }

    fun open(window: WinDef.HWND) {
        this.isVisible = true

        /*
         * Create and add the native game shell. Auto attach the client.
         */
        nativeGameShell = NativeGameShell(window)

        nativeGameShell.setSize(width, height)
        add(nativeGameShell, BorderLayout.CENTER)

        /*
         * Add window shutdown hook
         */
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                nativeGameShell.release()
                isVisible = false
                spectral.stop()
            }
        })
    }

    companion object {
        private const val INIT_WIDTH = 800
        private const val INIT_HEIGHT = 600
        private const val MIN_WIDTH = 765
        private const val MIN_HEIGHT = 503

        /*
         * The various sized icons to use for the Spectral client application icon.
         */
        private val APP_ICONS = listOf(
            "/images/app/icon16.png",
            "/images/app/icon32.png",
            "/images/app/icon64.png",
            "/images/app/icon128.png",
            "/images/app/icon256.png",
            "/images/app/icon512.png",
            "/images/app/icon1024.png"
        ).map { SpectralWindow::class.java.getResource(it) }.map { ImageIcon(it).image }
    }
}