package io.github.mosaicmc.mosaiccore.internal.mixins;

import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer;
import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainerKt;
import io.github.mosaicmc.mosaiccore.api.plugin.PluginInitializer;
import io.github.mosaicmc.mosaiccore.internal.MainKt;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;initServer()Z"),
            method = "runServer"
    )
    private void pluginLoader(CallbackInfo ci) {
        final var plugins = FabricLoader.getInstance().getEntrypointContainers("plugin", PluginInitializer.class);

        plugins.forEach(plugin -> {
            final var entryPoint = plugin.getEntrypoint();
            final var modContainer = plugin.getProvider();

            final var pluginContainer = new PluginContainer(modContainer, (MinecraftServer) (Object) this);

            entryPoint.onLoad(pluginContainer);

            final var pluginName = PluginContainerKt.getName(pluginContainer);
            final var metadata = PluginContainerKt.getMetadata(pluginContainer);

            MainKt.getLogger().info("Loaded " + pluginName + ":" + metadata.getVersion());
        });
    }
}
