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

import org.spectralpowered.client.Spectral
import org.spectralpowered.common.inject
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JSeparator

class MenuBar : JMenuBar() {

    private val spectral: Spectral by inject()

    init {
        /**
         * File Menu
         */
        JMenu("File").also { menu ->
            JMenuItem("New Tab").also {
                menu.add(it)
            }

            JMenuItem("Close Tab").also {
                menu.add(it)
            }

            JSeparator().also {
                menu.add(it)
            }

            JMenuItem("Exit").also {
                it.addActionListener {
                    /*
                     * Close the Spectral Client.
                     */
                    spectral.stop()
                }
                menu.add(it)
            }

            add(menu)
        }

        /**
         * Edit Menu
         */
        JMenu("Edit").also { menu ->
            JMenuItem("Preferences").also {
                menu.add(it)
            }

            add(menu)
        }

        /**
         * View Menu
         */
        JMenu("View").also { menu ->
            JMenu("SubViews").also { submenu ->
                JMenuItem("Pinned Plugin Tool Bar").also {
                    submenu.add(it)
                }
            }
        }
    }

}