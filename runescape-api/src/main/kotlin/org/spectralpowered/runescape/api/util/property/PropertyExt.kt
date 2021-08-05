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

package org.spectralpowered.runescape.api.util.property

import io.reactivex.Observable
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

@Suppress("UNCHECKED_CAST")
fun <T : Any> KProperty0<T>.onChanged(): Observable<T> {
    isAccessible = true

    val delegate = this.getDelegate()
    return delegate?.let {
        (it as MemoryValue<*>).changed as Observable<T>
    } ?: Observable.just(this as T)
}