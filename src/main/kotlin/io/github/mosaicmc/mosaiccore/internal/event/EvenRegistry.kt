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
