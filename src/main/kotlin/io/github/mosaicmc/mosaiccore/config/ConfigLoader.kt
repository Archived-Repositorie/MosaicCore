package io.github.mosaicmc.mosaiccore.config

import io.github.mosaicmc.mosaiccore.config.impl.JsonConverter
import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * A generic configuration loader that loads configuration data from a file on disk or creates a new one if it doesn't
 * exist yet.
 *
 * @param dataConverter the data converter that converts the configuration data between its serialized and deserialized forms
 */
class ConfigLoader<T>(private val dataConverter: DataConverter<T>) {
    /**
     * Loads or creates a configuration file with the specified [name] for the given [PluginContainer].
     *
     * @param plugin the plugin container that identifies the plugin to load the configuration for
     * @param configObject an optional configuration object to use if no file exists yet
     *
     * @return a pair containing the loaded configuration object (or the given [configObject] if it was provided)
     * and the configuration data
     */
    fun <Object> loadOrCreateConfig(plugin: PluginContainer, configObject: Object? = null): Pair<Object?, T> {
        return loadOrCreateConfig(plugin.name, configObject)
    }

    /**
     * Loads or creates a configuration file with the specified [name].
     *
     * @param name the name of the configuration file (without extension)
     * @param configObject an optional configuration object to use if no file exists yet
     *
     * @return a pair containing the loaded configuration object (or the given [configObject] if it was provided)
     * and the configuration data
     */
    fun <Object> loadOrCreateConfig(name: String, configObject: Object? = null): Pair<Object?, T> {
        return loadOrCreateConfig(Path.of("$name.${dataConverter.extension}"), configObject)
    }

    private fun <Object> loadOrCreateConfig(path: Path, configObject: Object? = null): Pair<Object?, T> {
        val configPath = FabricLoader.getInstance().configDir.resolve(path)

        val configData = if (Files.notExists(configPath)) {
            getDefaultOrWriteData(configPath, configObject)
        } else {
            dataConverter.parseData(configPath)
        }

        return if (configObject != null) {
            Pair(configObject, configData)
        } else {
            Pair(null, configData)
        }
    }

    private fun getDefaultOrWriteData(configPath: Path, configObject: Any? = null): T {
        Files.createFile(configPath)
        val dataToUse: T = if (configObject != null) {
            dataConverter.convertObject(configObject)
        } else {
            dataConverter.default
        }
        Files.writeString(configPath, dataConverter.convertToString(dataToUse))
        return dataToUse
    }

    companion object {
        val JSON_CONFIG = ConfigLoader(JsonConverter())
    }
}