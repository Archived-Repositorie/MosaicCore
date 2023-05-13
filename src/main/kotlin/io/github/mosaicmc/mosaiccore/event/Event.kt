package io.github.mosaicmc.mosaiccore.event

/**
 * An abstract class that represents an event that can be triggered by the event system.
 *
 * To implement an event, create a class that extends this `Event` class and provide an implementation for the `getHandler()`
 * function. Additionally, add a companion object with a handler property to manage the event listeners:
 *
 * ```kt
 * companion object {
 *     private val handler = Handler<YourEventClass>()
 *
 *     fun getHandler(): Handler<YourEventClass> {
 *         return handler
 *     }
 * }
 * ```
 *
 * Replace `YourEventClass` with the name of your event class.
 */
abstract class Event {
    /**
     * Gets the `Handler` instance associated with this event. The `Handler` instance is used to manage event listeners
     * and to dispatch this event.
     *
     * @return The `Handler` instance for this event.
     */
    abstract fun getHandler(): Handler<*>

    /**
     * Gets the name of the event.
     *
     * @return The name of the event.
     */
    val name: String = this::class.simpleName!!
}