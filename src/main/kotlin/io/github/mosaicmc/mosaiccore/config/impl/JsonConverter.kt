package io.github.mosaicmc.mosaiccore.config.impl

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import io.github.mosaicmc.mosaiccore.config.ConfigObject
import io.github.mosaicmc.mosaiccore.config.DataConverter
import java.io.FileReader
import java.nio.file.Path

class JsonConverter : DataConverter<JsonObject> {
    override val default: JsonObject = JsonObject()
    override val extension: String = "json"

    override fun <Object : ConfigObject> convertObject(data: Object): JsonObject {
        return gson.toJsonTree(data).asJsonObject
    }

    override fun parseData(string: String): JsonObject {
        return gson.fromJson(string, JsonObject::class.java)
    }

    override fun convertToString(data: JsonObject): String = gson.toJson(data)

    override fun parseData(path: Path): JsonObject {
        val file = path.toFile()
        val reader = JsonReader(FileReader(file))
        return JsonParser.parseReader(reader).asJsonObject
    }

    companion object {
        private val gson = GsonBuilder().setPrettyPrinting().create()
    }
}