package io.github.mosaicmc.mosaiccore.mixins

import io.github.mosaicmc.mosaiccore.beforePlugins
import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.plugins
import net.minecraft.server.MinecraftServer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo


@Mixin(MinecraftServer::class)
class MinecraftServerMixin {
    @Inject(
        at = [At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z")],
        method = ["runServer"]
    )
    private fun pluginLoader(info: CallbackInfo) {
        println("owo")
        val beforePlugins = beforePlugins
        for (plugin in beforePlugins) {
            val entryPoint = plugin.entrypoint
            entryPoint.beforeLoad()
        }
        val plugins = plugins
        for (plugin in plugins) {
            val entryPoint = plugin.entrypoint
            val modContainer = plugin.provider
            val server = this as MinecraftServer
            entryPoint.onLoad(
                PluginContainer(
                    modContainer,
                    server,
                )
            )
        }
    }
}
