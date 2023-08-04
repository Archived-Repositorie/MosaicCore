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

@file:Suppress("UNCHECKED_CAST")

package io.github.mosaicmc.mosaiccore.internal.event

import io.github.mosaicmc.mosaiccore.api.event.Event
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * EventHandler object
 *
 * The `EventHandler` object manages event handlers for different event types. It allows to register
 * subscribers for various events and handles the dispatching of events to the appropriate
 * subscribers.
 */
object EventHandler {
    private val events: TypeMap<Event<*>, Handler<*>> = TypeMap()

    /**
     * Get or create the event handler for a specific event type.
     *
     * If an event handler for the given event type already exists, it is returned. Otherwise, a new
     * event handler is created and associated with the event type.
     *
     * @param eventKClass The Kotlin class representing the event type.
     * @return The event handler for the specified event type.
     */
    internal fun <E : Event<E>> getOrCreateHandler(eventKClass: KClass<out E>): Handler<E> =
        events.getOrPut(eventKClass) { Handler() } as Handler<E>
}

/**
 * TypeMap typealias
 *
 * A thread-safe map that associates event types (`E`) with their corresponding values (`V`).
 */
internal typealias TypeMap<E, V> = ConcurrentHashMap<KClass<out E>, V>
