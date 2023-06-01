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

import com.google.gson.JsonObject
import io.github.mosaicmc.mosaiccore.config.ConfigLoader.ExtendedUpdater
import io.github.mosaicmc.mosaiccore.config.ConfigLoader.Updater
import io.github.mosaicmc.mosaiccore.config.impl.SimpleJsonCoder
import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import net.fabricmc.loader.api.FabricLoader
import java.io.File
import java.nio.file.Path

class ConfigLoader<T>(private val dataCoder: DataCoder<T>) {

    fun <O : ConfigData> loadOrCreateConfig(
        plugin: PluginContainer,
        fileName: String = "common",
        configData: O,
        configDataModifier: ExtendedUpdater<O, T> = ExtendedUpdater { it.data }
    ): ExtendedConfigKey<O, T> {
        val file = getConfigPath(plugin,fileName).toFile() /* gets file */
        val dataObject = dataCoder.convertObject(configData) /* converts configData to dataObject */
        val data = loadOrWrite(file, dataObject) /* loads or writes dataObject to file */
        val configObject = dataCoder.convertToObject(data, configData.javaClass) /* converts data to configObject */
        val key = ExtendedConfigKey(file, configObject, data) /* creates key */
        val modifiedConfig = configDataModifier.update(key) /* modifies config */
        val modifiedData = dataCoder.convertObject(modifiedConfig) /* converts modified config to modified data */

        return ExtendedConfigKey(file, modifiedConfig, modifiedData) /* returns key */
    }

    fun loadOrCreateConfig(
        plugin: PluginContainer,
        fileName: String = "common",
        configData: T = dataCoder.default,
        configDataModifier: Updater<T> = Updater { it.coderObject }
    ): ConfigKey<T> {
        val file = getConfigPath(plugin,fileName).toFile()
        var data = loadOrWrite(file, configData)

        data = configDataModifier.update(ConfigKey(file,data))
        return ConfigKey(file,data)
    }

    fun <O : ConfigData> updateExtendedConfig(
        key: ExtendedConfigKey<O,T>,
        updater: ExtendedUpdater<O,T>
    ): T {
        val data = loadOrWrite(key.file,key.coderObject)
        val objectData = dataCoder.convertToObject(data, key.data.javaClass)
        val updatedData = updater.update(ExtendedConfigKey(key.file,objectData,data))
        val updatedConfig = dataCoder.convertObject(updatedData)

        if (updatedConfig != data) {
            writeConfig(key.file, updatedConfig)
        }
        return updatedConfig
    }

    fun updateConfig(
        key: ConfigKey<T>,
        updater: Updater<T>
    ): T {
        val data = loadOrWrite(key.file,key.coderObject)
        val updatedData = updater.update(ConfigKey(key.file,data))
        if (updatedData != data) {
            writeConfig(key.file, updatedData)
        }
        return updatedData
    }

    private fun loadOrWrite(
        file: File,
        toWrite: T = dataCoder.default
    ): T {
        return if (file.exists()) {
            loadConfig(file)
        } else {
            writeConfig(file, toWrite)
        }
    }

    private fun loadConfig(file: File): T = dataCoder.decodeFile(file)

    private fun writeConfig(file: File, data: T): T {
        createConfig(file)
        dataCoder.encodeToFile(data, file)
        return data
    }

    private fun createConfig(file: File) {
        val path = file.parentFile
        if (!path.exists()) {
            path.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    private fun getConfigPath(plugin: PluginContainer, fileName: String): Path = getConfigPath("${plugin.name}/$fileName.${dataCoder.extension}")

    private fun getConfigPath(path: Path): Path = FabricLoader.getInstance().configDir.resolve(path)

    private fun getConfigPath(path: String): Path = getConfigPath(Path.of(path))

    fun interface Updater<T> {
        fun update(data: ConfigKey<T>): T
    }

    fun interface ExtendedUpdater<O : ConfigData,T> {
        fun update(data: ExtendedConfigKey<O,T>): O
    }

    companion object {
        /**
         * A configuration loader that uses JSON as its data format.
         */
        val SIMPLE_JSON_CONFIG: ConfigLoader<JsonObject> = ConfigLoader(SimpleJsonCoder())
    }
}

typealias JsonCoder = DataCoder<JsonObject>