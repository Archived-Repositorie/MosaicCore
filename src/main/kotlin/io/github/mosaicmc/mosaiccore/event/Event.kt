package io.github.mosaicmc.mosaiccore.event

import io.github.mosaicmc.mosaiccore.utils.sort.SortList

abstract class Event {
    val name: String = this::class.simpleName!!

    abstract fun getHandlers(): SortList<SubscriberKey, Priority>

    abstract fun cancel(): Boolean
}