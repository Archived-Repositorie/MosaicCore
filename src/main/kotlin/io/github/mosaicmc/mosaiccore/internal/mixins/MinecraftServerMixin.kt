/*
 * Copyright (c) 2023. JustFoxx
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
@file:Suppress("CAST_NEVER_SUCCEEDS")

package io.github.mosaicmc.mosaiccore.internal.mixins

import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.api.plugin.PluginInitializer
import io.github.mosaicmc.mosaiccore.api.plugin.metadata
import io.github.mosaicmc.mosaiccore.api.plugin.name
import io.github.mosaicmc.mosaiccore.internal.logger
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.MinecraftServer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(MinecraftServer::class)
class MinecraftServerMixin {
    @Inject(
        at =
            [At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z")],
        method = ["runServer"]
    )
    private fun pluginLoader(info: CallbackInfo) {
        val plugins =
            FabricLoader.getInstance()
                .getEntrypointContainers("plugin", PluginInitializer::class.java)

        for (plugin in plugins) {
            val entryPoint = plugin.entrypoint
            val modContainer = plugin.provider
            val server = this as MinecraftServer
            val pluginContainer = PluginContainer(modContainer, server)

            entryPoint.onLoad(pluginContainer)

            logger.info("loaded ${pluginContainer.name}:${pluginContainer.metadata.version}")
        }
    }
}
