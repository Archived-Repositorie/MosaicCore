@file:JvmName("PluginAdditions")
@file:Suppress("unused")

package io.github.mosaicmc.mosaiccore.api.plugin

import net.fabricmc.loader.api.metadata.ModMetadata
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Extension function `resource`
 *
 * Gets the resource location for the plugin by combining the plugin's name and the specified path.
 *
 * @param path The path to the resource.
 * @return The resource location as a `ResourceLocation` object.
 */
infix fun PluginContainer.resource(path: String): ResourceLocation = ResourceLocation(name, path)

/**
 * Extension property `metadata`
 *
 * Gets the metadata of the plugin from the `modContainer` property.
 *
 * @return The metadata of the plugin as a `ModMetadata` object.
 */
val PluginContainer.metadata: ModMetadata
    get() = modContainer.metadata

/**
 * Extension property `logger`
 *
 * Gets the logger associated with the plugin using the plugin's name.
 *
 * @return The logger as an `Logger` object.
 */
val PluginContainer.logger: Logger
    get() = LoggerFactory.getLogger(name)

/**
 * Extension property `name`
 *
 * Gets the name of the plugin from the `metadata` property.
 *
 * @return The name of the plugin as a `String`.
 */
val PluginContainer.name: String
    get() = metadata.id
