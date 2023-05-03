
package io.github.mosaicmc.mosaiccore

import io.github.mosaicmc.mosaiccore.event.*
import io.github.mosaicmc.mosaiccore.utils.Mod
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.test.TestListener


internal val mod = Mod("mosaicmc")

@Suppress("unused")
fun preInit() {
    mod.logger.info("Welcome to mosaicmc!")

    if(System.getenv("TEST") == "true") {
        mod.logger.info("Test mode enabled")
        FabricLoader.getInstance().getEntrypoints("test", Runnable::class.java).forEach(Runnable::run)
    }
}

@Suppress("unused")
fun test() {

}

@Suppress("unused")
fun init() {
}








