package io.github.mosaicmc.mosaiccore.event

import io.github.mosaicmc.mosaiccore.plugin.PluginContainer

internal typealias MutList = MutableList<Handler.SubscriberObject<out Event>>

/**
 * Listener builder
 *
 * Listener builder is a class that holds all subscribers and registers them to the event handler
 * @property plugin The plugin container
 */
class ListenerBuilder(
    val plugin: PluginContainer
) {
    @PublishedApi
    internal val subs: MutList = mutableListOf()

    /**
     * Subscriber
     *
     * Subscriber is a DSL function that add subscriber to listener
     * @param E
     * @param eventClass
     * @param data
     * @param function
     */
    inline fun <reified E : Event> subscriber(
        data: SubscriberData = SubscriberData(),
        function: Handler.Subscriber<E>
    ) {
        subs.add(Handler.SubscriberObject(E::class, data, function, plugin))
    }

    /**
     * Register
     *
     * Register is a function that registers all subscribers to the event handler.
     * Clears all the subscribers after registering.
     */
    fun register() {
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
fun listener(plugin: PluginContainer, block: ListenerBuilder.() -> Unit): ListenerBuilder = ListenerBuilder(plugin).apply(block)
