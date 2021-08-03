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
import org.jire.kna.set
import org.spectralpowered.runescape.api.memory.osrs

lateinit var client: Client internal set

class Client(override val address: Long) : Addressed {

    var gameState: Int
        get() = osrs.int(this.offset(0x491D88))
        set(value) { osrs[this.offset(0x491D88)] = value }

    var loginState: Int
        get() = osrs.int(this.offset(0x5C15AC))
        set(value) { osrs[this.offset(0x5C15AC)] = value }

    val localPlayer: Player = PlayerPointer(this.offset(0x1B3A520)).player

}