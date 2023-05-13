package io.github.mosaicmc.mosaiccore.plugin

/**
 * Interface for plugins to implement initialization code when loaded by the server.
 */
fun interface PluginInitializer {

    /**
     * Called when the plugin is loaded by the server.
     * @param plugin the container of the plugin
     */
    fun onLoad(plugin: PluginContainer)
}