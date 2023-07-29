package io.github.mosaicmc.mosaiccore.internal

import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.api.plugin.PluginInitializer
import jdk.tools.jlink.resources.plugins
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer

/**
 * Singleton object that manages plugins for a Minecraft server.
 *
 * This object provides functionality to load and retrieve plugins for a Minecraft server. Plugins
 * are loaded from the FabricLoader's entrypoint containers, and PluginInitializer classes are used
 * to initialize the plugins.
 *
 * @property plugins A list of loaded PluginContainers representing the currently loaded plugins.
 */
object Loader {
    /**
     * A list of loaded plugins.
     *
     * This property holds a list of PluginContainer objects representing the currently loaded
     * plugins for the Minecraft server. The list is initially empty and gets populated once the
     * plugins are loaded using the [loadPlugins] function.
     */
    var plugins: List<PluginContainer> = emptyList()
        private set

    /**
     * Loads the plugins for the Minecraft server.
     *
     * This function loads plugins by querying the FabricLoader for entrypoint containers of the
     * specified type ("plugin" with PluginInitializer class). It initializes each plugin in
     * parallel and stores them in the [plugins] list.
     *
     * @param server The MinecraftServer instance to be passed to each plugin's PluginInitializer.
     */
    internal fun loadPlugins(server: MinecraftServer): List<PluginContainer> {
        plugins =
            FabricLoader.getInstance()
                .getEntrypointContainers("plugin", PluginInitializer::class.java)
                .parallelStream()
                .map {
                    val pluginContainer = PluginContainer(it.provider, server)
                    it.entrypoint.onLoad(pluginContainer)
                    pluginContainer
                }
                .toList()
        return plugins
    }
}
