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
package io.github.mosaicmc.mosaiccore.api.event.properties

/**
 * CancellableEvent interface
 *
 * The `CancellableEvent` interface represents an event that can be canceled. Events implementing
 * this interface can have a `cancelled` property, indicating whether the event has been canceled or
 * not.
 */
interface CancellableEvent {

    /** Gets or sets whether this event is canceled. */
    var cancelled: Boolean
}
