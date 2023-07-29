package io.github.mosaicmc.mosaiccore.internal

import io.github.mosaicmc.mosaiccore.api.plugin.metadata
import io.github.mosaicmc.mosaiccore.api.plugin.name
import net.minecraft.server.MinecraftServer

internal object CoreEvents {
    internal fun pluginLoader(server: MinecraftServer) =
        Loader.loadPlugins(server).forEach {
            logger.info("Loaded ${it.name}:${it.metadata.version}")
        }
}
