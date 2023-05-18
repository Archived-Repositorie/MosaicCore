package io.github.mosaicmc.mosaiccore.event

import kotlin.reflect.KClass

class EventHandler1 {
    private val events: HashMap<KClass<out Event>, Handler<out Event>> = HashMap()
    fun registerDSL(list: MutableList<Subscriber<out Event>>) {
        list.forEach { registerSubscriber(it) }
    }

    fun <E : Event> registerEvent(eventClass: KClass<E>) {
        require(!events.containsKey(eventClass)) { "Event ${eventClass.simpleName} is already registered!" }
        val handler: Handler<E> = Handler()
        events[eventClass] = handler
    }

    private fun <E : Event> registerSubscriber(subscriber: Subscriber<E>) {
        val eventClass = subscriber.
        require(events.containsKey(eventClass)) { "Event ${eventClass.simpleName} is not registered!" }
        val handler: Handler<E> = events[eventClass] as Handler<E>
        handler.registerSubscriber(subscriber)
    }

    private fun <E : Event> getEventClassFromSubscriber(subscriber: Subscriber<E>): KClass<E> {
        return E::class
    }

    private fun <E : Event> checkForEvent(eventClass: KClass<E>, doesContain: Boolean): KClass<E> {
        require(events.containsKey(eventClass) == doesContain) {
            "Event ${eventClass.simpleName} is ${if (doesContain) "not" else "already"} registered!"
        }

    }

}