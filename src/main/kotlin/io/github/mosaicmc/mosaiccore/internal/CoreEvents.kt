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

import io.github.mosaicmc.mosaiccore.api.plugin.metadata
import io.github.mosaicmc.mosaiccore.api.plugin.name
import net.minecraft.server.MinecraftServer

/**
 * CoreEvents object
 *
 * The `CoreEvents` object contains functions related to core event handling in the plugin loader.
 */
internal object CoreEvents {

    /**
     * Plugin Loader function
     *
     * The `pluginLoader` function is responsible for loading plugins in the Minecraft server. It
     * calls the `Loader.loadPlugins` method to load the plugins and then logs information about the
     * loaded plugins.
     *
     * @param server The Minecraft server instance where the plugins will be loaded.
     */
    internal fun pluginLoader(server: MinecraftServer) =
        Loader.loadPlugins(server).forEach {
            logger.info("Loaded ${it.name}:${it.metadata.version}")
        }
}
