package io.github.mosaicmc.mosaiccore.event

import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.full.functions

/**
 * Event handler is a class that handles all events.
 */
object EventHandler {

    private val events: HashMap<KClass<out Event>, Handler<out Event>> = HashMap()

    fun <E : Event> registerEvent(event: KClass<E>) {
        if (events.containsKey(event)) {
            throw IllegalArgumentException("Event ${event.simpleName} is already registered!")
        }
        val handler: Handler<E> = Handler()
        events[event] = handler
    }

    fun <E : Event> unregisterEvent(event: KClass<E>) {
        if (!events.containsKey(event)) {
            throw IllegalArgumentException("Event ${event.simpleName} is not registered!")
        }
        events.remove(event)
    }

    fun <E : Event> registerSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
        subscriber: Subscriber
    ) {
        getHandler(event).add(function, subscriber, null)
    }

    private fun <E : Event> registerSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
        subscriber: Subscriber,
        listener: Listener
    ) {
        getHandler(event).add(function, subscriber, listener)
    }

    fun <E : Event> unregisterSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
        subscriber: Subscriber
    ) {
        getHandler(event).remove(function, subscriber, null)
    }

    private fun <E : Event> unregisterSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
        subscriber: Subscriber,
        listener: Listener
    ) {
        getHandler(event).remove(function, subscriber, listener)
    }


    fun registerListener(listener: Listener) {
        val functions = getSubscribers(listener)

        for (function in functions) {
            val event = getEventSubscriber(function)
            val subscriber = getSubscriberData(function)

            testFunction(function)
            registerSubscriber(event, function, subscriber, listener)
        }
    }

    fun unregisterListener(listener: Listener) {
        val functions = getSubscribers(listener)

        for (function in functions) {
            val event = getEventSubscriber(function)
            val subscriber = getSubscriberData(function)

            testFunction(function)
            unregisterSubscriber(event, function, subscriber, listener)
        }
    }

    fun <E : Event> callEvent(event: E) {
        checkForEvent(event::class)
        val handler = getHandler(event::class)
        for(key in handler) {
            if (
                key.data.ignoreCancelled ||
                event is CancellableEvent &&
                event.cancelled
            ) break

            callFunction(key, event)
        }
    }

    private fun <E : Event> callFunction(key: Handler.Key<out E>, event: E) {
        if (key.listener == null) {
            key.subscriber.call(event)
        } else {
            key.subscriber.call(key.listener, event)
        }
    }

    private fun getSubscribers(listener: Listener): List<KFunction1<Event, Unit>> {
        return listener::class.functions.asSequence().filter {
            it.annotations.any { annotation -> annotation is Subscriber }
        }.map @Suppress("UNCHECKED_CAST") {
            it as KFunction1<Event, Unit>
        }.toList()
    }

    private fun getSubscriberData(callable: KFunction1<Event, Unit>): Subscriber {
        return callable.annotations.find { annotation -> annotation is Subscriber } as Subscriber
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : Event> getEventSubscriber(callable: KFunction1<E, Unit>): KClass<E> {
        val eventClass = callable.parameters[1].type.classifier as KClass<E>
        checkForEvent(eventClass)
        return eventClass
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : Event> getHandler(event: KClass<E>): Handler<out E> {
        checkForEvent(event)
        return events[event]!! as Handler<out E>
    }

    private fun testFunction(callable: KFunction1<Event, Unit>) {
        val params = callable.parameters

        println(params)

        if (params.size != 2) {
            throw IllegalArgumentException("Function ${callable.name} must have 1 parameter!")
        }
    }

    private fun checkForEvent(event: KClass<out Event>) {
        if (!events.containsKey(event)) {
            throw IllegalArgumentException("Event ${event.simpleName} is not registered!")
        }
    }
}