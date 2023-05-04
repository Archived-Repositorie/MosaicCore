package io.github.mosaicmc.mosaiccore.plugin

import net.fabricmc.loader.api.ModContainer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path

/**
 * Plugin represents a plugin.
 *
 * @property modContainer Represents the mod of the plugin
 * @property name The name of the plugin.
 * @property server The server.
 * @property logger The logger.
 * @property configPath The path to the config file.
 */
data class PluginContainer (
    val modContainer: ModContainer,
    val server: MinecraftServer,
    val name: String = modContainer.metadata.id,
    val logger: Logger = LoggerFactory.getLogger(name),
    val configPath: Path = Path.of("$name.json")
) {
    /**
     * Gets the identifier for a resource.
     * @param path The path to the resource.
     * @return The identifier for the resource.
     */
    fun idOf(path: String): Identifier {
        return Identifier(name, path)
    }

    constructor(modContainer: ModContainer, server: MinecraftServer) :
            this(modContainer, server, modContainer.metadata.id, LoggerFactory.getLogger(modContainer.metadata.id), Path.of("${modContainer.metadata.id}.json"))

}

