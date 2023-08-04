package io.github.mosaicmc.mosaiccore.internal.event

import io.github.mosaicmc.mosaiccore.api.event.Event
import io.github.mosaicmc.mosaiccore.api.event.subscriber.SubscriberData
import io.github.mosaicmc.mosaiccore.api.event.subscriber.SubscriberFunction
import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import kotlin.reflect.KClass

/**
 * Subscriber class
 *
 * This data class represents a subscriber that listens to events of type [E].
 *
 * @param E The type of event that this subscriber listens to.
 * @property eventClass The event class that this subscriber listens to.
 * @property data The subscriber data, such as name, description, or any other relevant information.
 * @property function The subscriber function that will be invoked when the corresponding event
 *   occurs.
 * @property plugin The plugin container associated with this subscriber.
 */
data class Subscriber<E : Event<E>>(
    val eventClass: KClass<E>,
    val data: SubscriberData,
    val function: SubscriberFunction<Event<E>>,
    val plugin: PluginContainer
) : Comparable<Subscriber<E>> {
    /**
     * Compare this subscriber to another subscriber based on their priority. This is required to
     * sort subscribers in a meaningful order.
     *
     * @param other The other subscriber to compare to.
     * @return A negative integer if this subscriber has lower priority, zero if they have the same
     *   priority, and a positive integer if this subscriber has higher priority.
     */
    override fun compareTo(other: Subscriber<E>): Int = data.priority.compareTo(other.data.priority)
}
