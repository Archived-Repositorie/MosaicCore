package io.github.mosaicmc.mosaiccore.event

import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.full.functions
import kotlin.reflect.full.valueParameters

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

    fun <E : Event> unregisterSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
        subscriber: Subscriber
    ) {
        getHandler(event).remove(function, subscriber, null)
    }

    fun registerListener(listener: Listener) {
        val subscribers = getSubscribers(listener)

        for (subscriber in subscribers) {
            val event = getEventSubscriber(subscriber)
            val subscriberData = getSubscriberData(subscriber)

            registerSubscriber(event, subscriber, subscriberData, listener)
        }
    }

    fun unregisterListener(listener: Listener) {
        val subscribers = getSubscribers(listener)

        for (subscriber in subscribers) {
            val event = getEventSubscriber(subscriber)
            val subscriberData = getSubscriberData(subscriber)

            unregisterSubscriber(event, subscriber, subscriberData, listener)
        }
    }

    fun <E : Event> callEvent(event: E): Boolean {
        checkForEvent(event::class)
        val handler = getHandler(event::class)
        for(key in handler) {
            if (
                !key.data.ignoreCancelled &&
                event is CancellableEvent &&
                event.cancelled
            ) {
                return true
            }

            callFunction(key, event)
        }
        return false
    }

    private fun <E : Event> registerSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
        subscriber: Subscriber,
        listener: Listener
    ) {
        getHandler(event).add(function, subscriber, listener)
    }


    private fun <E : Event> unregisterSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
        subscriber: Subscriber,
        listener: Listener
    ) {
        getHandler(event).remove(function, subscriber, listener)
    }

    private fun <E : Event> callFunction(key: Handler.Key<out E>, event: E) {
        if (key.listener == null) {
            key.subscriber.call(event)
        } else {
            key.subscriber.call(key.listener, event)
        }
    }

    private fun getSubscribers(listener: Listener): List<KFunction1<Event, Unit>> {
        return listener::class.functions
            .filter { it.annotations.any { annotation -> annotation is Subscriber } }
            .filterIsInstance<KFunction1<Event, Unit>>()
    }

    private fun getSubscriberData(callable: KFunction1<Event, Unit>): Subscriber {
        return callable.annotations
            .filterIsInstance<Subscriber>()
            .first()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : Event> getEventSubscriber(callable: KFunction1<E, Unit>): KClass<out E> {
        val eventClass = callable.valueParameters[0].type.classifier!! as KClass<E>
        return checkForEvent(eventClass)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <E : Event> getHandler(event: KClass<out E>): Handler<out E> {
        return events[checkForEvent(event)]!! as Handler<out E>
    }

    private fun <E : Event> checkForEvent(event: KClass<out E>): KClass<out E> {
        if (!events.containsKey(event)) {
            throw IllegalArgumentException("Event ${event.simpleName} is not registered!")
        }
        return event
    }
}