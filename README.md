# MosaicCore Documentation
## What is MosaicCore?
MosaicCore is pure library created as core for our MosaicMC project. The core doesn't modify nor access minecraft code and it is made to be implemented by MosaicMC. 
It has own event system which can be implemented and helper classes.
## Usage
1. Add modrinth repository to build.gradle
```groovy
repositories {
    // ...
    maven {
        url = "https://api.modrinth.com/maven"
    }
}
```
2. Add dependience (replace `{version}` with latest version found [here](https://modrinth.com/mod/mosaiccore/versions))
```groovy
dependencies {
    // Adding and remapping a mod only in local runtime
    modImplementation "maven.modrinth:mosaiccore:{version}"
}
```
## Event system
### Registering listener
Registering listener can be done using `EventHandler.registerListener(EventListener)`. Listener needs to implement `Listener` interface.</br>
Example usage:
```kt
fun init() {
  EventHandler.registerListener(EventListener)
}

object EventListener : Listener {
  @Subscriber
  fun test(event: TestEvent)
}
```
### Subscriber
Subscriber is a function inside listener. It subscribes on event which is in the argument of the function. Fanction cannot have annotation `@JvmStatic` otherwise it errors.
Subscriber can also handle priority and can ignore cancellation.</br>
Example usage: 
```kt
object EventListener : Listener {
  @Subscriber(priority = Priority.HIGHEST, ignoreCancelled = true)
  fun test(event: TestEvent)
}
```
### Custom event
Custom event is class which subscriber can subscribe on through listener. Events can be cancellable by implementing `Cancellable` interface and also can be laggy by annotating `@Laggy`
which will force user to OptIn and also will warn them from usage of the event. They can be called by using `EventHandler.callEvent(TestEvent)`
</br>
Example of simple event:
```kt
fun init() {
  EventHandler.callEvent(TestEvent)
}

class TestEvent : Event() {
    override fun getHandler(): Handler<*> {
        return handler
    }
    
    companion object {  // !! THIS PART IS REQUIRED TO WORK OTHERWISE IT WILL FAIL !!
        private val handler = Handler<TestEvent>()
        fun getHandler(): Handler<TestEvent> {
            return handler
        }
    }
}
```
Example of laggy and cancellable event and usage:
```kt
fun init() {
  @OptIn(Laggy::class) EventHandler.callEvent(TestEvent())
}
fun afterInit() {
  EventHandler.registerListener(EventListener)
}

data EventListener {
  @OptIn(Laggy::class) @Subscriber
  fun test(event: TestEvent)
}

@Laggy
class TestEvent : Event(), CancellableEvent {
    override var cancelled: Boolean = false

    override fun getHandler(): Handler<*> {
        return handler
    }
    
    companion object {  // !! THIS PART IS REQUIRED TO WORK OTHERWISE IT WILL FAIL !!
        private val handler = Handler<TestEvent>()
        fun getHandler(): Handler<TestEvent> {
            return handler
        }
    }
}
```
