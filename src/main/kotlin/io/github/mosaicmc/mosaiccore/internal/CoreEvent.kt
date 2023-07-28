@file:Suppress("CAST_NEVER_SUCCEEDS")

package io.github.mosaicmc.mosaiccore.internal

import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.api.plugin.PluginInitializer
import io.github.mosaicmc.mosaiccore.api.plugin.metadata
import io.github.mosaicmc.mosaiccore.api.plugin.name
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer

object CoreEvent {
    internal fun pluginLoader() {
        val plugins =
            FabricLoader.getInstance()
                .getEntrypointContainers("plugin", PluginInitializer::class.java)

        plugins.forEach {
            val entryPoint = it.entrypoint
            val modContainer = it.provider
            val pluginContainer = PluginContainer(modContainer, (this as MinecraftServer))

            entryPoint.onLoad(pluginContainer)

            logger.info("Loaded " + pluginContainer.name + ":" + pluginContainer.metadata.version)
        }
    }
}
