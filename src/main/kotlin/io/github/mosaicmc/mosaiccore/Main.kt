@file:Suppress("KDocMissingDocumentation", "UNUSED")


package io.github.mosaicmc.mosaiccore

import io.github.mosaicmc.mosaiccore.config.ConfigData
import io.github.mosaicmc.mosaiccore.config.ConfigLoader
import io.github.mosaicmc.mosaiccore.plugin.BeforePluginInitializer
import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.plugin.PluginInitializer
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.LoggerFactory
import kotlin.io.encoding.ExperimentalEncodingApi


internal val logger = LoggerFactory.getLogger("mosaicmc")
internal val plugins = FabricLoader.getInstance().getEntrypointContainers("plugin", PluginInitializer::class.java)
internal val beforePlugins = FabricLoader.getInstance().getEntrypointContainers("before_plugin", BeforePluginInitializer::class.java)


@OptIn(ExperimentalEncodingApi::class)
fun preInit() {
    logger.info("Welcome to mosaicmc!")
}

fun test(plugin: PluginContainer) {
    val config = ConfigLoader.SIMPLE_JSON_CONFIG.loadOrCreateConfig(
        plugin,
        configData = TestConfig("not test"),
    ) { key ->
        key.data.copy(test = "test2")
    }
    println("test: ${config.data.test}")
}

data class TestConfig(
    val test: String = "test"
) : ConfigData








