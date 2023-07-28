package io.github.mosaicmc.mosaiccore.internal

import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.api.plugin.PluginInitializer
import io.github.mosaicmc.mosaiccore.api.plugin.metadata
import io.github.mosaicmc.mosaiccore.api.plugin.name
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer

object CoreEvent {
    internal fun pluginLoader(server: MinecraftServer) =
        FabricLoader.getInstance()
            .getEntrypointContainers("plugin", PluginInitializer::class.java)
            .stream()
            .map {
                val pluginContainer = PluginContainer(it.provider, server)
                it.entrypoint.onLoad(pluginContainer)
                pluginContainer.name to pluginContainer.metadata.version
            }
            .forEach { (name, version) -> logger.info("Loaded $name:$version") }
}
