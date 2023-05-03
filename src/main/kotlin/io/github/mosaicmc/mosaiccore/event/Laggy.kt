package io.github.mosaicmc.mosaiccore.event

@RequiresOptIn(message = "This event is laggy and may cause performance issues.")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Laggy
