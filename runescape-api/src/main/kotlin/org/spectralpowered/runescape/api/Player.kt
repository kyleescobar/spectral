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

package org.spectralpowered.runescape.api

import org.jire.kna.Addressed
import org.jire.kna.int
import org.spectralpowered.runescape.api.memory.osrs

class Player(override val address: Long) : Addressed {
    val x: Int get() = osrs.int(this.offset(0x10))
    val y: Int get() = osrs.int(this.offset(0x14))
    val rotation: Int get() = osrs.int(this.offset(0x18))
    val tick: Int get() = osrs.int(this.offset(0x24))
    val combatLevel: Int get() = osrs.int(this.offset(0x3D0))
    val headIconPvp: Int get() = osrs.int(this.offset(0x368))
    val headIconPrayer: Int get() = osrs.int(this.offset(0x36C))
}