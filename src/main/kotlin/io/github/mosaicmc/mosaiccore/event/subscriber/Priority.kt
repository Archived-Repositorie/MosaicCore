package io.github.mosaicmc.mosaiccore.event.subscriber


/**
 * Priority
 *
 * Priority of an event subscriber.
 * If there are multiple subscribers for an event,
 * subscribers with higher priority are called before those with lower priority.
 * @property HIGHEST
 * @property HIGH
 * @property NORMAL (default)
 * @property LOW
 * @property LOWEST
 * @constructor Create empty Priority
 */
enum class Priority {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST
}