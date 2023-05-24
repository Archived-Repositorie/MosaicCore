package io.github.mosaicmc.mosaiccore.event

import kotlin.reflect.KClass

/**
 * Event handler
 */
object EventHandler {
    private val events: EventMap = HashMap()

    /**
     * Register DSL
     *
     * Register DSL is a helper function that registers all subscribers to the event handler
     * @param list The list of subscriber objects
     */
    internal fun registerDSL(list: List<Handler.SubscriberObject<out Event>>) {
        list.forEach {
            registerSubscriber(it)
        }
    }

    /**
     * Register event
     *
     * Register event is a function that registers an event to the event handler.
     * Required for event to be called.
     * @param E
     * @param eventClass
     */
    fun <E : Event> registerEvent(eventClass: KClass<E>) {
        checkForEvent(eventClass, false)
        val handler: Handler<E> = Handler()
        events[eventClass] = handler
    }

    fun <E : Event> callEvent(event: E) {
        checkForEvent(event::class, true)
        @Suppress("UNCHECKED_CAST")
        val handler: Handler<E> = events[event::class] as Handler<E>
        handler.forEach { it.function.accept(event) }
    }

    private fun <E : Event> registerSubscriber(
        sub: Handler.SubscriberObject<E>,
    ) {
        checkForEvent(sub.eventClass, true)
        @Suppress("UNCHECKED_CAST")
        val handler: Handler<E> = events[sub.eventClass] as Handler<E>
        handler.add(sub)
    }

    private fun <E : Event> checkForEvent(eventClass: KClass<E>, doesContain: Boolean): KClass<E> {
        require(events.containsKey(eventClass) == doesContain) {
            "Event ${eventClass.simpleName} is ${if (doesContain) "not" else "already"} registered!"
        }
        return eventClass
    }
}

internal typealias EventMap = HashMap<KClass<out Event>, Handler<out Event>>