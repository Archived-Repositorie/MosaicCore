package io.github.mosaicmc.mosaiccore.api.event.subscriber

/**
 * Data class for subscriber
 *
 * Represents additional data associated with a subscriber, including its priority and cancellable
 * status.
 *
 * @property priority The priority of the subscriber, defaults to [Priority.NORMAL].
 * @property cancellable Determines whether canceled events should be ignored, defaults to false.
 */
data class SubscriberData(
    val priority: Priority = Priority.NORMAL,
    val cancellable: Boolean = false
)
