package io.github.mosaicmc.mosaiccore.mixins;

import io.github.mosaicmc.mosaiccore.MainKt;
import io.github.mosaicmc.mosaiccore.plugin.PluginContainer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;setupServer()Z"), method = "runServer")
    private void beforeSetupServer(CallbackInfo info) {
        final var plugins = MainKt.getPlugins();
        for(final var plugin : plugins) {
            final var entryPoint = plugin.getEntrypoint();
            final var provider = plugin.getProvider();
            final var server = (MinecraftServer) (Object) this;

            entryPoint.onLoad(new PluginContainer(provider, server));
        }
    }
}
