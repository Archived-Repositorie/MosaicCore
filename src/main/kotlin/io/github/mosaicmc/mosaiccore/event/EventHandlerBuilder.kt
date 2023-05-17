package io.github.mosaicmc.mosaiccore.event

class EventHandlerBuilder {
    private val test: MutableList<Subscriber<out Event>> = mutableListOf()

    fun <E : Event> subscriber(data: SubscriberData = SubscriberData(), function: Subscriber<out E>) {
        test.add(function)
    }

    fun register() {
        println(test)
    }
}

fun interface Subscriber<E : Event> {
    fun accept(event: E)
}

fun eventHandler(block: EventHandlerBuilder.() -> Unit) = EventHandlerBuilder().apply(block)
