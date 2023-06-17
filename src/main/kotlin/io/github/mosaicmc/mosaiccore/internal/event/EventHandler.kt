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
import kotlin.reflect.KClass

object EventHandler {
    private val events: EventMap = HashMap()

    /**
     * Register DSL
     *
     * Register DSL is a helper function that registers all subscribers to the event handler
     *
     * @param subs The list of subscriber objects
     */
    internal fun registerDSL(subs: List<Subscriber<out Event>>) {
        for (sub in subs) {
            registerSubscriber(sub)
        }
    }

    internal fun <E : Event> getOrCreateHandler(eventKClass: KClass<out E>): Handler<E> {
        if (events[eventKClass] == null) {
            events[eventKClass] = Handler()
        }
        return events[eventKClass] as Handler<E>
    }

    private fun <E : Event> registerSubscriber(sub: Subscriber<E>) =
        getOrCreateHandler(sub.eventClass).add(sub)
}

internal typealias EventMap = HashMap<KClass<out Event>, Handler<out Event>>
