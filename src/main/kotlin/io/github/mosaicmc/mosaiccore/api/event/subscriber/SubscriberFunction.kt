package io.github.mosaicmc.mosaiccore.api.event.subscriber

/**
 * Type alias for a SubscriberFunction
 *
 * A `SubscriberFunction` is a lambda expression that represents a subscriber's action or callback
 * when handling an event of type [E].
 *
 * @param E The type of event that the subscriber function can handle.
 */
typealias SubscriberFunction<E> = E.() -> Unit
