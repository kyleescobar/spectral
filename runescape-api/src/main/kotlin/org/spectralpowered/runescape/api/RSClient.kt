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

object RSClient : Addressed {

    override var address: Long = 0L
        internal set

    var gameState: Int
        get() = osrs[address + 0x491D88]
        set(value) { osrs[address + 0x491D88] = value }

    /**
     * Controls the currently active login page.
     */
    var loginState: Int
        get() = osrs[address + 0x5C15AC]
        set(value) { osrs[address + 0x5C15AC] = value }

    val localPlayerIndexes get() = Array<Int>(2048) { osrs[address + 0x5BDB00 + it] }

    val localPlayers get() = Array<RSPlayer>(2048) { osrs[address + 0x1B32520 + it] }

}


