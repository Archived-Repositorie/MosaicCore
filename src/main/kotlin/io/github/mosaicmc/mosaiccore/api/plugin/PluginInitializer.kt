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
package io.github.mosaicmc.mosaiccore.api.plugin

/**
 * PluginInitializer interface
 *
 * This interface should be implemented by plugins to perform initialization code when loaded by the
 * server. Plugins can provide their custom initialization logic by implementing the `onLoad`
 * method.
 */
fun interface PluginInitializer {

    /**
     * Called when the plugin is loaded by the server.
     *
     * @param plugin The container of the plugin containing information about the mod and the server
     *   instance.
     */
    fun onLoad(plugin: PluginContainer)
}
