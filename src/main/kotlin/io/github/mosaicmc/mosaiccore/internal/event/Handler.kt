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
import io.github.mosaicmc.mosaiccore.api.event.Subscriber
import io.github.mosaicmc.mosaiccore.api.event.SubscriberData
import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import kotlin.reflect.KClass

internal class Handler<E : Event>(
    private val values: MutableList<SubscriberObject<E>> = mutableListOf()
) {
    /**
     * Add
     *
     * Add is a function that adds a subscriber to the handler
     *
     * @param valueKey The subscriber object
     */
    fun add(valueKey: SubscriberObject<E>) {
        val index = getSortedPlace(valueKey)
        values.add(index, valueKey)
    }

    fun forEach(action: (SubscriberObject<E>) -> Unit) {
        for (value in values) {
            action(value)
        }
    }

    private fun getSortedPlace(key: SubscriberObject<E>): Int {
        var left = 0
        var right = values.size - 1
        while (left <= right) {
            val mid = (left + right) / 2
            when {
                values[mid] == key -> return mid
                values[mid] < key -> left = mid + 1
                else -> right = mid - 1
            }
        }
        return left
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
data class SubscriberObject<E : Event>(
    val eventClass: KClass<E>,
    val data: SubscriberData,
    val function: Subscriber<E>,
    val plugin: PluginContainer
) : Comparable<SubscriberObject<E>> {
    override fun compareTo(other: SubscriberObject<E>): Int {
        return data.priority.compareTo(other.data.priority)
    }
}
