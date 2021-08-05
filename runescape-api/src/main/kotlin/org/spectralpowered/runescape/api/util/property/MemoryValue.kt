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

@file:Suppress("UNCHECKED_CAST")

package org.spectralpowered.runescape.api.util.property

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import org.jire.arrowhead.get
import org.spectralpowered.runescape.api.memoryObservers
import org.spectralpowered.runescape.api.osrs
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MemoryValue<T : Number>(val address: Long, default: T) : ReadWriteProperty<Any, T> {

    private val lock = Any()

    private val signal = BehaviorRelay.createDefault(default)

    val changed: Observable<T>? get() = signal.hide()

    init {
        memoryObservers.add(this)
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return synchronized(lock) {
            signal.value!!
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        synchronized(lock) {
            when(value) {
                is Byte -> osrs[address] = value
                is Short -> osrs[address] = value
                is Int -> osrs[address] = value
                is Float -> osrs[address] = value
                is Double -> osrs[address] = value
                is Long -> osrs[address] = value
                else -> {}
            }
            signal.accept(value)
        }
    }
}