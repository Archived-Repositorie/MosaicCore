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
package io.github.mosaicmc.mosaiccore.deprecated.config

import java.io.File

@Deprecated("Gonna be moved into different library", level = DeprecationLevel.WARNING)
/**
 * Extended a config key
 *
 * Key which stores data about a config file.
 * @param O Config data
 * @param T Coder object
 * @property file The file
 * @property data The data
 * @property coderObject The coder object
 */
data class ExtendedConfigKey<O, T>(
    val file: File,
    val data: O,
    val coderObject: T
) where O : ConfigData
