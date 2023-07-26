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
@file:Suppress("unused")

package io.github.mosaicmc.mosaiccore.api.plugin

import net.fabricmc.loader.api.ModContainer
import net.fabricmc.loader.api.metadata.ModMetadata
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Plugin represents a plugin.
 *
 * @property modContainer Represents the mod of the plugin
 * @property server The server.
 */
data class PluginContainer(
    val modContainer: ModContainer,
    val server: MinecraftServer,
)

/**
 * Gets the resource location.
 *
 * @param path The path to the resource.
 * @return The resource location.
 */
infix fun PluginContainer.resource(path: String): ResourceLocation = ResourceLocation(name, path)

/**
 * Gets the metadata of the plugin.
 *
 * @return The metadata of the plugin
 */
val PluginContainer.metadata: ModMetadata
    get() = this.modContainer.metadata

/**
 * Gets the (logger) of the plugin
 *
 * @return The logger
 */
val PluginContainer.logger: Logger
    get() = LoggerFactory.getLogger(name)

/**
 * Gets the name of the plugin.
 *
 * @return The name of the plugin
 */
val PluginContainer.name: String
    get() = metadata.id
