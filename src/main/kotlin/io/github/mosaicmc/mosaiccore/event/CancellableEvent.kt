package io.github.mosaicmc.mosaiccore.event

/**
 * Represents an event that can be cancelled.
 */
interface CancellableEvent {

    /**
     * Gets or sets whether this event is cancelled.
     */
    var cancelled: Boolean
}
