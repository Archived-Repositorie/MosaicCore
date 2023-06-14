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

package io.github.mosaicmc.mosaiccore.deprecated.config.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.mosaicmc.mosaiccore.deprecated.config.ConfigData
import io.github.mosaicmc.mosaiccore.deprecated.config.JsonCoder
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/** A data converter that converts configuration data to and from JSON. */
@Deprecated("Gonna be moved into different library", level = DeprecationLevel.WARNING)
class SimpleJsonCoder : JsonCoder {
    override val default: JsonObject = JsonObject()
    override val extension: String = "json"

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    override fun <O : ConfigData> convertObject(data: O): JsonObject {
        val json = gson.toJson(data)
        return JsonParser.parseString(json).asJsonObject
    }

    override fun <O : ConfigData> convertToObject(data: JsonObject, clazz: Class<O>): O {
        return gson.fromJson(data, clazz)
    }

    override fun decodeFile(file: File): JsonObject {
        return JsonParser.parseReader(FileReader(file)).asJsonObject
    }

    override fun encodeToFile(data: JsonObject, file: File) {
        FileWriter(file).use { writer -> gson.toJson(data, writer) }
    }
}
