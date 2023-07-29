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

import io.github.mosaicmc.mosaiccore.api.event.properties.CancellableEvent
import io.github.mosaicmc.mosaiccore.api.plugin.name
import io.github.mosaicmc.mosaiccore.internal.event.EventHandler
import io.github.mosaicmc.mosaiccore.internal.logger

/**
 * Event interface
 *
 * The `Event` interface is used as a base interface for defining events. Events are objects that
 * represent something that happened in the system and can be listened to by subscribers.
 */
interface Event

/**
 * Call function for events
 *
 * The `call` extension function allows triggering the event and handling it by executing the
 * corresponding subscribers.
 *
 * @param E The event type. The type of event on which this function is called.
 */
fun <E : Event> E.call() {
    val handler = EventHandler.getOrCreateHandler(this::class)

    handler.iterator().forEach {
        if ((this is CancellableEvent) && cancelled && !it.data.cancellable) {
            return@forEach
        }
        apply(it.function)
        logger.debug("Handled event ${this::class.simpleName} by ${it.plugin.name}")
    }
}
