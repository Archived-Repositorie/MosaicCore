package io.github.mosaicmc.mosaiccore.event

import kotlin.reflect.KFunction1

data class SubscriberKey(
    val subscriber: KFunction1<Event,Unit>,
    val data: Subscriber,
    val classObject: Any
)
