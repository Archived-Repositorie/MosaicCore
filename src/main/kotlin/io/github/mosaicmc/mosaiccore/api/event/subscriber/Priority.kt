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

package io.github.mosaicmc.mosaiccore.api.event.subscriber

/**
 * Priority of a subscriber
 *
 * Represents the priority level of a subscriber. Subscribers with higher priority values are
 * executed first.
 *
 * @property integer The integer value representing the priority level.
 */
data class Priority(private val integer: Int) : Comparable<Priority> {
    companion object {
        val HIGHEST = Priority(Int.MAX_VALUE)
        val HIGH = Priority(1)
        val NORMAL = Priority(0)
        val LOW = Priority(-1)
        val LOWEST = Priority(Int.MIN_VALUE)
    }

    /**
     * Compare this priority to another priority.
     *
     * @param other The other priority to compare to.
     * @return A positive integer if this priority is greater, zero if they are equal, and a
     *   negative integer if this priority is smaller.
     */
    override fun compareTo(other: Priority): Int = other.integer.compareTo(integer)
}
