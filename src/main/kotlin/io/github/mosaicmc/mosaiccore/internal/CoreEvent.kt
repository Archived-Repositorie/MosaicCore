package io.github.mosaicmc.mosaiccore.internal

import io.github.mosaicmc.mosaiccore.api.plugin.metadata
import io.github.mosaicmc.mosaiccore.api.plugin.name
import net.minecraft.server.MinecraftServer

/**
 * CoreEvents object
 *
 * The `CoreEvents` object contains functions related to core event handling in the plugin loader.
 */
internal object CoreEvents {

    /**
     * Plugin Loader function
     *
     * The `pluginLoader` function is responsible for loading plugins in the Minecraft server. It
     * calls the `Loader.loadPlugins` method to load the plugins and then logs information about the
     * loaded plugins.
     *
     * @param server The Minecraft server instance where the plugins will be loaded.
     */
    internal fun pluginLoader(server: MinecraftServer) =
        Loader.loadPlugins(server).forEach {
            logger.info("Loaded ${it.name}:${it.metadata.version}")
        }
}
