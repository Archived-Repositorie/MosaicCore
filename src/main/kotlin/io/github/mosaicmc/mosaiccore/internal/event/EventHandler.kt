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

package io.github.mosaicmc.mosaiccore.internal.event

import io.github.mosaicmc.mosaiccore.api.event.Event
import kotlin.reflect.KClass

object EventHandler {
    private val events: EventMap = HashMap()

    internal fun <E : Event> getHandler(eventKClass: KClass<E>): Handler<E> {
        @Suppress("UNCHECKED_CAST")
        return events[checkForEvent(eventKClass)]!! as Handler<E>
    }

    /**
     * Register DSL
     *
     * Register DSL is a helper function that registers all subscribers to the event handler
     * @param list The list of subscriber objects
     */
    internal fun registerDSL(
        list: List<SubscriberObject<out Event>>
    ) {
        list.forEach {
            registerSubscriber(it)
        }
    }

    private fun <E : Event> checkForEvent(eventKClass: KClass<E>): KClass<E> {
        if (!events.containsKey(eventKClass)) {
            events[eventKClass] = Handler()
        }
        return eventKClass
    }

    private fun <E : Event> registerSubscriber(sub: SubscriberObject<E>) = getHandler(sub.eventClass).add(sub)

}

internal typealias EventMap = HashMap<KClass<out Event>, Handler<out Event>>

