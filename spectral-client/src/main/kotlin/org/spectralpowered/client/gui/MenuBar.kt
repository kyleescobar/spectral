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

import jiconfont.icons.font_awesome.FontAwesome
import jiconfont.swing.IconFontSwing
import org.spectralpowered.client.Spectral
import org.spectralpowered.common.inject
import javax.swing.JButton
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class MenuBar : JMenuBar() {

    private val spectral: Spectral by inject()

    init {
        JMenu("File").also { menu ->
            JMenuItem("Exit").also {
                it.addActionListener {
                    spectral.window.close()
                }
                menu.add(it)
            }
            add(menu)
        }

        JMenu("Tools").also { menu ->
            add(menu)
        }

        JMenu("Plugins").also { menu ->
            add(menu)
        }

        JMenu("Help").also { menu ->
            JMenuItem("About").also {
                menu.add(it)
            }
            add(menu)
        }
    }

}