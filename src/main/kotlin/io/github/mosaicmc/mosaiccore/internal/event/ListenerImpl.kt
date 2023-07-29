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

import io.github.mosaicmc.mosaiccore.api.event.*
import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.internal.unit
import kotlin.reflect.KClass

internal typealias MutList = MutableList<Subscriber<out Event>>

/**
 * Listener builder
 *
 * Listener builder is a class that holds all subscribers and registers them to the event handler
 *
 * @property plugin The plugin container
 */
class ListenerImpl(private val plugin: PluginContainer) : Listener {
    private val subs: MutList = mutableListOf()
    /**
     * Register
     *
     * Register is a function that registers all subscribers to the event handler.
     */
    internal fun register() = EventHandler.registerAll(subs)

    override fun <E : Event> subscriber(
        eventClazz: KClass<E>,
        data: SubscriberData,
        function: SubscriberFunction<E>
    ) = subs.add(Subscriber(eventClazz, data, function, plugin)).unit
}
