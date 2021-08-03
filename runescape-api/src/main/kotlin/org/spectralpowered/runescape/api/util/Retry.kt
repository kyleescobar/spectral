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

/**
 * Runs an action and if it fails, it waits for a set delay time to pass before attempting again.
 */
inline fun <T> retry(delay: Long = 1000L, noinline exceptionHandler: ((Throwable) -> Unit)? = null, action: () -> T) {
    while(!Thread.interrupted()) {
        try {
            action()
            break
        } catch(e : Throwable) {
            exceptionHandler?.invoke(e)
            Thread.sleep(delay)
        }
    }
}