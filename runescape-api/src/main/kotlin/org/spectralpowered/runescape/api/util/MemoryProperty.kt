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

package org.spectralpowered.runescape.api.util

import org.jire.arrowhead.get
import org.spectralpowered.runescape.api.osrs
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("ReplaceGetOrSet")
inline fun <reified T : Any> memory(address: Long) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return osrs.get(address) as T
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        when(T::class) {
            Byte::class -> osrs[address] = value as Byte
            Short::class -> osrs[address] = value as Short
            Int::class -> osrs[address] = value as Int
            Long::class -> osrs[address] = value as Long
            Float::class -> osrs[address] = value as Float
            Double::class -> osrs[address] = value as Double
            Boolean::class -> osrs[address] = value as Boolean
            Char::class -> osrs[address] = value as Char
            else -> throw IllegalArgumentException("Can not write non-primitive types to memory.")
        }
    }

}