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

import org.jire.kna.attach.Attach
import org.jire.kna.attach.windows.WindowsAttachAccess
import org.jire.kna.attach.windows.WindowsAttachedModule
import org.jire.kna.attach.windows.WindowsAttachedProcess
import org.slf4j.LoggerFactory
import org.spectralpowered.runescape.api.util.retry

internal val Logger = LoggerFactory.getLogger("RuneScape")

lateinit var osrs: WindowsAttachedProcess private set
lateinit var osrsModule: WindowsAttachedModule private set

/**
 * Attaches this JVM to a native running process exe. This
 * allows the JVM to interact with the memory of the process.
 */
fun attachNativeProcess(processName: String) {
    Logger.info("Attaching current JVM to native process: '$processName'...")

    /*
     * Attempt to attach the current JVM to the process exe.
     */
    retry(100L) {
        osrs = Attach.byName(processName, WindowsAttachAccess.All) {} as WindowsAttachedProcess
    }

    /*
     * Attempt to locate the attached process module base.
     */
    retry(100L) {
        osrs.modules.attach(osrs)
        osrsModule = osrs.modules.byName(processName) as WindowsAttachedModule
    }

    Logger.info("Successfully attached JVM to the native process: '$processName'.")
}