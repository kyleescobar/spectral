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
import org.spectralpowered.runescape.api.util.osrs

object Client : Addressed {

    override var address: Long = 0L
        internal set

    var loginState: Int by osrs(0x5C15AC)
}

private operator fun <T, V> ReadWriteProperty<T, V>.setValue(t: T, property: KProperty<V?>, v: V) {

}

private operator fun <T, V> ReadWriteProperty<T, V>.getValue(t: T, property: KProperty<V?>): V {

}


