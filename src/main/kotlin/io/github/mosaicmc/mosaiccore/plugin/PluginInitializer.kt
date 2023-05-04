package io.github.mosaicmc.mosaiccore.plugin

interface PluginInitializer {
    fun onLoad(plugin: PluginContainer)
}