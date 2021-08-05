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
import org.jire.arrowhead.get
import org.jire.arrowhead.processByName
import org.slf4j.LoggerFactory
import org.spectralpowered.common.SteamUtil
import org.spectralpowered.runescape.api.util.every
import org.spectralpowered.runescape.api.util.property.MemoryValue
import org.spectralpowered.runescape.api.util.retry

internal val memoryObservers = mutableListOf<MemoryValue<*>>()

lateinit var osrs: Process private set

internal lateinit var moduleBase: Module private set

/**
 * Hooks the current JVM process into the Old School RuneScape Steam client process.
 * This allows for the JVM to freely read and write values in memory.
 */
fun hookSteamProcMemory() {
    val logger = LoggerFactory.getLogger("RuneScape")

    /*
     * Attach / hook to the memory process.
     */
    logger.info("Attempting to hook the Old School RuneScape Steam client process.")

    retry(100L) {
        osrs = processByName(SteamUtil.OSRS_PROCESS_NAME)!!

        /*
         * Load the modules of the hooked process.
         */
        osrs.loadModules()
        moduleBase = osrs.modules[SteamUtil.OSRS_PROCESS_NAME]!!
        RSClient.address = moduleBase.address
    }

    logger.info("Successfully hooked native process memory.")

    /*
     * Start watching memory for each memory observer.
     */
    every(1L) {
        RSClient.gameState = osrs[RSClient.address + 0x490D88]
        RSClient.loginState = osrs[RSClient.address + 0x5C05AC]
    }
}


