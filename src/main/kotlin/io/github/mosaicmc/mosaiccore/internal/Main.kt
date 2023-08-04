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
@file:JvmName("Main")

package io.github.mosaicmc.mosaiccore.internal

import io.github.mosaicmc.mosaiccore.api.event.Event
import io.github.mosaicmc.mosaiccore.api.event.call
import io.github.mosaicmc.mosaiccore.api.event.listen
import io.github.mosaicmc.mosaiccore.api.event.subscriber
import io.github.mosaicmc.mosaiccore.api.plugin.PluginContainer
import io.github.mosaicmc.mosaiccore.internal.event.Handler
import org.slf4j.LoggerFactory

internal val logger = LoggerFactory.getLogger("mosaicmc")

internal val Any?.unit
    get() = Unit

fun preInit() {
    logger.info("Welcome to mosaiccore!")
}

fun test(plugin: PluginContainer) {
    plugin listen { subscriber<TestEvent> { logger.info("Test") } }
    TestEvent().call()
}

class TestEvent : Event<TestEvent> {
    override fun call(handler: Handler<TestEvent>) {
        handler.iterator().forEach { apply(it.function) }
    }
}
