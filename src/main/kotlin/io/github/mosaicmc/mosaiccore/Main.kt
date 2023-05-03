package io.github.mosaicmc.mosaiccore

import io.github.mosaicmc.mosaiccore.event.*
import io.github.mosaicmc.mosaiccore.utils.Mod
import net.fabricmc.loader.api.FabricLoader


internal val mod = Mod("mosaicmc")

@Suppress("unused")
fun preInit() {
    mod.logger.info("Welcome to mosaicmc!")

    if(System.getenv("TEST") == "true") {
        mod.logger.info("Test mode enabled")
        FabricLoader.getInstance().getEntrypoints("test", Runnable::class.java).forEach(Runnable::run)
    }
}

@Suppress("unused")
fun test() {
    EventHandler.registerListener(TestListener())
    EventHandler.callEvent(TestEvent())

}

@Suppress("unused")
fun init() {
    EventHandler.callEvent(TestEvent2())
}

class TestListener : Listener {
    @Subscriber
    fun onTestEvent(event: TestEvent) {
        mod.logger.info("Test event fired!")
    }

    @Subscriber
    fun onTestEvent2(event: TestEvent2) {
        mod.logger.info("Test event 2 fired!")
    }
}

class TestEvent : Event() {
    override fun getHandler(): Handler<TestEvent> {
        return handler
    }

    override fun cancel(): Boolean {
        return false
    }

    companion object {
        private val handler = Handler<TestEvent>()
        fun getHandler(): Handler<TestEvent> {
            return handler
        }
    }
}

class TestEvent2 : Event() {
    override fun getHandler(): Handler<TestEvent2> {
        return handler
    }

    override fun cancel(): Boolean {
        return false
    }

    companion object {
        private val handler = Handler<TestEvent2>()
        fun getHandler(): Handler<TestEvent2> {
            return handler
        }
    }
}









