@file:JvmName("Subscribers")

package io.github.mosaicmc.mosaiccore.api.event

import io.github.mosaicmc.mosaiccore.api.event.subscriber.Priority
import io.github.mosaicmc.mosaiccore.api.event.subscriber.SubscriberData
import io.github.mosaicmc.mosaiccore.api.event.subscriber.SubscriberFunction
import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.internal.event.ListenerImpl

/**
 * DSL subscriber function
 *
 * This DSL function allows adding a subscriber to listen for events of type [E].
 *
 * @param E The event class that the subscriber will listen to.
 * @param data The subscriber data, which includes priority and cancellable settings (optional).
 * @param function The subscriber function, a lambda expression to handle the event.
 */
inline fun <reified E : Event<E>> Listener.subscriber(
    data: SubscriberData = SubscriberData(),
    noinline function: SubscriberFunction<Event<E>>
) = subscriber(E::class, data, function)

/**
 * DSL subscriber function with priority and cancellable settings
 *
 * This DSL function allows adding a subscriber to listen for events of type [E] with specific
 * priority and cancellable settings.
 *
 * @param E The event class that the subscriber will listen to.
 * @param priority The priority of the subscriber.
 * @param cancellable Determines whether canceled events should be ignored by the subscriber.
 * @param function The subscriber function, a lambda expression to handle the event.
 */
inline fun <reified E : Event<E>> Listener.subscriber(
    priority: Priority = Priority.NORMAL,
    cancellable: Boolean = false,
    noinline function: SubscriberFunction<Event<E>>
) = subscriber<E>(SubscriberData(priority, cancellable), function)

/**
 * Listener DSL function
 *
 * This DSL function provides a convenient way to register multiple event subscribers.
 *
 * @param block The DSL block where event subscribers are defined.
 * @receiver The plugin container to which the listeners will be associated.
 */
infix fun PluginContainer.listen(block: Listener.() -> Unit) =
    ListenerImpl(this).apply(block).register()
