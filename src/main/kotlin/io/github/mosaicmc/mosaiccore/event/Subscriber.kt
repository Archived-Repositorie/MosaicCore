package io.github.mosaicmc.mosaiccore.event

/**
 * Annotation used to mark methods to be used as event subscribers.
 * @property priority the priority of the subscriber, defaulting to [Priority.NORMAL].
 * @property ignoreCancelled whether events cancelled should be ignored, defaulting to false.
 */
@Suppress("unused")
annotation class Subscriber(
    val priority: Priority = Priority.NORMAL,
    val ignoreCancelled: Boolean = false
)
