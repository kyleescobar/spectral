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

package org.spectralpowered.api.util

import org.spectralpowered.runescape.api.util.every

class Await(
    val clause: Boolean,
    val recheck: Long = 1L,
    val predicate: () -> Boolean
) {

    inline operator fun invoke(
        clause: Boolean = this.clause,
        recheck: Long = this.recheck,
        crossinline action: () -> Unit
    ) {
        if(!clause) every(recheck) {
            if(predicate()) {
                action()
            }
        } else if(predicate()) {
            action()
        }
    }
}

fun await(recheck: Long = 1L, clause: Boolean = false, until: () -> Boolean) = Await(clause, recheck, until)