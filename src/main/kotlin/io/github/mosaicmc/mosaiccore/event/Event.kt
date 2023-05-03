package io.github.mosaicmc.mosaiccore.event

/**
 * Event is an abstract class that represents an event that can be called by the event system.
 * Remember, when implementing the class, add a companion object with a handler otherwise it will error.
 * ```kt
 * companion object { //replace TestEvent2 with the class of your event
 *         private val handler = Handler<TestEvent2>()
 *         fun getHandler(): Handler<TestEvent2> {
 *             return handler
 *         }
 *     }
 * ```
 */
abstract class Event {
    val name: String = this::class.simpleName!!

    abstract fun getHandler(): Handler<*>
}