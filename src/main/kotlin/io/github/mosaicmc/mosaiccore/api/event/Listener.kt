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

@file:Suppress("unused")

package io.github.mosaicmc.mosaiccore.api.event

import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.internal.event.ListenerImpl
import kotlin.reflect.KClass

interface Listener {
    /**
     * Subscriber
     *
     * Subscriber is a function that adds subscriber to listener
     *
     * @param E The event class
     * @param eventClazz The event class
     * @param data The subscriber data
     * @param function The subscriber function
     */
    fun <E : Event> subscriber(
        eventClazz: KClass<E>,
        data: SubscriberData = SubscriberData(),
        function: SubscriberFunction<E>
    )

    companion object
}

/**
 * Subscriber
 *
 * Subscriber is a DSL function that adds subscriber to listener
 *
 * @param E The event class
 * @param data The subscriber data
 * @param function The subscriber function
 */
inline fun <reified E : Event> Listener.subscriber(
    data: SubscriberData = SubscriberData(),
    noinline function: SubscriberFunction<E>
) {
    subscriber(E::class, data, function)
}

/**
 * Subscriber
 *
 * Subscriber is a DSL function that adds subscriber to listener
 *
 * @param E The event class
 * @param priority The priority
 * @param cancellable The cancellable
 * @param function The subscriber function
 */
inline fun <reified E : Event> Listener.subscriber(
    priority: Priority = Priority.NORMAL,
    cancellable: Boolean = false,
    noinline function: SubscriberFunction<E>
) {
    subscriber<E>(SubscriberData(priority, cancellable), function)
}

/**
 * Listener
 *
 * Listener is a DSL function that allows for easy event registration.
 *
 * @param block The DSL block where event subscribers are defined.
 * @receiver The plugin container to which the listeners will be associated.
 */
infix fun PluginContainer.listen(block: Listener.() -> Unit) =
    ListenerImpl(this).apply(block).register()
