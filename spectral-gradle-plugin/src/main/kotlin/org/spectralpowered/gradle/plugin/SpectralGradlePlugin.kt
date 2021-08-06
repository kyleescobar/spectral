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

package org.spectralpowered.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.spectralpowered.gradle.plugin.task.BuildPluginTask
import java.nio.file.Paths

open class SpectralGradlePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val pluginExtension = project.extensions.create("configurePlugin", SpectralPluginConfigExtension::class.java)
        val spectralExtension = project.extensions.create("spectral", SpectralConfigExtension::class.java)

        project.configurations.create("pluginApi") {
            it.isTransitive = false
            it.isCanBeResolved = true
            it.isCanBeConsumed = true
        }

        /*
         * Add default dependencies
         */
        project.dependencies.apply {
            add("implementation", "org.spectralpowered:spectral-plugin:${spectralExtension.version}")
            add("implementation", "org.spectralpowered:spectral-api:${spectralExtension.version}")
        }

        /*
         * Register Tasks
         */

        project.tasks.create("buildPlugin", BuildPluginTask::class.java) {
            it.group = "build"
        }

        project.tasks.create("installPlugin", Copy::class.java) {
            it.group = "build"
            it.from(project.tasks.named("buildPlugin"))
            it.into(SPECTRAL_PLUGIN_DIR.toAbsolutePath().toString())
        }

        project.tasks.getByName("build").dependsOn(project.tasks.getByName("installPlugin"))
    }

    companion object {
        val SPECTRAL_PLUGIN_DIR = Paths.get(System.getProperty("user.home"))
            .resolve("spectral")
            .resolve("plugins")
    }
}