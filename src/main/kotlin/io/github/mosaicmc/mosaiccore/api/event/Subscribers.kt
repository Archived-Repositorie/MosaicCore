/*
 * Copyright (c) 2023. JustFoxx
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
@file:JvmName("Subscribers")
@file:Suppress("unused")

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
