package io.github.mosaicmc.mosaiccore.event

import io.github.mosaicmc.mosaiccore.plugin.PluginContainer
import kotlin.reflect.KClass

class ListenerBuilder(val plugin: PluginContainer) {
    private val subscribers: MutableList<Subscriber<out Event>> = mutableListOf()

    fun <E : Event> subscriber(
        eventClass: KClass<E>,
        data: SubscriberData = SubscriberData(),
        function: Subscriber<E>
    ) {
        subscribers.add(function)
    }

    fun register() {
        println(subscribers)
    }
}

fun interface Subscriber<E : Event> {
    fun accept(event: E)
}

fun listener(plugin: PluginContainer, block: ListenerBuilder.() -> Unit) = ListenerBuilder(plugin).apply(block)
