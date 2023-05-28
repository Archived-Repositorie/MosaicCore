package io.github.mosaicmc.mosaiccore.event.additions

/**
 * Represents an event that can be canceled.
 */
interface CancellableEvent {

    /**
     * Gets or sets whether this event is canceled.
     */
    var cancelled: Boolean
}
