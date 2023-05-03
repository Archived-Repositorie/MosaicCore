package io.github.mosaicmc.mosaiccore.event

import kotlin.reflect.KFunction1


class Handler<E : Event> : Iterable<Handler.Key<E>> {
    private val values: MutableList<Key<E>> = mutableListOf()

    data class Key<E : Event>(
        val subscriber: KFunction1<E, Unit>,
        val data: Subscriber,
        val classObject: Any
    ) : Comparable<Key<E>> {
        override fun compareTo(other: Key<E>): Int {
            return this.data.priority.compareTo(other.data.priority)
        }
    }

    fun add(subscriber: KFunction1<E, Unit>, data: Subscriber, classObject: Any) {
        add(Key(subscriber, data, classObject))
    }

    fun remove(subscriber: KFunction1<E, Unit>, data: Subscriber, classObject: Any) {
        remove(Key(subscriber, data, classObject))
    }

    private fun remove(key: Key<E>) {
        values.remove(key)
    }

    private fun add(valueKey: Key<E>) {
        val index = getSortedPlace(valueKey)
        values.add(index, valueKey)
    }

    override fun iterator(): Iterator<Key<E>> {
        return values.iterator()
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