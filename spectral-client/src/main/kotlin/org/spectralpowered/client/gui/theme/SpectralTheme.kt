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

package org.spectralpowered.client.gui.theme

import com.formdev.flatlaf.IntelliJTheme
import org.tinylog.kotlin.Logger

class SpectralTheme : IntelliJTheme.ThemeLaf(Util.loadTheme("spectral")) {

    override fun getName(): String {
        return "Spectral Powered Theme"
    }

    companion object {

        fun install() {
            Logger.info("Installing the Spectral Powered Swing LAF theme.")
            setup(SpectralTheme())
        }

    }
}

private object Util {

    fun loadTheme(name: String): IntelliJTheme {
        return try {
            IntelliJTheme(Util::class.java.getResourceAsStream("/themes/$name.theme.json"))
        } catch(e : Exception) {
            throw RuntimeException(e)
        }
    }
}