package io.github.mosaicmc.mosaiccore.event

interface CancellableEvent {
    var cancelled: Boolean

    fun isCancelled(): Boolean {
        return cancelled
    }
}