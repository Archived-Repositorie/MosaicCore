package io.github.mosaicmc.mosaiccore.event


/**
 * The Handler class is responsible for managing a list of subscribers for a specific event type [E].
 * The subscribers are sorted by priority and are executed in that order.
 * @param E the type of event that this handler is responsible for.
 */
class Handler<E : Event> : Iterable<Handler.Key<E>> {
    private val values: MutableList<Key<E>> = mutableListOf()

    /**
     * The [Key] class represents a subscriber and its associated data, including the [SubscriberData] annotation
     * and the instance of the listener class.
     * @param subscriber the function that will be called when an event is dispatched.
     * @param data the [SubscriberData] annotation that contains information about the subscriber.
     * @param listener the instance of the listener class.
     */
    data class Key<E : Event>(
        val subscriber: (E) -> Unit,
        val data: SubscriberData,
        val listener: Listener?
    ) : Comparable<Key<E>> {
        override fun compareTo(other: Key<E>): Int {
            return this.data.priority.compareTo(other.data.priority)
        }
    }

    /**
     * Adds a subscriber to the handler's list of subscribers.
     * @param subscriber the function that will be called when an event is dispatched.
     * @param data the [SubscriberData] annotation that contains information about the subscriber.
     * @param listener the instance of the listener class.
     */
    fun add(subscriber: (E) -> Unit, data: SubscriberData, listener: Listener?) {
        add(Key(subscriber, data, listener))
    }

    /**
     * Removes a subscriber from the handler's list of subscribers.
     * @param subscriber the function that will be removed from the list of subscribers.
     * @param data the [SubscriberData] annotation associated with the subscriber.
     * @param listener the instance of the listener class.
     */
    fun remove(subscriber: (E) -> Unit, data: SubscriberData, listener: Listener?) {
        remove(Key(subscriber, data, listener))
    }

    override fun iterator(): Iterator<Key<E>> {
        return values.iterator()
    }

    private fun remove(key: Key<E>) {
        values.remove(key)
    }

    private fun add(valueKey: Key<E>) {
        val index = getSortedPlace(valueKey)
        values.add(index, valueKey)
    }

    private fun getSortedPlace(key: Key<E>): Int {
        var left = 0
        var right = values.size - 1
        while (left <= right) {
            val mid = (left + right) / 2
            when {
                values[mid] == key -> return mid
                values[mid] < key -> left = mid + 1
                else -> right = mid - 1
            }
        }
        return left
    }
}