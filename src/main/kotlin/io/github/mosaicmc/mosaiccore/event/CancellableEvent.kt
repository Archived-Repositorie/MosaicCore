package io.github.mosaicmc.mosaiccore.event

/**
 * Represents an event that can be canceled.
 */
interface CancellableEvent {

    /**
     * Gets or sets whether this event is canceled.
     */
    var cancelled: Boolean
}
