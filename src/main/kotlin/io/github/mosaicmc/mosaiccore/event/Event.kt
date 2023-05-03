package io.github.mosaicmc.mosaiccore.event

abstract class Event {
    val name: String = this::class.simpleName!!

    abstract fun getHandler(): Handler<*>

    abstract fun cancel(): Boolean
}