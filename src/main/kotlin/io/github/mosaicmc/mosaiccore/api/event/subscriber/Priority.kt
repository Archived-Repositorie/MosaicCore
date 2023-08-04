package io.github.mosaicmc.mosaiccore.api.event.subscriber

/**
 * Priority of a subscriber
 *
 * Represents the priority level of a subscriber. Subscribers with higher priority values are
 * executed first.
 *
 * @property integer The integer value representing the priority level.
 */
data class Priority(private val integer: Int) : Comparable<Priority> {
    companion object {
        val HIGHEST = Priority(Int.MAX_VALUE)
        val HIGH = Priority(1)
        val NORMAL = Priority(0)
        val LOW = Priority(-1)
        val LOWEST = Priority(Int.MIN_VALUE)
    }

    /**
     * Compare this priority to another priority.
     *
     * @param other The other priority to compare to.
     * @return A positive integer if this priority is greater, zero if they are equal, and a
     *   negative integer if this priority is smaller.
     */
    override fun compareTo(other: Priority): Int = other.integer.compareTo(integer)
}
