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
@file:Suppress("KDocMissingDocumentation", "UNUSED")

package io.github.mosaicmc.mosaiccore.config

import java.io.File

interface DataCoder<T> {
    val extension: String
    val default: T

    fun <O : ConfigData> convertObject(data: O): T

    fun <O : ConfigData> convertToObject(data: T, clazz: Class<O>): O

    fun decodeFile(file: File): T

    fun encodeToFile(data: T, file: File)

    fun validateFile(file: File): Boolean = file.extension == extension
}
