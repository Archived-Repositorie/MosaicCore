@file:Suppress("KDocMissingDocumentation", "UNUSED")
package io.github.mosaicmc.mosaiccore.config.impl

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.mosaicmc.mosaiccore.config.ConfigData
import io.github.mosaicmc.mosaiccore.config.DataCoder
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * A data converter that converts configuration data to and from JSON.
 */
class SimpleJsonCoder : DataCoder<JsonObject> {
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

    override fun validateFile(file: File): Boolean = file.extension == extension

    override fun encodeToFile(data: JsonObject, file: File) {
        FileWriter(file).use { writer ->
            gson.toJson(data, writer)
        }
    }
}