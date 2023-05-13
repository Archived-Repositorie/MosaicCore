package io.github.mosaicmc.mosaiccore.event

/**
 * Priority of an event subscriber.
 * If there are multiple subscribers for an event, subscribers with higher priority are called before those with lower priority.
 *
 * The `Priority` enum provides the following levels of priority, in decreasing order:
 * - `HIGHEST`
 * - `HIGH`
 * - `NORMAL` (default)
 * - `LOW`
 * - `LOWEST`
 */
@Suppress("unused")
enum class Priority {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST
}