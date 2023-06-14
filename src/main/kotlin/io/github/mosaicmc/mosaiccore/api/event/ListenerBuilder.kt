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
import io.github.mosaicmc.mosaiccore.internal.event.ListenerBuilderImpl
import kotlin.reflect.KClass

interface ListenerBuilder {
    /**
     * Subscriber
     *
     * Subscriber is a function that adds subscriber to listener
     * @param E The event class
     * @param eventClazz The event class
     * @param data The subscriber data
     * @param function The subscriber function
     */
    fun <E : Event> subscriber(
        eventClazz: KClass<E>,
        data: SubscriberData = SubscriberData(),
        function: Subscriber<E>
    )

    companion object {
        /**
         * Listener
         *
         * Listener is a DSL function that allows for easy event registration
         * @param plugin The plugin container
         * @param block The DSL block
         * @receiver The plugin container
         */
        fun listener(plugin: PluginContainer, block: ListenerBuilder.() -> Unit) =
            ListenerBuilderImpl(plugin).apply(block).register()
    }
}

/**
 * Subscriber
 *
 * Subscriber is a DSL function that add subscriber to listener
 * @param E The event class
 * @param data The subscriber data
 * @param function The subscriber function
 */
inline fun <reified E : Event> ListenerBuilder.subscriber(
    data: SubscriberData = SubscriberData(),
    function: Subscriber<E>
) {
    subscriber(E::class, data, function)
}
