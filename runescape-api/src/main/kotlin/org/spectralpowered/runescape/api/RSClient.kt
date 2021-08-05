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

import org.jire.arrowhead.Addressed
import org.jire.arrowhead.get
import org.spectralpowered.runescape.api.util.property.MemoryValue

object RSClient : Addressed {

    override var address: Long = 0L
        internal set

    var gameState: Int
        get() = osrs[address + 0x490D88]
        set(value) { osrs[address + 0x490D88] = value }

    var loginState: Int
        get() = osrs[address + 0x5C05AC]
        set(value) { osrs[address + 0x5C05AC] = value }

}


