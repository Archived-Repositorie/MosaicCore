package io.github.mosaicmc.mosaiccore.plugin

import net.fabricmc.loader.api.ModContainer
import net.fabricmc.loader.api.metadata.ModMetadata
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Plugin represents a plugin.
 *
 * @property modContainer Represents the mod of the plugin
 * @property name The name of the plugin.
 * @property server The server.
 * @property metadata The metadata of the plugin
 * @property logger The logger.
 */
data class PluginContainer (
    val modContainer: ModContainer,
    val server: MinecraftServer,
    val metadata: ModMetadata = modContainer.metadata,
    val name: String = metadata.id,
    val logger: Logger = LoggerFactory.getLogger(name),
) {
    /**
     * Gets the identifier for a resource.
     * @param path The path to the resource.
     * @return The identifier for the resource.
     */
    fun idOf(path: String): Identifier = Identifier(name, path)
}

