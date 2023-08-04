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
@file:JvmName("EventRegistry")

package io.github.mosaicmc.mosaiccore.internal.event

import io.github.mosaicmc.mosaiccore.api.event.Event

/**
 * Register All
 *
 * Register all subscribers for their corresponding event types.
 *
 * @param subs The list of subscriber objects to register.
 */
internal fun EventHandler.registerAll(subs: List<Subscriber<*>>) = subs.forEach { register(it) }

/**
 * Register a subscriber for its corresponding event type.
 *
 * @param sub The subscriber object to register.
 */
private fun <E : Event<E>> EventHandler.register(sub: Subscriber<E>) =
    getOrCreateHandler(sub.eventClass).add(sub)
