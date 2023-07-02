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

/** Event interface used for events. */
interface Event {
    companion object
}

fun <E : Event> E.call() {
    val handler = EventHandler.getOrCreateHandler(this::class)
    handler.iterator().forEach {
        if ((this is CancellableEvent) && this.cancelled) {
            return@forEach
        }
        this.apply(it.function)
        logger.debug("Handled event {} by {}", this::class.simpleName, it.plugin.name)
    }
}
