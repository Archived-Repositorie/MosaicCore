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
package io.github.mosaicmc.mosaiccore.plugin

import net.fabricmc.loader.api.ModContainer
import net.fabricmc.loader.api.metadata.ModMetadata
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Plugin represents a plugin.
 *
 * @property modContainer Represents the mod of the plugin
 * @property name The name of the plugin.
 * @property server The server.
 * @property metadata The metadata of the plugin
 * @property logger The logger.
 */
data class PluginContainer(
    val modContainer: ModContainer,
    val server: MinecraftServer,
    val metadata: ModMetadata = modContainer.metadata,
    val name: String = metadata.id,
    val logger: Logger = LoggerFactory.getLogger(name),
) {
    /**
     * Gets the identifier for a resource.
     * @param path The path to the resource.
     * @return The identifier for the resource.
     */
    fun idOf(path: String): Identifier = Identifier(name, path)
}

