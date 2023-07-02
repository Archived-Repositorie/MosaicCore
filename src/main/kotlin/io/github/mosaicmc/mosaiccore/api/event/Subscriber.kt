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

/**
 * Data class for subscriber
 *
 * @property priority The priority of the subscriber, defaults to [Priority.NORMAL].
 * @property ignoreCancelled Determines whether canceled events should be ignored, defaults to
 *   false.
 */
data class SubscriberData(
    val priority: Priority = Priority.NORMAL,
    val ignoreCancelled: Boolean = false
)

/**
 * Priority of a subscriber
 *
 * @property integer The integer value of the priority
 */
data class Priority(private val integer: Int) : Comparable<Priority> {
    companion object {
        val HIGHEST = Priority(Int.MAX_VALUE)
        val HIGH = Priority(1)
        val NORMAL = Priority(0)
        val LOW = Priority(-1)
        val LOWEST = Priority(Int.MIN_VALUE)
    }

    override fun compareTo(other: Priority): Int {
        return integer.compareTo(other.integer)
    }
}

typealias SubscriberFunction<E> = E.() -> Unit
