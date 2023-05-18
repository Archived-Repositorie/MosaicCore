
package io.github.mosaicmc.mosaiccore

import io.github.mosaicmc.mosaiccore.event.Event
import io.github.mosaicmc.mosaiccore.event.listener
import io.github.mosaicmc.mosaiccore.plugin.BeforePluginInitializer
import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.plugin.PluginInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory


internal val logger = LoggerFactory.getLogger("mosaicmc")
internal val plugins = FabricLoader.getInstance().getEntrypointContainers("plugin", PluginInitializer::class.java)
internal val beforePlugins = FabricLoader.getInstance().getEntrypointContainers("before_plugin", BeforePluginInitializer::class.java)

@Suppress("unused")
fun preInit() {
    logger.info("Welcome to mosaicmc!")
    if(System.getenv("TEST") == "true") {
        logger.info("Test mode enabled")
    }
}

fun test(plugin: PluginContainer) = listener(plugin) {
    subscriber(TestEvent::class) {
        println("Test event")
    }
}.register()


class TestEvent : Event







