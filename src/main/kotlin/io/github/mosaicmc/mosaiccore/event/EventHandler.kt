package io.github.mosaicmc.mosaiccore.event

import io.github.mosaicmc.mosaiccore.utils.sort.SortList
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.isAccessible

object EventHandler {
    fun registerListener(listener: Listener) {
        listener::class.functions.asSequence().filter {
            it.annotations.any { annotation -> annotation is Subscriber }
        }.forEach {
            it.isAccessible = true
            val function = it as KFunction1<Event, Unit>
            val subscriber = it.annotations.find { annotation -> annotation is Subscriber } as Subscriber
            testFunction(function, listener)
            registerFunction(function, subscriber, listener)
        }
    }

    private fun registerFunction(callable: KFunction1<Event, Unit>, subscriber: Subscriber, classObject: Any) {
        val clazz = callable.parameters[1].type.classifier as KClass<Event>
        val classObjectInstance = clazz.companionObjectInstance!!
        val function = classObjectInstance::class.functions.asSequence().filter { it.name == "getHandlers" }.first()

        function.isAccessible = true
        val handlers = function.call(classObjectInstance) as SortList<SubscriberKey, Priority>
        handlers.add(SubscriberKey(callable, subscriber, classObject), subscriber.priority)
    }

    private fun testFunction(callable: KFunction1<Event, Unit>, classObject: Any) {
        val params = callable.parameters
        val objectType = classObject::class.createType()

        when (params.size) {
            2 -> if (params[0].type != objectType) throw IllegalArgumentException("Function ${callable.name} cannot be static")
        }
    }

    fun <T : Event> execute(event: T) {
        event.getHandlers().iterator().forEach {
            it.subscriber.call(it.classObject, event)
        }
    }
}