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
    internal fun registerDSL(
        list: List<Handler.SubscriberObject<out Event>>
    ) {
        list.forEach {
            registerSubscriber(it)
        }
    }

    @Deprecated("Use reified version instead", ReplaceWith("registerEvent(eventClass)"))
    fun <E : Event> registerEvent(eventClass: KClass<E>) {
        if (events.containsKey(eventClass)) return
        val handler: Handler<E> = Handler()
        events[eventClass] = handler
    }

    /**
     * Register event
     *
     * Register event is a function that registers an event to the event handler.
     * Required for event to be called.
     * @param E
     * @param eventClass
     */
    inline fun <reified E: Event> registerEvent() {
        registerEvent(E::class)
    }

    fun <E : Event> callEvent(event: E) {
        (getHandler(event::class) as Handler<E>).forEach { it.function.accept(event) }
    }

    private fun <E : Event> registerSubscriber(sub: Handler.SubscriberObject<E>) =
        (getHandler<Event>() as Handler<E>).add(sub)

    private fun <E : Event> checkForEvent(eventClass: KClass<E>, doesContain: Boolean): KClass<E> {
        require(events.containsKey(eventClass) == doesContain) {
            "Event ${eventClass.simpleName} is ${if (doesContain) "not" else "already"} registered!"
        }
        return eventClass
    }

    private fun <E : Event> getHandler(eventKClass: KClass<E>): Handler<E> {
        checkForEvent(eventKClass, true)
        @Suppress("UNCHECKED_CAST")
        return events[eventKClass] as Handler<E>
    }

    private inline fun <reified E : Event> getHandler(): Handler<E> = getHandler(E::class)
}

internal typealias EventMap = HashMap<KClass<out Event>, Handler<out Event>>