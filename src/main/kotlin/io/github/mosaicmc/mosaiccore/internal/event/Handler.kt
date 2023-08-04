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

package io.github.mosaicmc.mosaiccore.internal.event

import io.github.mosaicmc.mosaiccore.api.event.Event
import java.util.concurrent.ConcurrentSkipListSet
import java.util.stream.Stream

/**
 * Handler class
 *
 * This internal class is responsible for managing event subscribers of type [E].
 *
 * @param E The type of event that this handler manages.
 */
class Handler<E : Event<E>> {
    private val values: ConcurrentSkipListSet<Subscriber<E>> = ConcurrentSkipListSet()

    /**
     * Add a subscriber to the handler.
     *
     * @param value The subscriber to be added.
     */
    fun add(value: Subscriber<E>) = values.add(value)

    /**
     * Get an immutable list of the subscribers.
     *
     * @return Immutable list of the subscribers in this handler.
     */
    fun asList(): List<Subscriber<E>> = values.toList()

    /**
     * Get a stream of the subscribers
     *
     * @return Stream of the subscribers in this handler.
     */
    fun asStream(): Stream<Subscriber<E>> = values.stream()

    /**
     * Convert the handler to a human-readable string representation.
     *
     * @return A string representation of the handler, listing all the subscribers.
     */
    override fun toString(): String = values.joinToString("\n")
}
