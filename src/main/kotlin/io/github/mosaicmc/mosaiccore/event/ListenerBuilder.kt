package io.github.mosaicmc.mosaiccore.event

import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import kotlin.reflect.KClass

/**
 * Listener builder
 *
 * Listener builder is a class that holds all subscribers and registers them to the event handler
 * @property plugin The plugin container
 */
class ListenerBuilder(
    private val plugin: PluginContainer
) {
    private val subscribers: MutableList<Handler.SubscriberObject<out Event>> = mutableListOf()

    /**
     * Subscriber
     *
     * Subscriber is a DSL function that add subscriber to listener
     * @param E
     * @param eventClass
     * @param data
     * @param function
     */
    fun <E : Event> subscriber(
        eventClass: KClass<E>,
        data: SubscriberData = SubscriberData(),
        function: Handler.Subscriber<E>
    ) {
        subscribers.add(Handler.SubscriberObject(eventClass, data, function, plugin))
    }

    /**
     * Register
     *
     * Register is a function that registers all subscribers to the event handler.
     * Clears all the subscribers after registering.
     */
    fun register() {
        EventHandler.registerDSL(subscribers.toList())
        subscribers.clear()
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
fun listener(plugin: PluginContainer, block: ListenerBuilder.() -> Unit): ListenerBuilder = ListenerBuilder(plugin).apply(block)
