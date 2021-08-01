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
import java.awt.Color
import javax.swing.JToolBar
import javax.swing.SwingConstants

class IconPane : JToolBar() {

    init {
        orientation = SwingConstants.VERTICAL
        isFloatable = false
        background = Color(33, 37, 43)

        add(IconButton(FontAwesome.CHEVRON_RIGHT))
        add(IconButton(FontAwesome.PLUG))
        add(IconButton(FontAwesome.CLOUD_DOWNLOAD))
        add(IconButton(FontAwesome.CODE))
    }
}