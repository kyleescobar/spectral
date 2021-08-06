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

import org.jire.arrowhead.Module
import org.jire.arrowhead.Process
import org.jire.arrowhead.processByName
import org.slf4j.LoggerFactory
import org.spectralpowered.runescape.api.offset.ClientOffsets
import org.spectralpowered.runescape.api.util.every
import org.spectralpowered.runescape.api.util.retry

internal val Logger = LoggerFactory.getLogger("RuneScape")

internal lateinit var osrs: Process private set

internal lateinit var module: Module private set

/**
 * Attaches this JVM to a native running process exe. This
 * allows the JVM to interact with the memory of the process.
 */
fun attachNativeProcess(processName: String) {
    Logger.info("Attaching current JVM to native process: '$processName'...")

    retry(1L) {
        osrs = processByName(processName)!!
    }

    retry(1L) {
        osrs.loadModules()
        module = osrs.modules[processName]!!
    }

    Logger.info("Successfully attached JVM to the native process: '$processName'.")

    every(1000L) {
        println(ClientOffsets.dwLoginState.toULong().toString(16))
        println(ClientOffsets.dwGameState.toULong().toString(16))
    }
}