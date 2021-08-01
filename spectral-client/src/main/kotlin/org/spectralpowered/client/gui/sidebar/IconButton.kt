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

import jiconfont.icons.font_awesome.FontAwesome
import jiconfont.swing.IconFontSwing
import java.awt.Color
import java.awt.Dimension
import javax.swing.JButton

class IconButton(icn: FontAwesome) : JButton() {

    init {
        IconFontSwing.register(FontAwesome.getIconFont())
        icon = IconFontSwing.buildIcon(icn, 20f, Color(151, 159, 173))
        size = Dimension(Int.MAX_VALUE, preferredSize.height)
    }
}