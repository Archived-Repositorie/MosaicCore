package com.test

import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.plugin.PluginInitializer


@Suppress("unused")
object Test : PluginInitializer {
    override fun onLoad(plugin: PluginContainer) {
        plugin.logger.info("Test plugin loaded")
    }
}