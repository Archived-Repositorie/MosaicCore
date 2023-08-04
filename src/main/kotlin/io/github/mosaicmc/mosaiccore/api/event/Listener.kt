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

import io.github.mosaicmc.mosaiccore.api.event.subscriber.SubscriberData
import io.github.mosaicmc.mosaiccore.api.event.subscriber.SubscriberFunction
import kotlin.reflect.KClass

/**
 * Listener interface
 *
 * The `Listener` interface allows adding subscribers to listen for specific event types.
 */
interface Listener {
    /**
     * Subscriber function
     *
     * A function that adds a subscriber to the listener to listen for events of type [E].
     *
     * @param E The event class that the subscriber will listen to.
     * @param eventClazz The event class representing the type of event that the subscriber listens
     *   to.
     * @param data The subscriber data, which includes priority and cancellable settings (optional).
     * @param function The subscriber function, a lambda expression to handle the event.
     */
    fun <E : Event<E>> subscriber(
        eventClazz: KClass<E>,
        data: SubscriberData = SubscriberData(),
        function: SubscriberFunction<Event<E>>
    )
}
