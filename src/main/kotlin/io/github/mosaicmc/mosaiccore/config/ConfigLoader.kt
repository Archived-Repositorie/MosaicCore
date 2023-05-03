package io.github.mosaicmc.mosaiccore.config

import com.google.gson.JsonObject
import io.github.mosaicmc.mosaiccore.utils.getDefaultJsonObject
import io.github.mosaicmc.mosaiccore.utils.readJsonObject
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

object ConfigLoader {
    @JvmStatic fun <T> loadOrCreateConfigFile(path: Path, configObject: T? = null): Pair<Optional<T & Any>, JsonObject> {
        val configPath = FabricLoader.getInstance().configDir.resolve(path)

        val configJsonObject: JsonObject = if (Files.notExists(configPath)) {
            createConfigFile(configPath, configObject)
        } else {
            configPath.readJsonObject()
        }


        return if (configObject != null)
            Pair(Optional.ofNullable(configObject), configJsonObject)
        else
            Pair(Optional.empty(), configJsonObject)
    }

    private fun createConfigFile(configPath: Path, configObject: Any? = null): JsonObject {
        Files.createFile(configPath)
        return configPath.getDefaultJsonObject(
            configObject
        )
    }
}