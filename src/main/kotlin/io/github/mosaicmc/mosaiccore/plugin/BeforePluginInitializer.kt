package io.github.mosaicmc.mosaiccore.plugin

/**
 * Represents a plugin initializer that is called before the plugin is loaded.
 */
fun interface BeforePluginInitializer {
    /**
     * Called before the plugin is loaded.
     */
    fun beforeLoad()
}
