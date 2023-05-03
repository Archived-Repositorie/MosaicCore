package io.github.mosaicmc.mosaiccore.event

/**
 * Priority of an event subscriber.
 * If higher priority subscriber are registered, they will be called first.
 */
@Suppress("unused")
enum class Priority {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST
}