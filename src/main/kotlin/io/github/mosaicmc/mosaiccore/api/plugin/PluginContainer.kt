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
import net.minecraft.server.MinecraftServer

/**
 * PluginContainer data class
 *
 * Represents a container for a plugin, containing information such as the mod container and the
 * Minecraft server instance.
 *
 * @property modContainer Represents the mod container of the plugin.
 * @property server The Minecraft server instance associated with the plugin.
 */
data class PluginContainer(
    val modContainer: ModContainer,
    val server: MinecraftServer,
)
