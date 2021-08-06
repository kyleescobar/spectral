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

package org.spectralpowered.api.type

/**
 * Represents the various views the client's login screen can display.
 */
enum class LoginPage(val id: Int) {
    STEAM(0),
    NORMAL(2),
    INVALID_CREDENTIALS(3),
    AUTHENTICATOR(4),
    FORGOT_PASSWORD(5),
    SET_DATE_OF_BIRTH(7),
    TRY_AGAIN(9),
    MOBILE_PLAY(10),
    MOBILE_SIGN_OUT(11),
    MOBILE_TOS(12),
    MOBILE_ACCEPT_TOS(13),
    ACCOUNT_DISABLED(14),
    APPLY_OR_SKIP(16),
    RETRY_OR_SKIP(19),
    LOGIN(20),
    MOBILE_DISCLAIMER(21),
    MOBILE_UPDATE_APP(22),
    MOBILE_GOOGLE_ERROR(23),
    STEAM_PLAY(26),
    STEAM_ACCOUNT_DISCLAIMER(28),
    STEAM_CONNECT_ERROR(29),
    UNKNOWN(-1);

    companion object {

        val values = enumValues<LoginPage>()

        /**
         * Gets a [LoginPage] for a given id.
         */
        fun fromId(id: Int): LoginPage {
            return values.firstOrNull { it.id == id } ?: UNKNOWN
        }
    }
}