package io.github.mosaicmc.mosaiccore.config

import com.google.gson.JsonObject
import io.github.mosaicmc.mosaiccore.utils.getDefaultOrWriteJsonObject
import io.github.mosaicmc.mosaiccore.utils.readJsonObject
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * Config loader is class with helper functions for loading and creating config files
 * Uses json
 */
object ConfigLoader {
    /**
     * Load or create config file
     *
     * @param T The type of the config object
     * @param path The path to the config file
     * @param configObject The config default object
     * @return A pair of the config object and the config json object
     */
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
        return configPath.getDefaultOrWriteJsonObject(
            configObject
        )
    }
}