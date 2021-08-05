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
    `maven-publish`
}

dependencies {
    implementation(project(":common"))
    implementation(project(":logger"))
    api("org.jire.arrowhead:arrowhead:_")
    api("net.java.dev.jna:jna:_")
    api("net.java.dev.jna:jna-platform:_")
    api("io.reactivex.rxjava3:rxjava:_")
    api("io.reactivex.rxjava3:rxkotlin:_")
    api("com.jakewharton.rxrelay2:rxrelay:_")
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = "org.spectralpowered"
            artifactId = "runescape-api"
            version = "${project.version}"
            from(components["java"])
        }
    }
}