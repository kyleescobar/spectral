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

package org.spectralpowered.plugin

import org.slf4j.LoggerFactory
import org.spectralpowered.common.SPECTRAL_DATA_DIR
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import java.util.jar.JarFile

class PluginManager {

    private val plugins = mutableMapOf<SpectralPlugin, Boolean>()

    @Suppress("UNCHECKED_CAST")
    fun loadPlugins() {
        Logger.info("Loading Spectral client Plugins...")

        val pluginJars = mutableListOf<File>()

        /*
         * Scan the plugin directory.
         */
        Files.walk(SPECTRAL_DATA_DIR.resolve("plugins")).forEach {
            if(Files.isDirectory(it) || it.toFile().extension != "jar") {
                return@forEach
            }
            pluginJars.add(it.toFile())
        }

        Logger.info("Found ${pluginJars.size} jar files in plugin folder.")

        val classloader = URLClassLoader(pluginJars.map { it.toURI().toURL() }.toTypedArray())

        /*
         * Load classes within each jar file.
         */
        pluginJars.forEach {
            try {
                val jar = JarFile(it)
                val manifest = jar.manifest ?: throw IllegalStateException("No manifest found for jar: ${it.path}.")

                val pluginName = manifest.mainAttributes.getValue("Plugin-Name")
                val pluginVersion = manifest.mainAttributes.getValue("Plugin-Version")
                val pluginAuthor = manifest.mainAttributes.getValue("Plugin-Author")
                val pluginMainClass = manifest.mainAttributes.getValue("Plugin-Class")

                val pluginClass = classloader.loadClass(pluginMainClass) as Class<SpectralPlugin>
                val pluginInst = pluginClass.getDeclaredConstructor().newInstance()

                /*
                 * Set the plugin instance attributes.
                 */
                pluginInst.name = pluginName
                pluginInst.author = pluginAuthor
                pluginInst.version = pluginVersion

                /*
                 * Store the plugin instance as disabled.
                 */
                plugins[pluginInst] = false

            } catch (e : Exception) {
                Logger.error("Failed to load plugin from JAR file. [jar: ${it.path}].", e)
                return@forEach
            }

            plugins.forEach { (plugin, _) ->
                enablePlugin(plugin)
            }

            Logger.info("Loaded ${plugins.size} Spectral client plugins.")
        }
    }

    fun unloadPlugins() {
        Logger.info("Unloading all Spectral client plugins...")

        plugins.filter { it.value }.keys.forEach { plugin ->
            disablePlugin(plugin)
        }

        /*
         * Clear the plugin map.
         */
        plugins.clear()
    }

    fun reloadPlugins() {
        /*
         * Unload all plugins.
         */
        this.unloadPlugins()

        /*
         * Load all plugins.
         */
        this.loadPlugins()
    }

    fun enablePlugin(plugin: SpectralPlugin): Boolean {
        if(!plugins.containsKey(plugin)) {
            Logger.error("The plugin supplied was not found in the list of loaded plugins.")
            return false
        }

        if(plugins[plugin]!!) {
            Logger.error("The plugin supplied is already enabled.")
            return false
        }

        plugin.onEnable()
        plugins[plugin] = true

        Logger.info("Enabled plugin: [name: ${plugin.name}, version: ${plugin.version}].")

        return true
    }

    fun disablePlugin(plugin: SpectralPlugin): Boolean {
        if(!plugins.containsKey(plugin)) {
            Logger.error("The plugin supplied was not found in the list of loaded plugins.")
            return false
        }

        if(!plugins[plugin]!!) {
            Logger.error("The plugin supplied is already disabled.")
            return false
        }

        plugin.onDisable()
        plugins[plugin] = false

        Logger.info("Enabled plugin: [name: ${plugin.name}, version: ${plugin.version}].")

        return true
    }

    fun isEnabled(plugin: SpectralPlugin): Boolean = plugins.containsKey(plugin)

}