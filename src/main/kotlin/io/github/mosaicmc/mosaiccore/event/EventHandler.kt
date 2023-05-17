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

    /**
     * Registers a new event.
     *
     * @param event the event class to register.
     * @throws IllegalArgumentException if the event is already registered.
     */
    fun <E : Event> registerEvent(event: KClass<E>) {
        if (events.containsKey(event)) {
            throw IllegalArgumentException("Event ${event.simpleName} is already registered!")
        }
        val handler: Handler<E> = Handler()
        events[event] = handler
    }

    /**
     * Unregisters an existing event..
     *
     * @param event the event class to unregister.
     * @throws IllegalArgumentException if the event is not registered.
     */
    fun <E : Event> unregisterEvent(event: KClass<E>) {
        if (!events.containsKey(event)) {
            throw IllegalArgumentException("Event ${event.simpleName} is not registered!")
        }
        events.remove(event)
    }

    fun <E : Event> registerSubscriber(
        event: KClass<E>,
        subscriberData: SubscriberData = SubscriberData(),
        function: (E) -> Unit,
    ) {
        registerSubscriber(event, function, subscriberData)
    }

    /**
     * Registers a new subscriber for the specified event.
     *
     * @param event the event class to register the subscriber for.
     * @param function the subscriber function to register.
     */
    fun <E : Event> registerSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
    ) {
        registerSubscriber(event, function, getSubscriberData(function))
    }

    /**
     * Unregisters a subscriber for the specified event.
     *
     * @param event the event class to unregister the subscriber for.
     * @param function the subscriber function to unregister.
     */
    fun <E : Event> unregisterSubscriber(
        event: KClass<E>,
        function: KFunction1<E, Unit>,
    ) {
        unregisterSubscriber(event, function, getSubscriberData(function))
    }

    /**
     * Registers all the subscribers of the specified [Listener].
     *
     * @param listener the listener object whose subscribers to register.
     */
    fun registerListener(listener: Listener) {
        val subscribers = getSubscribers(listener)

        for (subscriber in subscribers) {
            val event = getEventSubscriber(subscriber)
            val subscriberData = getSubscriberData(subscriber)

            registerSubscriber(event, subscriber, subscriberData, listener)
        }
    }

    /**
     * Unregisters all the subscribers of the specified [Listener] object.
     *
     * @param listener the listener object whose subscribers to unregister.
     */
    fun unregisterListener(listener: Listener) {
        val subscribers = getSubscribers(listener)

        for (subscriber in subscribers) {
            val event = getEventSubscriber(subscriber)
            val subscriberData = getSubscriberData(subscriber)

            unregisterSubscriber(event, subscriber, subscriberData, listener)
        }
    }

    /**
     * Calls the specified event and invokes all its subscribers.
     *
     * @param event the event to call.
     * @return true if the event was canceled, false otherwise.
     * @throws IllegalArgumentException if the event is not registered.
     */
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

            callSubscriber(key, event)
        }
        return false
    }

    /**
     * Unregisters a subscriber for the specified event.
     *
     * @param event the event class to unregister the subscriber for.
     * @param subscriber the subscriber function to unregister.
     * @param subscriberData the subscriber object to unregister.
     */
    private fun <E : Event> unregisterSubscriber(
        event: KClass<E>,
        subscriber: (E) -> Unit,
        subscriberData: SubscriberData = SubscriberData()
    ) {
        getHandler(event).remove(subscriber, subscriberData, null)
    }

    /**
     * Registers a new subscriber for the specified event.
     *
     * @param event the event class to register the subscriber for.
     * @param subscriber the subscriber function to register.
     * @param subscriberData the subscriber object to register.
     */
    private fun <E : Event> registerSubscriber(
        event: KClass<E>,
        subscriber: (E) -> Unit,
        subscriberData: SubscriberData = SubscriberData()
    ) {
        getHandler(event).add(subscriber, subscriberData, null)
    }


    /**
     * Registers a subscriber for the specified event.
     *
     * @param event the event class for which to register the subscriber.
     * @param subscriber the subscriber function to register.
     * @param subscriberData the subscriber object.
     * @param listener the listener object to which the subscriber belongs.
     */
    private fun <E : Event> registerSubscriber(
        event: KClass<E>,
        subscriber: (E) -> Unit,
        subscriberData: SubscriberData = SubscriberData(),
        listener: Listener
    ) {
        getHandler(event).add(subscriber, subscriberData, listener)
    }

    /**
     * Unregisters a subscriber for the specified event.
     *
     * @param event the event class for which to unregister the subscriber.
     * @param subscriber the subscriber function to unregister.
     * @param subscriberData the subscriber object.
     * @param listener the listener object to which the subscriber belongs.
     */
    private fun <E : Event> unregisterSubscriber(
        event: KClass<E>,
        subscriber: (E) -> Unit,
        subscriberData: SubscriberData = SubscriberData(),
        listener: Listener
    ) {
        getHandler(event).remove(subscriber, subscriberData, listener)
    }

    /**
     * Calls the function associated with the specified event key.
     *
     * @param key the event key whose function to call.
     * @param event the event object to pass to the function.
     */
    private fun <E : Event> callSubscriber(key: Handler.Key<out E>, event: E) {
        val subscriber = key.subscriber as (E) -> Unit
        if (key.listener == null) {
            subscriber.invoke(event)
        } else {
            (subscriber as KFunction1<Event, Unit>).call(key.listener,event)
        }
    }

    /**
     * Returns the list of all subscribers belonging to the specified listener object.
     *
     * @param listener the listener object whose subscribers to retrieve.
     * @return the list of subscriber functions belonging to the specified listener object.
     */
    private fun getSubscribers(listener: Listener): List<KFunction1<Event, Unit>> {
        return listener::class.functions
            .filter { it.annotations.any { annotation -> annotation is SubscriberData } }
            .filterIsInstance<KFunction1<Event, Unit>>()
    }

    /**
     * Returns the subscriber data for the specified subscriber function.
     *
     * @param subscriber the subscriber function for which to retrieve the subscriber data.
     * @return the subscriber object associated with the specified subscriber function.
     */
    private fun <E : Event> getSubscriberData(subscriber: KFunction1<E, Unit>): SubscriberData {
        return subscriber.annotations
            .filterIsInstance<SubscriberData>()
            .firstOrNull() ?: SubscriberData()
    }

    /**
     * Returns the event class for the specified event subscriber function.
     *
     * @param callable the event subscriber function.
     * @return the event class for the specified event subscriber function.
     * @throws IllegalArgumentException if the event class for the specified function is not registered.
     */
    @Suppress("UNCHECKED_CAST")
    private fun <E : Event> getEventSubscriber(callable: KFunction1<E, Unit>): KClass<out E> {
        val eventClass = callable.valueParameters[0].type.classifier!! as KClass<E>
        return checkForEvent(eventClass)
    }

    /**
     * Returns the handler object associated with the specified event class.
     *
     * @param event the event class for which to retrieve the handler object.
     * @return the handler object associated with the specified event class.
     * @throws IllegalArgumentException if the specified event class is not registered.
     */
    @Suppress("UNCHECKED_CAST")
    private fun <E : Event> getHandler(event: KClass<out E>): Handler<out E> {
        return events[checkForEvent(event)]!! as Handler<out E>
    }

    /**
     * Checks if the specified event class is registered.
     *
     * @param event the event class to check.
     * @throws IllegalArgumentException if the specified event class is not registered.
     */
    private fun <E : Event> checkForEvent(event: KClass<out E>): KClass<out E> {
        require(events.containsKey(event)) { "Event ${event.simpleName} is not registered" }
        return event
    }
}