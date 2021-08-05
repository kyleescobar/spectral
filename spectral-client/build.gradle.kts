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

plugins {
    id("org.openjfx.javafxplugin") version("0.0.9")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":logger"))
    implementation(project(":spectral-plugin"))
    implementation("com.formdev:flatlaf:_")
    implementation("com.formdev:flatlaf-intellij-themes:_")
    implementation("net.java.dev.jna:jna:_")
    implementation("net.java.dev.jna:jna-platform:_")
    implementation("no.tornado:tornadofx:_")
}

javafx {
    version = "11"
    modules = listOf("javafx.controls", "javafx.graphics", "javafx.swing")
}