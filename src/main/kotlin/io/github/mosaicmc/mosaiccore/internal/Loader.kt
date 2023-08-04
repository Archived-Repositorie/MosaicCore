/*
 * Copyright (c) 2023. JustFoxx
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.mosaicmc.mosaiccore.internal

import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.api.plugin.PluginInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer

/**
 * Singleton object that manages plugins for a Minecraft server.
 *
 * This object provides functionality to load and retrieve plugins for a Minecraft server. Plugins
 * are loaded from the FabricLoader's entrypoint containers, and PluginInitializer classes are used
 * to initialize the plugins.
 *
 * @property plugins A list of loaded PluginContainers representing the currently loaded plugins.
 */
object Loader {
    /**
     * A list of loaded plugins.
     *
     * This property holds a list of PluginContainer objects representing the currently loaded
     * plugins for the Minecraft server. The list is initially empty and gets populated once the
     * plugins are loaded using the [loadPlugins] function.
     */
    var plugins: List<PluginContainer> = emptyList()
        private set

    /**
     * Loads the plugins for the Minecraft server.
     *
     * This function loads plugins by querying the FabricLoader for entrypoint containers of the
     * specified type ("plugin" with PluginInitializer class). It initializes each plugin in
     * parallel and stores them in the [plugins] list.
     *
     * @param server The MinecraftServer instance to be passed to each plugin's PluginInitializer.
     */
    internal fun loadPlugins(server: MinecraftServer): List<PluginContainer> {
        plugins =
            FabricLoader.getInstance()
                .getEntrypointContainers("plugin", PluginInitializer::class.java)
                .parallelStream()
                .map {
                    val pluginContainer = PluginContainer(it.provider, server)
                    it.entrypoint.onLoad(pluginContainer)
                    pluginContainer
                }
                .toList()
        return plugins
    }
}
