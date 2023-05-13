package io.github.mosaicmc.mosaiccore.event

/**
 * An annotation indicating that an event may cause performance issues due to lag.
 *
 * Use this annotation to mark events that are known to cause lag or other performance issues, so that users can opt in or out of using them based on their specific needs.
 *
 * For example:
 *
 * ```
 * @Laggy
 * class SlowEvent : Event
 * ```
 *
 * Users of this event will receive a warning that the event is laggy and may cause performance issues, and they can choose to handle it accordingly.
 *
 * @property message The message to display when this annotation is triggered.
 */
@RequiresOptIn(message = "This event is laggy and may cause performance issues.")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Laggy

