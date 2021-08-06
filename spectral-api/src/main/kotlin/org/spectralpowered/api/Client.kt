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

package org.spectralpowered.api

import org.spectralpowered.api.type.LoginPage
import org.spectralpowered.runescape.api.RSClient

/**
 * Represents and holds values that are global to the RuneScape Client.
 */
object Client {

    /**
     * The delay in milliseconds between client game refreshes
     */
    const val REFRESH: Long = 1L

    /**
     * The currently displayed login screen / page for the client.
     *
     * Setting this to "LoginPage.LOGIN" will force a client login.
     */
    var loginPage: LoginPage
        get() = LoginPage.fromId(RSClient.loginState)
        set(value) { RSClient.loginState = value.id }

}