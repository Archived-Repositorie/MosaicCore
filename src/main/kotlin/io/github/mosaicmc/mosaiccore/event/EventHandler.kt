package io.github.mosaicmc.mosaiccore.event

import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.isAccessible

object EventHandler {
    fun registerListener(listener: Listener) {
        val functions = getFunctions(listener)

        for (function in functions) {
            val subscriber = getFunctionSubscriber(function)

            testFunction(function, listener)
            registerFunction(function, subscriber, listener)
        }
    }

    fun unregisterListener(listener: Listener) {
        val functions = getFunctions(listener)

        for (function in functions) {
            val subscriber = getFunctionSubscriber(function)

            testFunction(function, listener)
            unregisterFunction(function, subscriber, listener)
        }
    }

    fun <T : Event> callEvent(event: T) {
        event.getHandler().forEach {
            it.subscriber.call(it.classObject, event)
        }
    }

    private fun getFunctions(listener: Listener): List<KFunction1<Event, Unit>> {
        return listener::class.functions.asSequence().filter {
            it.annotations.any { annotation -> annotation is Subscriber }
        }.map {
            it as KFunction1<Event, Unit>
        }.toList()
    }

    private fun getFunctionSubscriber(callable: KFunction1<Event, Unit>): Subscriber {
        return callable.annotations.find { annotation -> annotation is Subscriber } as Subscriber
    }

    private fun unregisterFunction(callable: KFunction1<Event, Unit>, subscriber: Subscriber, listener: Listener) {
        val handler = getHandler(callable)

        handler.remove(callable, subscriber, listener)
    }

    private fun registerFunction(callable: KFunction1<Event, Unit>, subscriber: Subscriber, classObject: Listener) {
        val handler = getHandler(callable)

        handler.add(callable, subscriber, classObject)
    }

    private fun getHandler(callable: KFunction1<Event, Unit>): Handler<Event> {
        val clazz = callable.parameters[1].type.classifier as KClass<Event>
        val classObjectInstance = clazz.companionObjectInstance!!
        val function = classObjectInstance::class.functions.asSequence().filter { it.name == "getHandler" }.first()

        function.isAccessible = true

        return function.call(classObjectInstance) as Handler<Event>
    }

    private fun testFunction(callable: KFunction1<Event, Unit>, classObject: Listener) {
        val params = callable.parameters
        val objectType = classObject::class.createType()

        if (
            params.size == 2 &&
            params[0].type != objectType
        ) {
            throw IllegalArgumentException("Function ${callable.name} cannot be static")
        }
    }
}