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

object EventHandler {
    private val events: TypeMap<Event, Handler<out Event>> = TypeMap()

    internal fun <E : Event> EventHandler.getOrCreateHandler(
        eventKClass: KClass<out E>
    ): Handler<E> {
        events.putIfAbsent(eventKClass, Handler())
        return events[eventKClass] as Handler<E>
    }
}

/**
 * Register All
 *
 * Register all subscribers
 *
 * @param subs The list of subscriber objects
 */
internal fun EventHandler.registerSubscribers(subs: List<Subscriber<*>>) {
    for (sub in subs) register(sub)
}

private fun <E : Event> EventHandler.register(sub: Subscriber<E>) {
    val handler = getOrCreateHandler(sub.eventClass)
    handler.add(sub)
}

internal typealias TypeMap<E, V> = ConcurrentHashMap<KClass<out E>, V>
