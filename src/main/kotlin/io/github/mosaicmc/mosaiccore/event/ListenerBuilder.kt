@file:Suppress("unused","deprecation")
package io.github.mosaicmc.mosaiccore.event

import io.github.mosaicmc.mosaiccore.event.subscriber.SubscriberData
import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import kotlin.reflect.KClass

internal typealias MutList = MutableList<Handler.SubscriberObject<out Event>>

/**
 * Listener builder
 *
 * Listener builder is a class that holds all subscribers and registers them to the event handler
 * @property plugin The plugin container
 */
class ListenerBuilder(
    private val plugin: PluginContainer
) {
    private val subs: MutList = mutableListOf()

    /**
     * Subscriber
     *
     * Subscriber is a function that adds subscriber to listener
     * @param E The event class
     * @param eventClazz The event class
     * @param data The subscriber data
     * @param function The subscriber function
     */
    @Deprecated("Use reified version instead", ReplaceWith("subscriber(data, function)"))
    fun <E : Event> subscriber(
        eventClazz: KClass<E>,
        data: SubscriberData = SubscriberData(),
        function: Handler.Subscriber<E>
    ) {
        subs.add(Handler.SubscriberObject(eventClazz, data, function, plugin))
    }

    /**
     * Subscriber
     *
     * Subscriber is a DSL function that add subscriber to listener
     * @param E The event class
     * @param data The subscriber data
     * @param function The subscriber function
     */
    inline fun <reified E : Event> subscriber(
        data: SubscriberData = SubscriberData(),
        function: Handler.Subscriber<E>
    ) {
        subscriber(E::class, data, function)
    }

    /**
     * Register
     *
     * Register is a function that registers all subscribers to the event handler.
     * Clears all the subscribers after registering.
     */
    internal fun register() {
        EventHandler.registerDSL(subs.toList())
        subs.clear()
    }
}

/**
 * Listener
 *
 * Listener is a DSL function that allows for easy event registration
 * @param plugin The plugin container
 * @param block The DSL block
 * @receiver The plugin container
 */
fun listener(plugin: PluginContainer, block: ListenerBuilder.() -> Unit) {
    val builder = ListenerBuilder(plugin).apply(block)
    builder.register()
}
