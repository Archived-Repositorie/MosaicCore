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
import io.github.mosaicmc.mosaiccore.api.event.SubscriberData
import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import kotlin.reflect.KClass

internal class Handler<E : Event> {
    private val values: MutableList<Subscriber<E>> = mutableListOf()

    /**
     * Add
     *
     * Add is a function that adds a subscriber to the handler
     */
    fun add(value: Subscriber<E>) {
        val index = value.getSortedPlace()
        values.add(index, value)
    }

    fun iterator(): Iterator<Subscriber<E>> = values.iterator()

    private fun Subscriber<E>.getSortedPlace(): Int {
        return values.binarySearch(this).let { index ->
            if (index >= 0) {
                index // Key already exists in the array, return the index directly
            } else {
                -(index + 1) // Key doesn't exist, return the insertion point
            }
        }
    }
}

/**
 * Subscriber object
 *
 * Subscriber object is a data class that holds the subscriber data and the subscriber function
 *
 * @param E
 * @property eventClass The event class
 * @property data The subscriber data
 * @property function The subscriber function
 * @property plugin The plugin container
 */
data class Subscriber<E : Event>(
    val eventClass: KClass<E>,
    val data: SubscriberData,
    val function: E.() -> Unit,
    val plugin: PluginContainer
) : Comparable<Subscriber<E>> {
    override fun compareTo(other: Subscriber<E>): Int {
        return data.priority.compareTo(other.data.priority)
    }
}
