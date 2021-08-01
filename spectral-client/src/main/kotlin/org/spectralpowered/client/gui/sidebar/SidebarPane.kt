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

package org.spectralpowered.client.gui.sidebar

import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class SidebarPane : JPanel() {

    private val iconPane = IconPane()

    private var collapsed = true

    init {
        border = EmptyBorder(0, 0, 0, 0)
        layout = GridLayout()
        initIconPane()
    }

    private fun initIconPane() {
        add(iconPane)
    }

    fun isCollapsed(): Boolean = collapsed

    fun collapse() {
        if(collapsed) return
        collapsed = true
    }

    fun extend() {
        if(!collapsed) return
        collapsed = false
    }

    companion object {
        private const val COLLAPSED_WIDTH = 64
        private const val EXTENDED_WIDTH = 256
    }
}