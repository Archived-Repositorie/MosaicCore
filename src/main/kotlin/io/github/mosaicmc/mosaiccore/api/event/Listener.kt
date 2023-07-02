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

import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.internal.event.ListenerImpl
import kotlin.reflect.KClass

interface Listener {
    /**
     * Subscriber
     *
     * Subscriber is a function that adds subscriber to listener
     *
     * @param E The event class
     * @param eventClazz The event class
     * @param data The subscriber data
     * @param function The subscriber function
     */
    fun <E : Event> subscriber(
        eventClazz: KClass<E>,
        data: SubscriberData = SubscriberData(),
        function: SubscriberFunction<E>
    )

    companion object
}

/**
 * Subscriber
 *
 * Subscriber is a DSL function that add subscriber to listener
 *
 * @param E The event class
 * @param data The subscriber data
 * @param function The subscriber function
 */
inline fun <reified E : Event> Listener.subscriber(
    data: SubscriberData = SubscriberData(),
    noinline function: SubscriberFunction<E>
) {
    subscriber(E::class, data, function)
}

/**
 * Listener
 *
 * Listener is a DSL function that allows for easy event registration
 *
 * @param plugin The plugin container
 * @param block The DSL block
 * @receiver The plugin container
 */
fun Listener.Companion.listener(plugin: PluginContainer, block: Listener.() -> Unit) =
    ListenerImpl(plugin).apply(block).register()
