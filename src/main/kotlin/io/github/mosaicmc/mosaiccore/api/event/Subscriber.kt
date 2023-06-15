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
 * Subscriber
 *
 * Functional interface that represents an event subscriber.
 *
 * @param E The event type
 */
fun interface Subscriber<E : Event> {
    fun accept(event: E)
}

/**
 * Annotation used to mark methods as event subscribers.
 *
 * @property priority The priority of the subscriber, defaults to [Priority.NORMAL].
 * @property ignoreCancelled Determines whether canceled events should be ignored, defaults to
 *   false.
 */
annotation class SubscriberData(
    val priority: Priority = Priority.NORMAL,
    val ignoreCancelled: Boolean = false
)

/**
 * Priority
 *
 * Enum class representing the priority of an event subscriber. Subscribers with higher priority are
 * invoked before those with lower priority.
 */
enum class Priority {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST
}
