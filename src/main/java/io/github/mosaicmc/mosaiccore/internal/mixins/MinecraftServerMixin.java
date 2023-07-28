package io.github.mosaicmc.mosaiccore.internal.mixins;

import io.github.mosaicmc.mosaiccore.internal.CoreEvent;
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
    private void pluginLoader(CallbackInfo $unused$) {
        CoreEvent.INSTANCE.pluginLoader$MosaicCore((MinecraftServer)(Object)this);
    }
}
