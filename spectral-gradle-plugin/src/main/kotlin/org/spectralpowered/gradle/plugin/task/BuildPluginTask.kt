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

package org.spectralpowered.gradle.plugin.task

import org.gradle.api.tasks.TaskAction
import org.gradle.jvm.tasks.Jar
import org.spectralpowered.gradle.plugin.SpectralPluginConfigExtension

open class BuildPluginTask : Jar() {

    init {

        project.afterEvaluate {
            val extension = project.extensions.getByType(SpectralPluginConfigExtension::class.java)

            archiveBaseName.set("plugin-${extension.name}")
            archiveVersion.set(extension.version)
            archiveClassifier.set("")

            manifest {
                it.attributes.putAll(mapOf(
                    "Plugin-Name" to extension.name,
                    "Plugin-Version" to extension.version,
                    "Plugin-Author" to extension.author,
                    "Plugin-Class" to extension.mainClass
                ))
            }

            into("classes")
            into("jar") {
                it.from(project.configurations.getByName("pluginApi"))
            }
        }
    }

    @TaskAction
    override fun copy() {
        super.copy()
    }
}