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

package org.spectralpowered.runescape.api.offset

import org.spectralpowered.runescape.api.ext.invoke
import org.spectralpowered.runescape.api.module
import org.spectralpowered.runescape.api.util.get

internal object ClientOffsets {

    val dwGameState by module(2, 8)(0xC7, 0x05, 0[4], 0x02, 0x00, 0x00, 0x00, 0xEB, 0, 0x48, 0x8D, 0x8B, 0[4])

    val dwLoginState by module(2, 4)(0x8B, 0x0D, 0[4], 0x85, 0xC9, 0x0F, 0x85, 0[4])

}