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

package io.github.mosaicmc.mosaiccore.api.event.properties

/**
 * Laggy annotation
 *
 * An annotation indicating that an event or function may cause performance issues due to lag. Use
 * this annotation to mark events or functions that are known to cause lag or other performance
 * issues, so that users can opt in or out of using them based on their specific needs.
 *
 * @property message The message to display when this annotation is triggered.
 */
@RequiresOptIn(message = "This event is laggy and may cause performance issues.")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Laggy
